package com.samuelfernando.sysmapparrot.modules.user.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samuelfernando.sysmapparrot.modules.user.dto.CreateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.dto.UpdateUserRequest;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody CreateUserRequest user) {
		userService.createUser(user);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable UUID id, @RequestBody UpdateUserRequest updateUserRequest) {
		userService.updateUser(id, updateUserRequest);
	}
	
	@DeleteMapping("/{id}")
	public void update(@PathVariable UUID id) {
		userService.deleteUser(id);
	}
}
