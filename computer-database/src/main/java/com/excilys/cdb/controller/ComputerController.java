package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTOValidator;
import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;



@Controller
public class ComputerController {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	private ComputerService computerService;
	
	private CompanyService companyService;
	
	private ComputerMapper computerMapper;
	
	private ComputerDTOValidator validator;
	
	public ComputerController(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper, ComputerDTOValidator validator) {
		this.computerService = computerService;
		this.companyService  =  companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}
	
	@ModelAttribute
	public ComputerDTO initComputerDTO() {
		return new ComputerDTO();
}
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.addValidators(validator);
}
	
	@GetMapping({"/dashboard"})
	public String list(@RequestParam(value="search", required=false, defaultValue="") String search, 
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@RequestParam(value="page", required=false, defaultValue="1") int index,
			@RequestParam(value="size", required=false, defaultValue="10") int size,
			Model model) {
	
		Page<ComputerDTO> displayer = new Page<ComputerDTO>(new ArrayList<>());

	    try {
	    	
			List<Computer> computerList = computerService.search(search,sort);
			List<ComputerDTO> computerData = new ArrayList<>();
			for (Computer computer : computerList) {
				computerData.add(new ComputerDTO(computer));
			}
			displayer = new Page<>(computerData,10);
		} catch (DAOException e) {
			return "404";
		}

	    displayer.setIndex(index-1);
	    displayer.setPageSize(size);
	    
		if (!displayer.isLegal()) {
			if (displayer.getIndex()==-1) {
				displayer.setIndex((displayer.getNbLine()-1)/displayer.getPageSize());
			} else {
				displayer.setIndex(0);
			}	
		}
		
		model.addAttribute("nbPage", displayer.getNbLine());
		model.addAttribute("list",displayer.getPage());
		model.addAttribute("current", displayer.getIndex()+1);
		model.addAttribute("size", displayer.getPageSize());
		model.addAttribute("search", search);
		model.addAttribute("sort", sort);
		return "dashboard";
	}
	
	@PostMapping({"/","/dashboard"})
	public String delete(@RequestParam(value="search", required=false, defaultValue="") String search, 
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@RequestParam(value="page", required=false, defaultValue="1") int index,
			@RequestParam(value="size", required=false, defaultValue="10") int size,
			@RequestParam(value="selection", required=true) String selection,Model model) {
		String[] idList = selection.split(",");
		try {
			for (String id : idList) {
				computerService.delete(Integer.parseInt(id));
			}
		} catch (DAOException e){
			logger.error("DAO Error : ", e);
		}
		return list(search,sort,index,size,model);
		//return "redirect:/mvc/dashboard";
	}
	
	@GetMapping({"/addComputer"})
	public String createForm(Model model) {
		List<Company> list = companyService.getList();
		model.addAttribute("list", list);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "addComputer";
	}
	
	@PostMapping({"/addComputer"})
	public String create(@Validated @ModelAttribute("computerDTO") ComputerDTO dto, Model model) {
		try {
			computerService.create(computerMapper.getComputer(dto));
			model.addAttribute("res","Success");
		} catch (DAOException e) {
			model.addAttribute("res","DB Error");
		} catch (InvalidInputException e) {
			model.addAttribute("res","Error : " + e.getMessage());
		}
		return createForm(model);
	}
			
	@GetMapping({"/editComputer"})
	public String editForm(@RequestParam(value="id", required=true) int id, Model model) {
		Optional<Computer> computer = computerService.find(id);
		
		if (computer.isPresent()) {
			ComputerDTO dto = new ComputerDTO(computer.get());
			model.addAttribute("computer", dto);
		} else {
			model.addAttribute("res", "Error while looking for your computer");
		}
		List<Company> list = companyService.getList();
		model.addAttribute("list_company", list);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "editComputer";
	}
	
	@PostMapping({"/editComputer"})
	public String edit(@Validated @ModelAttribute("computerDTO") ComputerDTO dto, Model model) {
		try {
			computerService.update(computerMapper.getComputer(dto));
			model.addAttribute("res", "Success");
		} catch (DAOException e) {
			model.addAttribute("res", "DB Error");
		} catch (InvalidInputException e) {
			model.addAttribute("res", "Error : " + e.getMessage());
		}
		return editForm(dto.getId(), model);
	}
	
}
