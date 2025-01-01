package com.devteria.identity_sevice.UserService;

import com.devteria.identity_sevice.dto.request.UserCreationRequest;
import com.devteria.identity_sevice.entity.User;
import com.devteria.identity_sevice.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUsers(UserCreationRequest request) {
        User user = new User();
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }
}
