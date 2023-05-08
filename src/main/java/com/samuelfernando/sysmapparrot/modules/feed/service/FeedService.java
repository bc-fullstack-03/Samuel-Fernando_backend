package com.samuelfernando.sysmapparrot.modules.feed.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponsePage;
import com.samuelfernando.sysmapparrot.modules.post.service.IPostService;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@Service
public class FeedService implements IFeedService {
	@Autowired
	IUserService userService;
	@Autowired
	IPostService postService;
	
	@Override
	public PostResponsePage generateFeed(int page) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		UserProfileResponse userProfileResponse = userService.findUserProfileById(userId);
				
		return postService.getFeed(userProfileResponse, page);
	}
	
}
