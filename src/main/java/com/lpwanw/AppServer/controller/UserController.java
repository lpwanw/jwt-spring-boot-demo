package com.lpwanw.AppServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lpwanw.AppServer.Entity.User;
import com.lpwanw.AppServer.Model.UserDTO;
import com.lpwanw.AppServer.reponsitory.UserDAO;


@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;
	@RequestMapping("/user")
	public ResponseEntity<User> getUser(@RequestBody UserDTO user){
		System.out.println(user.getUsername());
		return new ResponseEntity<User>(userDAO.findByUsername(user.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not Found with name: "+ user.getUsername())), HttpStatus.OK);
	}
}
