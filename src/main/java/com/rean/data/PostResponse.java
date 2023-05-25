package com.rean.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rean.model.Post;
import lombok.Data;

import java.util.Date;

@Data
public class PostResponse {

    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("posted_at")
    private Date postedAt;
    private String content;

    @JsonProperty("user")
    private UserResponse user;


    @Data
    public static class UserResponse {
        private String username;
        @JsonProperty("profile_img")
        private String profileImg;
    }

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.postedAt = post.getPostedAt();
        this.content = post.getContent();

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(post.getUser().getUsername());
        userResponse.setProfileImg(post.getUser().getProfileImg());

        this.user = userResponse;
    }
}
