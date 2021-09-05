package com.manish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.FinishTask;

public interface IFinishTasksRepository extends JpaRepository<FinishTask, Integer> {

	public List<FinishTask> findAllByUserName(String username);
}
