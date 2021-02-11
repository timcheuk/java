package com.timhappyjava.springsecurity.user;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
//public interface UserRepository extends CrudRepository<User, Integer> {
	
	//if you extends from CrudRepository need to write query and code like below
	//@Query("SELECT u FROM User u WHERE u.username = :username")
	//public User findByUsername(@Param("username") String username);
	User findByUsername(String username);
}
