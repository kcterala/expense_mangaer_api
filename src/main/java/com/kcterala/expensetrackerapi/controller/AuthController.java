package com.kcterala.expensetrackerapi.controller;

import com.kcterala.expensetrackerapi.entity.AuthModel;
import com.kcterala.expensetrackerapi.entity.JwtResponse;
import com.kcterala.expensetrackerapi.entity.User;
import com.kcterala.expensetrackerapi.entity.UserModel;
import com.kcterala.expensetrackerapi.security.CustomUserDetailsService;
import com.kcterala.expensetrackerapi.service.UserService;
import com.kcterala.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {
        
        
        authenticate(authModel.getEmail(),authModel.getPassword());
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password)); 
        }catch(DisabledException e){
            throw new Exception("User Disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credentials");
        }
    }
}
