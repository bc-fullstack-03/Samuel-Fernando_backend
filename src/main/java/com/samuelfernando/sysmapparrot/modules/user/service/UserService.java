package com.samuelfernando.sysmapparrot.modules.user.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.config.exception.BusinessRuleException;
import com.samuelfernando.sysmapparrot.config.exception.ForbiddenException;
import com.samuelfernando.sysmapparrot.config.exception.NotFoundException;
import com.samuelfernando.sysmapparrot.modules.file.service.IFileUploadService;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.service.IPostService;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UpdateUserProfileRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;
import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.dto.UpdateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.entity.User;
import com.samuelfernando.sysmapparrot.modules.user.repository.UserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;
	@Autowired
	private IFileUploadService fileUploadService;
	private IPostService postService;
	
	@Autowired
	public UserService(@Lazy IPostService postService) {
        this.postService = postService;
    }
	
	@Value("${app-config.secrets.aws-s3-url}")
	private String s3Url;
	@Value("${app-config.api-env}")
	private String apiEnv;

	@Override
	public List<UserProfileResponse> findAllUsersProfiles(String name) {
		if (isEmpty(name)) {
			return userRepository.findAll().stream().map(user -> {
				user.getUserProfile().setPhotoUri(generatePhotoUri(user));
				return UserProfileResponse.of(user);
			}).collect(Collectors.toList());
		} else {
			return userRepository.findAllByNameLikeIgnoreCase(name).stream().map(user -> {
				user.getUserProfile().setPhotoUri(generatePhotoUri(user));
				return UserProfileResponse.of(user);
			}).collect(Collectors.toList());
		}
	}

	@Override
	public UserProfileResponse findUserProfileById(UUID id) {
		User user = findById(id);
		user.getUserProfile().setPhotoUri(generatePhotoUri(user));
		return UserProfileResponse.of(user);
	}

	@Override
	public User findUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			throw new NotFoundException("User not found");
		}

		return user.get();
	}

	@Override
	public void createUser(CreateUserRequest user) {
		if (userRepository.findByEmail(user.email).isPresent()) {
			throw new BusinessRuleException("An error occurred when trying to create a user");
		}

		Set<UUID> genericSet = new HashSet<UUID>();
		LocalDateTime createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now();
		String photoUri = "";

		UserProfile userProfile = new UserProfile(user.name, photoUri, genericSet, genericSet, createdAt, updatedAt);
		User newUser = new User(user.name, user.email, user.password, userProfile, createdAt, updatedAt);
		newUser.setPassword(bcryptPasswordEncoder.encode(newUser.getPassword()));

		userRepository.save(newUser);
	}

	@Override
	@Transactional
	public void createUserProfileFollow(UUID id) {
		User userToBeFollowed = findById(id);
		User userToBeFollowing = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes()
				.getAttribute("userId", RequestAttributes.SCOPE_REQUEST)));
		validateProfileFollow(userToBeFollowed, userToBeFollowing);
		userToBeFollowed.getUserProfile().getFollowers().add(userToBeFollowing.getId());
		userToBeFollowing.getUserProfile().getFollowing().add(userToBeFollowed.getId());
		userRepository.save(userToBeFollowed);
		userRepository.save(userToBeFollowing);
	}

	@Override
	@Transactional
	public void removeUserProfileFollow(UUID id) {
		User userToBeUnfollowed = findById(id);
		User userToBeUnfollowing = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes()
				.getAttribute("userId", RequestAttributes.SCOPE_REQUEST)));
		userToBeUnfollowed.getUserProfile().getFollowers().remove(userToBeUnfollowing.getId());
		userToBeUnfollowing.getUserProfile().getFollowing().remove(userToBeUnfollowed.getId());
		userRepository.save(userToBeUnfollowed);
		userRepository.save(userToBeUnfollowing);
	}

	@Override
	public void updateUser(UUID id, UpdateUserRequest updateUserRequest) {
		User user = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST)));
		
		Optional<User> userWithEmailAlreadyUsed = userRepository.findByEmail(updateUserRequest.email);
		
		if (userWithEmailAlreadyUsed.isPresent() && !userWithEmailAlreadyUsed.get().getEmail().equals(user.getEmail())) {
			throw new BusinessRuleException("An error occurred when trying to update a user");
		}
		
		verifyUserPermissionToModifyData(id, user);
		LocalDateTime updatedAt = LocalDateTime.now();
		
		user.setName(updateUserRequest.name);
		user.setEmail(updateUserRequest.email);
		user.setUpdatedAt(updatedAt);
		
		boolean isPasswordValid = validateNewPassword(updateUserRequest, user.getPassword());
		
		if (isPasswordValid && !updateUserRequest.newPassword.equals("")) {
			user.setPassword(bcryptPasswordEncoder.encode(updateUserRequest.newPassword));
		}
		
		userRepository.save(user);
	}
	
	public void uploadProfilePhoto(MultipartFile photo) throws Exception {
		User user = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST)));

		String photoUri = "";

		try {
			String filename = user.getId() + "."
					+ photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);

			photoUri = fileUploadService.upload(photo, filename);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		user.getUserProfile().setPhotoUri(photoUri);
		userRepository.save(user);
	}

	@Override
	public void updateUserProfile(UUID id, UpdateUserProfileRequest updateProfileRequest) {
		User user = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST)));
		UserProfileResponse userProfileResponse = findUserProfileById(id);

		verifyUserPermissionToModifyData(id, user);
		LocalDateTime updatedAt = LocalDateTime.now();

		UserProfile userProfile = new UserProfile(updateProfileRequest.name, userProfileResponse.photoUri,
				userProfileResponse.followers, userProfileResponse.following, userProfileResponse.createdAt, updatedAt);
		
		user.setUserProfile(userProfile);
		
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public void deleteUser(UUID id) {
		User user = findById(UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST)));
		
		verifyUserPermissionToModifyData(id, user);
		List<PostResponse> posts = postService.getAllPostsByUserId(id);
		System.out.println(posts);
		
		posts.stream().forEach(post -> postService.deletePost(post.id));
		userRepository.delete(user);
	}

	private User findById(UUID id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new NotFoundException("User ID: " + id + " not found");
		}

		return user.get();
	}

	private void validateProfileFollow(User userToBeFollowed, User userToBeFollowing) {
		if (userToBeFollowed.getUserProfile().getFollowers().contains(userToBeFollowing.getId())
				|| userToBeFollowing.getUserProfile().getFollowing().contains(userToBeFollowed.getId())) {
			throw new BusinessRuleException("Already followed the user id: " + userToBeFollowed.getId());
		}
	}

	private void verifyUserPermissionToModifyData(UUID userId, User user) {
		if (!userId.equals(user.getId())) {
			throw new ForbiddenException("You don't have permission to change the user content");
		}
	}
	
	private boolean validateNewPassword(UpdateUserRequest updateUserRequest, String password) {
		if (updateUserRequest.newPassword.equals("") && updateUserRequest.oldPassword.equals("")) {
			return false;
		}
		
		if (!bcryptPasswordEncoder.matches(updateUserRequest.oldPassword, password)) {
			throw new BusinessRuleException("Incorrect password");
		}
		
		if (updateUserRequest.newPassword.length() < 3) {
			throw new BusinessRuleException("newPassword must have more than 3 characters");
		}
		
		return true;
	}
	
	private String generatePhotoUri(User user) {
		if (!user.getUserProfile().getPhotoUri().equals("")) {
			if (apiEnv.equals("container")) {
				return "http://localhost:4566" +user.getUserProfile().getPhotoUri(); 
			} else {
				return s3Url +user.getUserProfile().getPhotoUri();
			}
		}
		
		return user.getUserProfile().getPhotoUri();
	}
}
