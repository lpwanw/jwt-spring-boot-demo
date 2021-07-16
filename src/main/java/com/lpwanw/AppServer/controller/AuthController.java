package com.lpwanw.AppServer.controller;

import com.lpwanw.AppServer.Entity.Role;
import com.lpwanw.AppServer.Entity.User;
import com.lpwanw.AppServer.Model.JwtResponse;
import com.lpwanw.AppServer.Model.LoginRequest;
import com.lpwanw.AppServer.Model.MessageResponse;
import com.lpwanw.AppServer.Model.SignupRequest;
import com.lpwanw.AppServer.Service.iplm.UserDetailsImpl;
import com.lpwanw.AppServer.commom.ERole;
import com.lpwanw.AppServer.commom.JwtUtils;
import com.lpwanw.AppServer.reponsitory.RoleDAO;
import com.lpwanw.AppServer.reponsitory.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//API
//@CrossOrigin(origins = "*", maxAge = 63600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authticateUser(@Validated @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId()
                                                ,userDetails.getUsername()
                                                ,roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest){
        if(userDAO.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erroe: Username is already taken!"));
        }
        //check taken info in here
        User user = new User(signupRequest.getUsername(),encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles==null){
            Role userRole = roleDAO.findByName(ERole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else{
            strRoles.forEach(role->{
                switch (role){
                    case "admin":{
                        Role adminRole = roleDAO.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    }
                    case "mod":{
                        Role modRole = roleDAO.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    }
                    default:
                        Role userRole = roleDAO.findByName(ERole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userDAO.save(user);
        System.out.println("Amazing");
        return ResponseEntity.ok(new MessageResponse("User registerd successfully!"));
    }
}
