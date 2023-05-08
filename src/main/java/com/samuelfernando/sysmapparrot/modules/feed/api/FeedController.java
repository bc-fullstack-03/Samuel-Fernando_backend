package com.samuelfernando.sysmapparrot.modules.feed.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelfernando.sysmapparrot.modules.feed.service.IFeedService;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponsePage;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {
	@Autowired
	IFeedService feedService;
	
	@GetMapping("")
	public PostResponsePage getFeed(@RequestParam int page) {
		return feedService.generateFeed(page);
	}
}
