package com.manish.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.model.Task;

public interface ITaskRepository extends JpaRepository<Task, Integer> {
	public List<Task> findByTaskDate(Date date);

//	@Modifying
//	@Query("update Task set status = :status WHERE id = :id")
//	public void addStatus(@Param("status") String status, @Param("id") Integer id);
}
