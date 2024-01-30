package com.example.friendshipservice.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.friendshipservice.model.FriendList;

public interface FriendListRepository extends MongoRepository<FriendList, String> {
    @DeleteQuery
    void deleteById(String id);
}
