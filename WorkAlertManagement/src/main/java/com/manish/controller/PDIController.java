package com.manish.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manish.model.Common;
import com.manish.model.PDI;
import com.manish.service.PDIService;

@Controller
@RequestMapping("/pdi")
public class PDIController {

	private PDIService service;

	@Autowired
	public PDIController(PDIService service) {
		super();
		this.service = service;
	}

	@GetMapping("/getPDIPage")
	public String getPDIPage(Model model) {
		PDI pdiTempData = service.getPDITempData();
		List<Common> pdiDetails = service.getPDIDetails();
		model.addAttribute("pdiDetails", pdiDetails);
		if(StringUtils.isNotBlank(pdiTempData.getPartName()) &&
				StringUtils.isNotBlank(pdiTempData.getPartNumber())) {
			model.addAttribute("partName", pdiTempData.getPartName());
			model.addAttribute("partNumber", pdiTempData.getPartNumber());
		}
		return "addPDI";
	}

	@PostMapping("/addPDI")
	public String addPDI(@ModelAttribute PDI pdi, Model model){
		service.addPDIParameter(pdi);
		PDI pdiTempData = service.getPDITempData();
		List<Common> pdiDetails = service.getPDIDetails();
		model.addAttribute("pdiDetails", pdiDetails);
		if(StringUtils.isNotBlank(pdiTempData.getPartName()) &&
				StringUtils.isNotBlank(pdiTempData.getPartNumber())) {
			model.addAttribute("partName", pdiTempData.getPartName());
			model.addAttribute("partNumber", pdiTempData.getPartNumber());
		}
		return "addPDI";
	}

	@GetMapping("/savePDI")
	public String savePDI(Model model) {
		Integer pdiId = service.savePDI();
		model.addAttribute("pdiId",pdiId);
		return "addPDI";
	}
	
	@GetMapping("/")
	public String pdiHomePage() {
		return "pdiHomePage";
	}
	
	@GetMapping("/getPDI")
	public String getPDI(Model model) {
		
		return null;
	}
	
	@GetMapping("/getAllPDIs")
	public String getAllPDIs(Model model) {
		List<PDI> list = service.getAllPDIs();
		list.forEach(System.err::println);
		if(!list.isEmpty() || list!=null) {
			String partName = list.get(0).getPartName();
			String partNumber = list.get(0).getPartNumber();
					
			model.addAttribute("pdiList",list);
			model.addAttribute("partName",partName);
			model.addAttribute("partNumber",partNumber);
		}else {
			model.addAttribute("pdiList","Empty List");
		}
		return "allPDIs";
	}
	
	@GetMapping("getRefreshPDI")
	public String getRefreshPDI() {
		
		return null;
	}

}
