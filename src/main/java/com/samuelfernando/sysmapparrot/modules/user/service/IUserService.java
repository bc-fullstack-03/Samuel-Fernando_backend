package com.samuelfernando.sysmapparrot.modules.user.service;

import java.util.List;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;

public interface IUserService {
	public List<UserProfileResponse> findAllUsersProfiles(String name);
	public UserProfileResponse findUserProfileById(UUID id);
	public void createUser(CreateUserRequest user);
	public void createUserProfileFollow(UUID id);
	public void removeUserProfileFollow(UUID id);
}
