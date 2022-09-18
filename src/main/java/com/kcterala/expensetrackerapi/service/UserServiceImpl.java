package com.kcterala.expensetrackerapi.service;

import com.kcterala.expensetrackerapi.entity.User;
import com.kcterala.expensetrackerapi.entity.UserModel;
import com.kcterala.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.kcterala.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.kcterala.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder bcrptEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserModel user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistsException("User is already register with email "+ user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        newUser.setPassword(bcrptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser() {
        Long id = getLoggedInUser().getId();
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found for the id : "+id));
    }

    public User updateUser(UserModel user){
        User existingUser = readUser();

        existingUser.setName(user.getName() !=null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail()!=null? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword()!=null?bcrptEncoder.encode(user.getPassword()):existingUser.getPassword());
        existingUser.setAge(user.getAge()!=null? user.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);
    }
    @Override
    public User getLoggedInUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email "+email));
    }
}
