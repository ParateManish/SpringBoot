package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.Subject;

public interface IUserLearnerRepository extends JpaRepository<Subject, Integer> {

}
