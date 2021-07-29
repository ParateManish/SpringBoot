package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.FinishTask;

public interface IFinishTasksRepository  extends JpaRepository<FinishTask,Integer>{
	
}
