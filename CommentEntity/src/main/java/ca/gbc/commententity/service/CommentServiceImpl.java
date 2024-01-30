package ca.gbc.commententity.service;

import ca.gbc.commententity.dto.CommentRequest;
import ca.gbc.commententity.dto.CommentResponse;
import ca.gbc.commententity.dto.UserResponse;
import ca.gbc.commententity.model.Comment;
import ca.gbc.commententity.repository.CommentRepository;
//import ca.gbc.userentity.dto.UserResponse;
//import ca.gbc.userentity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository respository;
    private final WebClient webClient;

    @Value("${user.service.url}")
    private String userUrl;

    @Override
    public void createComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .user_id(commentRequest.getUser_id())
                .post_id(commentRequest.getPost_id())
                .content(commentRequest.getContent())
                .build();

        comment = respository.save(comment);
    }

    @Override
    public void updateComment(Long commentId, CommentRequest commentRequest) {
        Optional<Comment> comment = respository.findById(commentId);
        if (!comment.isPresent()) {
            return;
        }

        Comment obj = comment.get();
        obj.setContent(commentRequest.getContent());

        return;
    }
    @Override
    public List<CommentResponse> getComments(String postId) {
        Comment query = Comment.builder().post_id(postId).build();
        return respository.findAll(Example.of(query)).stream().map(this::mapToCommentResponse).toList();
    }


    @Override
    public void deleteComment(Long commentId) {
        respository.deleteById(commentId);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        CommentResponse commentResponse = CommentResponse.builder()
                .id(comment.getId())
                .user_id(comment.getUser_id())
                .content(comment.getContent())
                .build();
        UserResponse userResponse = webClient.get()
                .uri(userUrl + "/" + comment.getUser_id())
                .retrieve()
                .bodyToFlux(UserResponse.class)
                .blockFirst();
        log.info("{}", userResponse);
        commentResponse.setUser(userResponse);

        return commentResponse;
    }
}
