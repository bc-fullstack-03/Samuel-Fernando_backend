package com.samuelfernando.sysmapparrot.modules.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UpdateUserProfileRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.dto.UpdateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.entity.User;

public interface IUserService {
	public List<UserProfileResponse> findAllUsersProfiles(String name);
	public User findUserByEmail(String email);
	public UserProfileResponse findUserProfileById(UUID id);
	public void createUser(CreateUserRequest user);
	public void createUserProfileFollow(UUID id);
	public void removeUserProfileFollow(UUID id);
	public void uploadProfilePhoto(MultipartFile photo) throws Exception;
	public void updateUser(UUID id, UpdateUserRequest updateUserRequest);
	public void updateUserProfile(UUID id, UpdateUserProfileRequest updateProfileRequest);
	public void deleteUser(UUID id);
}
