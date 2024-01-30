package com.example.postservice.service;
import com.example.postservice.dto.PostRequest;
import com.example.postservice.dto.PostResponse;
import java.util.List;

public interface PostService {
    void createPost(PostRequest postRequest);

    String updatePost(String postId, PostRequest postRequest);

    void deletePost(String postId);

    PostResponse getPost(String postId);

    List<PostResponse> getAllPosts();
}
