package com.excilys.cdb.controller;

import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
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
				displayer.printComputerList(new Page<Computer>(DAOFactory.getInstance().getComputerDAO().list()));
				break;
			case CompanyList :
				displayer.printCompanyList(new Page<Company>(DAOFactory.getInstance().getCompanyDAO().list()));
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
			default :
				System.out.println("Your entry match no proposition.");
			}
			
			System.out.println();
			
		} while (input != CLI.Menu.Exit);
	}

	
	private void details() {
		int input;
		
		input = displayer.enterId();

		if (input!=0) {
			displayer.printComputerDetails(DAOFactory.getInstance().getComputerDAO().find(input));
		}
	}


	
	private void create() {	
		Optional<Computer> computer = displayer.enterComputer(true);
		
		
		if (computer.isPresent()) {
			Optional<Company> company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			boolean success = DAOFactory.getInstance().getComputerDAO().create(computer.get());
			displayer.computerCreation(success,computer.get().getId());
		} else {
			displayer.computerCreation(false,  0);
		}
		
		
				
	}

	private void update() {	
		Optional<Computer> computer = displayer.enterComputer(false);
		
		if (computer.isPresent()) {
			Optional<Company> company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			boolean success = DAOFactory.getInstance().getComputerDAO().update(computer.get());		
			displayer.computerUpdate(success,computer.get().getId());
		} else {
			displayer.computerUpdate(false, 0);
		}
				
	}
	
	
	private void delete() {	
		
		int input = displayer.enterId();		
		
		if (input != 0) {
			Optional<Computer> computer = DAOFactory.getInstance().getComputerDAO().find(input);
			boolean success;
			if (computer.isPresent()) {
				 success = DAOFactory.getInstance().getComputerDAO().delete(computer.get());	
			} else {
				success = false;
			}
			displayer.computerDeletion(success,computer.isPresent() ? computer.get().getId() : 0);	
		}
	}
	
}
