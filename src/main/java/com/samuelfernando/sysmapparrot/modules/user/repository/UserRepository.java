package com.samuelfernando.sysmapparrot.modules.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuelfernando.sysmapparrot.modules.user.entity.User;

public interface UserRepository extends MongoRepository<User, UUID> {
	List<User> findAllByNameLikeIgnoreCase(String name);
}
