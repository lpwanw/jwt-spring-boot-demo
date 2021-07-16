package com.lpwanw.AppServer.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lpwanw.AppServer.Entity.User;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer>{
//	@Query("SELECT t.title FROM Todo t where t.id = :id") 
//    String findTitleById(@Param("id") Long id);
//	@Query(value = "SELECT u FROM User u where u.username = ?1")
//	User findByUsername1(String username);
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
}
