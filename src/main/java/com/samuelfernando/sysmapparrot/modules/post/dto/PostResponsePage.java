package com.samuelfernando.sysmapparrot.modules.post.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.samuelfernando.sysmapparrot.config.PageableResponse;
import com.samuelfernando.sysmapparrot.modules.post.entity.Post;

public class PostResponsePage {
	public List<PostResponse> postResponse;
	public PageableResponse pageableResponse;
	
	public PostResponsePage() {
	}
	
	public static PostResponsePage of(Page<Post> postPage, List<PostResponse> postResponseList) {
		PostResponsePage response = new PostResponsePage();
		
		response.postResponse = postResponseList;
		response.pageableResponse = PageableResponse.of(postPage);
		
		return response;
	}
}
