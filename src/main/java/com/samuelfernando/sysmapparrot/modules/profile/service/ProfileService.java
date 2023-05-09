package com.samuelfernando.sysmapparrot.modules.profile.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UpdateUserProfileRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@Service
public class ProfileService implements IProfileService {
	@Autowired
	private IUserService userService;
	
	@Override
	public List<UserProfileResponse> findAllUsersProfiles(String name) {
		return userService.findAllUsersProfiles(name);
	}

	@Override
	public UserProfileResponse findUserProfileById(UUID id) {
		return userService.findUserProfileById(id);
	}

	@Override
	public void createUserProfileFollow(UUID id) {
		userService.createUserProfileFollow(id);
	}

	@Override
	public void removeUserProfileFollow(UUID id) {
		userService.removeUserProfileFollow(id);
	}

	@Override
	public void uploadProfilePhoto(MultipartFile photo) throws Exception {
		userService.uploadProfilePhoto(photo);
	}

	@Override
	public void updateUserProfile(UUID id, UpdateUserProfileRequest updateProfileRequest) {
		userService.updateUserProfile(id, updateProfileRequest);
	}
}
