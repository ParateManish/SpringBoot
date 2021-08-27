package com.manish.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.model.Subject;
import com.manish.repository.IUserLearnerRepository;

@Service
public class UserLearnerDBService {

	@Autowired
	private IUserLearnerRepository repo;

	public String saveSubject(Subject subject) {
		String returnValue = StringUtils.EMPTY;
		Integer id = repo.save(subject).getId();
		if (id != null)
			returnValue = subject.getSubjectName() + " Subject is Added";
		return returnValue;
	}

	public List<Subject> getAllSubjectLists() {
		return repo.findAll();
	}

}
