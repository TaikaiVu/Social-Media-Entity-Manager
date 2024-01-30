package ca.gbc.userentity.service;

import ca.gbc.userentity.dto.Login;
import ca.gbc.userentity.dto.UserRequest;
import ca.gbc.userentity.dto.UserResponse;

public interface UserService {

    void createUser(UserRequest userRequest);

    void updateUser(Long userId, UserRequest userRequest);

    UserResponse getUser(Long userId);

    void deleteUser(Long userId);

    boolean loginUser(Login login);

}



// this is the page that acts as a declaration of what are the methods or function that is required for the task
// we only state and function here to have the overview of the task but not really implementing the logic here
// this is for practice in hierarchy level for the file access permission for the whole team, to limit the access to
// certain people

// we retrieve the logic from the Impl file