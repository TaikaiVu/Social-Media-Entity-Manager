package ca.gbc.userentity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String email;
    private String names;

    private String password;


}



// dto is mainly to filtrate the data, especially used with large data where you only want to
// retrieve a specific data but not all of it, so we use dto to control the flow of the data

// This file: To modify data: create or update data -> limit the data field that can be edited so that we could have the control of the data
// POST request
