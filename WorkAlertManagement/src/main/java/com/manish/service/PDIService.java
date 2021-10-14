package com.manish.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.model.Common;
import com.manish.model.PDI;
import com.manish.model.PDIData;
import com.manish.repository.IPDIRepository;

@Service
public class PDIService {
	@Autowired
	private IPDIRepository repo;

	String partName = StringUtils.EMPTY;
	String partNumber = StringUtils.EMPTY;
	String parameter = StringUtils.EMPTY;
	String size = StringUtils.EMPTY;

	public Integer savePDI() {
		PDI pdiTempData = getPDITempData();
		Integer pdiId = repo.save(pdiTempData).getPdiId();
		return pdiId;
	}

	public void addPDIParameter(PDI reqPDI) {
		String partName = reqPDI.getPartName();
		String partNumber = reqPDI.getPartNumber();
		String reqParam = reqPDI.getParameters();
		String reqSize = reqPDI.getSizes();

		if (StringUtils.isNotBlank(partName) && StringUtils.isNotBlank(partNumber)) {
			this.partName = partName;
			this.partNumber = partNumber;
		}

		if (StringUtils.isBlank(parameter))
			parameter = parameter + reqParam + "#" + reqSize;
		else
			parameter = parameter + "-" + reqPDI.getParameters() + "#" + reqPDI.getSizes();
	}

	public List<Common> getPDIDetails() {
		List<Common> list = new ArrayList<Common>();
		if (StringUtils.contains(parameter, "-")) {
			String[] splitParam = parameter.split("-");
			for (int i = 0; splitParam.length > i; i++) {
				Common common = new Common();
				String parameter = splitParam[i];
				String[] splitSize = parameter.split("#");
				if (splitSize.length > 0) {
					common.setParameters(splitSize[0]);
					common.setSizes(splitSize[1]);
					list.add(common);
				}
			}
		} else {
			if (StringUtils.contains(parameter, "#")) {
				Common common = new Common();
				String[] splitParam = parameter.split("#");
				if (splitParam.length > 0) {
					common.setParameters(splitParam[0]);
					common.setSizes(splitParam[1]);
					list.add(common);
				}
			}
		}
		return list;
	}

	public PDI getPDITempData() {
		PDI pdi = new PDI();
		Set<PDIData> pdiDataSet = new HashSet<>();
		pdi.setPartName(partName);
		pdi.setPartNumber(partNumber);
//		pdi.setParameters(parameter);
//		pdi.setSizes(size);
		if (StringUtils.contains(parameter, "-")) {
			String[] splitParam = parameter.split("-");
			for (int i = 0; splitParam.length > i; i++) {
				PDIData pdiData = new PDIData();
				String parameter = splitParam[i];
				String[] splitSize = parameter.split("#");
				if (splitSize.length > 0) {
					pdiData.setParameters(splitSize[0]);
					pdiData.setSizes(splitSize[1]);
					pdiDataSet.add(pdiData);
				}
			}
		}else {
			if (StringUtils.contains(parameter, "#")) {
				PDIData pdiData = new PDIData();
				String[] splitParam = parameter.split("#");
				if (splitParam.length > 0) {
					pdiData.setParameters(splitParam[0]);
					pdiData.setSizes(splitParam[1]);
					pdiDataSet.add(pdiData);
				}
			}
		
		}
		pdi.setPdiData(pdiDataSet);
		return pdi;
	}

	public List<PDI> getAllPDIs() {
		List<PDI> list = repo.findAll();
		return list;
	}

}
