package com.samuelfernando.sysmapparrot.modules.post.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuelfernando.sysmapparrot.modules.post.entity.Post;

public interface PostRepository extends MongoRepository<Post, UUID> {
	List<Post> findAllByUserId(UUID userId);
	Page<Post> findAllPostsByUserIdIn(Set<UUID> profileIds, Pageable pageable);
}