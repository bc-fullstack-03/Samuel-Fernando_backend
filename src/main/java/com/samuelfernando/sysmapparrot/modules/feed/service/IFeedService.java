package com.samuelfernando.sysmapparrot.modules.feed.service;

import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponsePage;

public interface IFeedService {
	public PostResponsePage generateFeed(Integer page);
}
