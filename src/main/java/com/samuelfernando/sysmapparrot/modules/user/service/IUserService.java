package com.samuelfernando.sysmapparrot.modules.user.service;

import java.util.List;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.entity.User;

public interface IUserService {
	public List<UserProfileResponse> findAllUsersProfiles(String name);
	public User findUserByEmail(String email);
	public UserProfileResponse findUserProfileById(UUID id);
	public void createUser(CreateUserRequest user);
	public void createUserProfileFollow(UUID id);
	public void removeUserProfileFollow(UUID id);
}
