package com.example.postservice.dto;
import com.example.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String id;
    private String userId;
    private String title;
    private String content;
    private CommentResponse commentResponse;
    private UserResponse user;


    public static PostResponse from(Post post, UserResponse userResponse) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .commentResponse(new CommentResponse())
                .user(userResponse)
                .build();
    }
}
