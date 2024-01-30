package com.example.friendshipservice.service;

import com.example.friendshipservice.dto.FriendListResponse;
import com.example.friendshipservice.model.FriendList;
import com.example.friendshipservice.repository.FriendListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendListRepository friendListRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void addFriend(String userId, String otherUserId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        FriendList friendList = mongoTemplate.findOne(query, FriendList.class);

        if (friendList == null) {
            friendList = friendListRepository.save(FriendList.builder().userId(userId).friends(new ArrayList<>()).build());
        }

        friendList.getFriends().add(otherUserId);
        friendListRepository.save(friendList);
    }

    @Override
    public void removeFriend(String userId, String otherUserId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        FriendList friendList = mongoTemplate.findOne(query, FriendList.class);

        if (friendList == null) {
            return;
        }

        friendList.getFriends().remove(otherUserId);
        friendListRepository.save(friendList);
    }

    @Override
    public FriendListResponse getFriendList(String userId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        FriendList friendList = mongoTemplate.findOne(query, FriendList.class);
        return mapToFriendList(friendList);
    }

    private FriendListResponse mapToFriendList(FriendList friendList) {
        return FriendListResponse.builder()
                .userId(friendList.getUserId())
                .friends(friendList.getFriends())
                .build();
    }
}
