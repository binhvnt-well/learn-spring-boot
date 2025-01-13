package com.devteria.identity_sevice.service;

import com.devteria.identity_sevice.dto.request.UserCreationRequest;
import com.devteria.identity_sevice.dto.request.UserUpdateRequest;
import com.devteria.identity_sevice.entity.User;
import com.devteria.identity_sevice.exception.AppException;
import com.devteria.identity_sevice.exception.ErrorCode;
import com.devteria.identity_sevice.mapper.UserMapper;
import com.devteria.identity_sevice.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User createUsers(UserCreationRequest request) {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public User updateUsers(String id, UserUpdateRequest request) {
        User user = getUserById(id);
        userMapper.updateUser(user, request);

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
