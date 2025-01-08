package com.devteria.identity_sevice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

//Refer https://www.youtube.com/watch?v=3AIjB50cRzU&ab_channel=Devteria
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;

    @Size(min = 6, max = 16, message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;
    String email;
    String phone;
    String dob;

}
