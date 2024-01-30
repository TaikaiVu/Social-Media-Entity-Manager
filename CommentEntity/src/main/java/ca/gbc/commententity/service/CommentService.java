package ca.gbc.commententity.service;

import ca.gbc.commententity.dto.CommentRequest;
import ca.gbc.commententity.dto.CommentResponse;

import java.util.List;

public interface CommentService{
    void createComment(CommentRequest commentRequest);

    List<CommentResponse> getComments(String postId);

    void updateComment(Long commentId, CommentRequest commentRequestg);

    void deleteComment(Long commentId);

}
