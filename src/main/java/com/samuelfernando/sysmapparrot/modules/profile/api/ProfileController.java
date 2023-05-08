package com.samuelfernando.sysmapparrot.modules.profile.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.profile.dto.UpdateUserProfileRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	@Autowired
	private IUserService userService;
	
	@GetMapping("")
	public List<UserProfileResponse> findAllUsersProfiles(@RequestParam(required = false) String name) {
		return userService.findAllUsersProfiles(name);
	}
	
	@GetMapping("/{id}")
	public UserProfileResponse findUserProfileById(@PathVariable UUID id) {
		return userService.findUserProfileById(id);
	}
	
	@PostMapping("/{id}/follow")
	public void createUserProfileFollow(@PathVariable UUID id) {
		userService.createUserProfileFollow(id);
	}
	
	@PostMapping("/{id}/unfollow")
	public void removeUserProfileFollow(@PathVariable UUID id) {
		userService.removeUserProfileFollow(id);
	}
	
	@PostMapping("/photo")
	public void uploadProfilePhoto(@RequestParam("photo") MultipartFile photo) throws Exception {
		userService.uploadProfilePhoto(photo);
	}
	
	@PutMapping("/{id}")
	public void updateUserProfile(@PathVariable UUID id, @RequestBody UpdateUserProfileRequest updateProfileRequest) {
		userService.updateUserProfile(id, updateProfileRequest);
	}
}
