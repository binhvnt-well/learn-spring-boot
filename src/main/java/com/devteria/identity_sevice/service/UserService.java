package com.devteria.identity_sevice.service;

import com.devteria.identity_sevice.dto.request.UserCreationRequest;
import com.devteria.identity_sevice.dto.request.UserUpdateRequest;
import com.devteria.identity_sevice.dto.response.UserResponse;
import com.devteria.identity_sevice.entity.User;
import com.devteria.identity_sevice.exception.AppException;
import com.devteria.identity_sevice.exception.ErrorCode;
import com.devteria.identity_sevice.mapper.UserMapper;
import com.devteria.identity_sevice.reponsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // ..
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // ..
public class UserService {
    //    https://www.youtube.com/watch?v=3AIjB50cRzU&ab_channel=Devteria
    //    @Autowired // Sử dụng autowired không phải là 1 best practice.
//    Nên chúng ta sẽ sử dụng 1 cái anotation là @RequiredArgsConstructor.
//    Nó sẽ tạo 1 constructor cho tất cả các biến mà bạn define là final.
    UserRepository userRepository;

    UserMapper userMapper;

    public User createUsers(UserCreationRequest request) {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User is not found")));
    }

    public UserResponse updateUsers(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User is not found"));
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
