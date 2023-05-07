package com.samuelfernando.sysmapparrot.modules.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuelfernando.sysmapparrot.modules.post.entity.Post;

public interface PostRepository extends MongoRepository<Post, UUID> {
	List<Post> findAllByUserId(UUID postId);
}