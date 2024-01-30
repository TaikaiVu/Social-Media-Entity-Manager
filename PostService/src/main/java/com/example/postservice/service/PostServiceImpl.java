package com.example.postservice.service;
import com.example.postservice.dto.PostRequest;
import com.example.postservice.dto.PostResponse;
import com.example.postservice.dto.UserResponse;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final WebClient webClient;

    @Value("${user.service.url}")
    private String userUrl;

    public PostServiceImpl(PostRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.baseUrl(userUrl).build();
    }

    @Override
    public void createPost(PostRequest postRequest) {
        // Implementation to create a new post
        Post newPost = Post.builder()
                .userId(postRequest.getUserId())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

        repository.save(newPost);
    }

    @Override
    public String updatePost(String postId, PostRequest postRequest) {
        // Implementation to update a post
        Post post = repository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        repository.save(post);

        return post.getId();
    }

    @Override
    public void deletePost(String postId) {
        // Implementation to delete a post
        repository.deleteById(postId);
    }

    @Override
    public PostResponse getPost(String postId) {
        // Implementation to get a single post
        Post post = repository.findById(postId).orElse(null);

        if (post != null) {
            CompletableFuture<UserResponse> userResponseFuture = getUserDetails(post.getUserId());
            return PostResponse.from(post, userResponseFuture.join());
        } else {
            return null;
        }
    }

    @Override
    public List<PostResponse> getAllPosts() {
        // Implementation to get all posts and fetch user details for each post
        List<Post> posts = repository.findAll();
        return posts.stream()
                .map(post -> {
                    CompletableFuture<UserResponse> userResponseFuture = getUserDetails(post.getUserId());
                    return PostResponse.from(post, userResponseFuture.join());
                })
                .collect(Collectors.toList());
    }

    private CompletableFuture<UserResponse> getUserDetails(String userId) {
        return webClient.get()
                .uri("/api/users/{userId}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .toFuture();
    }
}
