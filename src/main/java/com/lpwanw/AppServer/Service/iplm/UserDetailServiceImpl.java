package com.lpwanw.AppServer.Service.iplm;

import com.lpwanw.AppServer.Entity.User;
import com.lpwanw.AppServer.reponsitory.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not Found with name: "+ username));
        return UserDetailsImpl.build(user);
    }
}
