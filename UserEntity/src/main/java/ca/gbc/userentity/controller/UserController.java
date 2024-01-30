package ca.gbc.userentity.controller;

import ca.gbc.userentity.dto.Login;
import ca.gbc.userentity.dto.UserRequest;
import ca.gbc.userentity.dto.UserResponse;
import ca.gbc.userentity.service.UserServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @GetMapping({ "/{userId}" })
    @ResponseStatus(HttpStatus.OK)
    //
    @CircuitBreaker(name = "Entry", fallbackMethod = "resilienceFallback")
    @TimeLimiter(name = "Entry")
    public CompletableFuture<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return CompletableFuture.supplyAsync(() -> userService.getUser(userId));
    }

    @PostMapping({ "/login" })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        boolean login = userService.loginUser(loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/user/login");
        return new ResponseEntity<String>(String.valueOf(login), headers, HttpStatus.ACCEPTED);
    }

    @PutMapping({ "/{userId}" })
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId,
                                        @RequestBody UserRequest userRequest) {

        userService.updateUser(userId, userRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/user/updateUser/" + userId);

        return new ResponseEntity<ObjectNode>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({ "/{userId}" })
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/user/deletedUser" + userId);

        return new ResponseEntity<ObjectNode>(headers, HttpStatus.NO_CONTENT);
    }


}