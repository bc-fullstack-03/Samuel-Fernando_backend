package com.samuelfernando.sysmapparrot.modules.profile.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UpdateUserProfileRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;

public interface IProfileService {
	public List<UserProfileResponse> findAllUsersProfiles(String name);
	public UserProfileResponse findUserProfileById(UUID id);
	public void createUserProfileFollow(UUID id);
	public void removeUserProfileFollow(UUID id);
	public void uploadProfilePhoto(MultipartFile photo) throws Exception;
	public void updateUserProfile(UUID id, UpdateUserProfileRequest updateProfileRequest);
}
