package com.samuelfernando.sysmapparrot.modules.comment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuelfernando.sysmapparrot.modules.comment.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, UUID> {
	Optional<Comment> findByPostId(UUID postId);
}
