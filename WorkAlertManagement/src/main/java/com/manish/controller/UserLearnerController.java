package com.manish.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manish.model.Subject;
import com.manish.service.UserLearnerDBService;

@Controller
@RequestMapping(value = "/learner")
public class UserLearnerController {

	private static final String REGISTER_SUBJECT_PAGE = "registerSubject";
	private static final String ALL_SUBJECTS_LIST_PAGE = "allSubjectsListPage";
	private static final String RESULT_MESSAGE_MODEL = "resultMsg";
	private static final String ALL_SUBJECTS_LIST_MESSAGE_MODEL = "allSubjectsListPage";

	private UserLearnerDBService dbService;

	@Autowired
	public UserLearnerController(UserLearnerDBService dbService) {
		this.dbService = dbService;
	}

	@GetMapping("/showSubjectPage")
	public String showregisterSubjectPage() {
		return REGISTER_SUBJECT_PAGE;
	}

	@PostMapping("/register")
	public String saveSubject(@ModelAttribute Subject subject, Model model) {
		subject.setStartDate(new Date());
		subject.setTargetDate(new Date());
		String resultMsg = dbService.saveSubject(subject);
		model.addAttribute(RESULT_MESSAGE_MODEL, resultMsg);
		return REGISTER_SUBJECT_PAGE;
	}

	@GetMapping("/getAllSubjects")
	public String getAllSubjectList(Model model) {
		List<Subject> allSubjectLists = dbService.getAllSubjectLists();
		model.addAttribute(ALL_SUBJECTS_LIST_MESSAGE_MODEL, allSubjectLists);
		return ALL_SUBJECTS_LIST_PAGE;
	}

}
