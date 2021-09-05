package com.manish.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.Task;

public interface ITaskRepository extends JpaRepository<Task, Integer> {

	public List<Task> findByTaskDate(Date date);

	public List<Task> findAllByUsername(String username);
}
