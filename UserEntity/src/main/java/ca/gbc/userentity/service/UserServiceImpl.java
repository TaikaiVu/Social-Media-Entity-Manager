package ca.gbc.userentity.service;

import ca.gbc.userentity.dto.FriendResponse;
import ca.gbc.userentity.dto.Login;
import ca.gbc.userentity.dto.UserRequest;
import ca.gbc.userentity.dto.UserResponse;
import ca.gbc.userentity.model.User;
import ca.gbc.userentity.repository.UserRepository;
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
public class UserServiceImpl implements UserService {
    //
    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${friendship.service.url}")
    private String friendshipUrl;
    //.


    @Override
    public void createUser(UserRequest user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(Long userId, UserRequest userRequest) {
        // log.info("Updating user {}", userId);
        User user = User.builder()
                .id(userId)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
//
        if (user.isPresent() == true) {
            User rettUser = user.get();
            log.info("{}", rettUser);
            UserResponse response = mapToUserResponse(rettUser);
            FriendResponse friendListResponse = webClient.get()
                    .uri(friendshipUrl + "/" + userId)
                    .retrieve()
                    .bodyToFlux(FriendResponse.class)
                    .blockFirst();

            List<UserResponse> friendList = new ArrayList<UserResponse>(response.getFriendList().size());
            for (int i = 0; i < friendListResponse.getFriends().size(); i++) {
                String id = friendListResponse.getFriends().get(i);
                Long uid = Long.valueOf(id);
                Optional<User> friend = userRepository.findById(uid);
                if (friend.isPresent() == true) {
                    UserResponse friendResponse = mapToUserResponse(friend.get());
                    friendList.add(friendResponse);
                }
            }

            return response;
        }

        return null;
    }

    @Override
    public boolean loginUser(Login user) {

        User query = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        Optional<User> dbUser = userRepository.findOne(Example.of(query));

        return dbUser.isPresent();
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}