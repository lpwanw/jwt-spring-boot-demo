package com.lpwanw.AppServer.reponsitory;

import com.lpwanw.AppServer.Entity.Role;
import com.lpwanw.AppServer.commom.ERole;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);
}
