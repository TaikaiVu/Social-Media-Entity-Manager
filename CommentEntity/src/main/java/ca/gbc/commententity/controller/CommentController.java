package ca.gbc.commententity.controller;

import ca.gbc.commententity.dto.CommentRequest;
import ca.gbc.commententity.dto.CommentResponse;
import ca.gbc.commententity.service.CommentServiceImpl;
//import ca.gbc.userentity.dto.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest) {
        service.createComment(commentRequest);
    }

    @GetMapping({ "/{postId}" })
    @ResponseStatus(HttpStatus.OK)
    @TimeLimiter(name = "Entry")

    @CircuitBreaker(name = "Entry", fallbackMethod = "resilienceFallback")

    public List<CommentResponse> getComments(@PathVariable("postId") String postId) {
        return service.getComments(postId);
    }

    @PutMapping({ "/{commentId}" })
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,
                                           @RequestBody CommentRequest commentRequest) {
        service.updateComment(commentId, commentRequest);
        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/api/comment/" + commentId);
        return new ResponseEntity<>(header, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({ "/{commentId}" })
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        service.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public CompletableFuture<String> resilienceFallback(RuntimeException _ex) {
        return CompletableFuture.supplyAsync(() -> "FALLBACK: COMMENT PROBLEM");
    }
}