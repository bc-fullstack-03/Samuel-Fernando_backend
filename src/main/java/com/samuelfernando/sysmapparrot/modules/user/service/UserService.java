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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samuelfernando.sysmapparrot.config.exception.BusinessRuleException;
import com.samuelfernando.sysmapparrot.config.exception.NotFoundException;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;
import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.entity.User;
import com.samuelfernando.sysmapparrot.modules.user.repository.UserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserProfileResponse> findAllUsersProfiles(String name) {
		if (isEmpty(name)) {
			return userRepository.findAll().stream().map(user -> UserProfileResponse.of(user))
					.collect(Collectors.toList());
		} else {
			return userRepository.findAllByNameLikeIgnoreCase(name).stream().map(user -> UserProfileResponse.of(user))
					.collect(Collectors.toList());
		}
	}

	@Override
	public UserProfileResponse findUserProfileById(UUID id) {
		User user = findById(id);
		return UserProfileResponse.of(user);
	}

	@Override
	public void createUser(CreateUserRequest user) {
		Set<UUID> genericSet = new HashSet<UUID>();
		LocalDateTime createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now();
		UserProfile userProfile = new UserProfile(user.name, genericSet, genericSet, createdAt, updatedAt);
		User newUser = new User(user.name, user.email, user.password, userProfile, createdAt, updatedAt);
		userRepository.save(newUser);
	}

	@Override
	@Transactional
	public void createUserProfileFollow(UUID id) {
		User userToBeFollowed = findById(id);
		// id will come from request user
		User userToBeFollowing = findById(UUID.fromString("dfcc0ad5-d02f-410a-a6b0-345b68a2f147"));
		validateProfileFollow(userToBeFollowed, userToBeFollowing);
		userToBeFollowed.getUserProfile().getFollowers().add(userToBeFollowing.getId());
		userToBeFollowing.getUserProfile().getFollowing().add(userToBeFollowed.getId());
		userRepository.save(userToBeFollowed);
		userRepository.save(userToBeFollowing);
	}

	@Override
	public void removeUserProfileFollow(UUID id) {
		User userToBeUnfollowed = findById(id);
		// id will come from request user
		User userToBeUnfollowing = findById(UUID.fromString("dfcc0ad5-d02f-410a-a6b0-345b68a2f147"));
		userToBeUnfollowed.getUserProfile().getFollowers().remove(userToBeUnfollowing.getId());
		userToBeUnfollowing.getUserProfile().getFollowing().remove(userToBeUnfollowed.getId());
		userRepository.save(userToBeUnfollowed);
		userRepository.save(userToBeUnfollowing);
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
}
