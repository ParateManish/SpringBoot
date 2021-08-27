package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.UserLogin;

public interface IUserLoginRepository extends JpaRepository<UserLogin, Integer> {

	public UserLogin findByUserName(String username);

	public UserLogin findByPassword(String password);

	public UserLogin findByUserNameAndPassword(String username, String password);
}
