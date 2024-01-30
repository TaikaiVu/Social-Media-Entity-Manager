package com.example.friendshipservice.service;

import com.example.friendshipservice.dto.FriendListResponse;

public interface FriendshipService {
    void addFriend(String userId, String otherUserId);

    void removeFriend(String userId, String otherUserId);

    FriendListResponse getFriendList(String userId);
}
