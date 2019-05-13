package com.excilys.cdb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.UserService;
import com.querydsl.jpa.codegen.JPADomainExporter;



@Controller
public class ComputerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerController.class);
	
	private ComputerService computerService;
	
	private CompanyService companyService;
	
	private ComputerMapper computerMapper;
	
	private ComputerDTOValidator validator;
	
	private SessionFactory sessionFactory;
	
	private UserService userService;
	
	public ComputerController(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper, ComputerDTOValidator validator, SessionFactory sessionFactory, UserService userService) {
		this.computerService = computerService;
		this.companyService  =  companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
		this.sessionFactory = sessionFactory;
		this.userService = userService;
		
		logger.warn("--------------------------------------------------------");
		Session session = this.sessionFactory.openSession();
		try {
			JPADomainExporter domainExporter = new JPADomainExporter(new File("persistence/src/main/java"), session.getMetamodel());
			domainExporter.execute();
		} catch (IOException e) {
			logger.warn("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			e.printStackTrace();
		}
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
			model.addAttribute("res", "Error while looking for your computers");
			return "500";
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
	
	@PostMapping({"/dashboard"})
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
			model.addAttribute("res", "Error while deleting your computers");
			return "500";
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
			return "500";
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
	
	@GetMapping({"/create"})
	public String createGet(Model model) {
		return "create";
	}
	
	@PostMapping({"/create"})
	public String createPost(@RequestParam(value="username", required=true, defaultValue="") String username, 
							 @RequestParam(value="password", required=true, defaultValue="") String password, Model model) {
		
		userService.addUser(new User(username, password, true), "ROLE_USER");
		
		return "redirect:/mvc/dashboard";
	}

	@PostMapping("/auth")
	public String auth(@RequestParam(value="username", required=true, defaultValue="") String username, 
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		logger.error("Credentials : "+username+ " "+password);
		return "redirect:/mvc/dashboard";
	}
	
	@GetMapping({"/*"})
	public String error(Model model) {
		return "404";
	}
	
	
}
