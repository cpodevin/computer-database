package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.vue.CLI;

public class Controller {

	private CLI displayer;
	
	public Controller() {
		displayer = new CLI();	
	}
	
	public void run() {
		
		CLI.Menu input;
		
		do {
			input = displayer.menu();
			
			switch (input) {
			case Exit :
				displayer.close();
				break;
			case ComputerList :
				try {
					ComputerService service = new ComputerService();
					displayer.printComputerList(new Page<Computer>(service.getList()));
				} catch (DAOException e) {
					displayer.printComputerList(new Page<Computer>(new ArrayList<Computer>()));
				}
				break;
			case CompanyList :
				CompanyService service = new CompanyService();
				displayer.printCompanyList(new Page<Company>(service.getList()));
				break;
			case ComputerDetails :
				details();
				break;
			case CreateComputer :
				create();
				break;
			case UpdateComputer :
				update();
				break;
			case DeleteComputer :
				delete();
				break;
			case DeleteCompany :
				deleteCompany();
			default :
				System.out.println("Your entry match no proposition.");
			}
			
			System.out.println();
			
		} while (input != CLI.Menu.Exit);
	}

	
	private void details() {
		int input;
		ComputerService service = new ComputerService();
		input = displayer.enterId();

		if (input!=0) {
			try {
				displayer.printComputerDetails(service.find(input));
			} catch (DAOException e) {
				displayer.printComputerDetails(Optional.empty());
			}
		}
	}


	
	private void create() {	
		Optional<Computer> computer = displayer.enterComputer(true);
		CompanyService companyService = new CompanyService();
		ComputerService computerService = new ComputerService();
		
		if (computer.isPresent()) {
			Optional<Company> company = companyService.find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			try {
				computerService.create(computer.get());
				displayer.computerCreation(true,computer.get().getId());
			} catch (DAOException e) {
				displayer.computerCreation(false,  0);
			} catch (InvalidInputException e) {
				System.out.println("Invalid Input");
			}
			
		} else {
			displayer.computerCreation(false,  0);
		}
		
		
				
	}

	private void update() {	
		Optional<Computer> computer = displayer.enterComputer(false);
		CompanyService companyService = new CompanyService();
		ComputerService computerService = new ComputerService();
		
		if (computer.isPresent()) {
			Optional<Company> company = companyService.find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			try {	
				computerService.update(computer.get());		
				displayer.computerUpdate(true,computer.get().getId());
			} catch (DAOException e) {
				displayer.computerUpdate(false,  0);
			} catch (InvalidInputException e) {
				System.out.println("Invalid Input");
			}
		} else {
			displayer.computerUpdate(false, 0);
		}
				
	}
	
	
	private void delete() {	
		
		int input = displayer.enterId();
		ComputerService service = new ComputerService();
		
		if (input != 0) {

			try {
				service.delete(input);
				displayer.computerDeletion(true, input);
			} catch (DAOException e) {
				displayer.computerDeletion(false,0);
			}
		} else {
			displayer.computerDeletion(false, 0);
		}
	}
	
	private void deleteCompany() {
		
		int input = displayer.enterId();
		CompanyService service = new CompanyService();
		
		if (input != 0) {
			Optional<Company> company;
			company = service.find(input);
			if (company.isPresent()) {
				try {
					service.delete(company.get());
					System.out.println("Done");
				} catch (DAOException e) {
					System.out.println("Failed");
				}
			} else {
				System.out.println("Nothing to delete");
			}
		}
		
	}
	
}
