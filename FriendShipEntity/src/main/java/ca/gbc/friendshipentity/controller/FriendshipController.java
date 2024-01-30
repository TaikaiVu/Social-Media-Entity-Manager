package com.example.friendshipservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.friendshipservice.dto.FriendListResponse;
import com.example.friendshipservice.dto.FriendRequest;
import com.example.friendshipservice.service.FriendshipService;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/friendship")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void addFriend(@RequestBody FriendRequest friendRequest) {
        friendshipService.addFriend(friendRequest.getUserId(), friendRequest.getOtherUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@RequestBody FriendRequest request) {
        friendshipService.removeFriend(request.getUserId(), request.getOtherUserId());
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<FriendListResponse> getFriendList(@PathVariable("userId") String userId) {
        return CompletableFuture.supplyAsync(() -> friendshipService.getFriendList(userId));
    }
}
