package com.excilys.cdb.controller;

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
		Computer computer = displayer.enterComputer(true);
		Company company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
		computer.setCompany(company);
		
		boolean success = DAOFactory.getInstance().getComputerDAO().create(computer);
		
		displayer.computerCreation(success,computer.getId());		
	}

	private void update() {	
		Computer computer = displayer.enterComputer(false);
		Company company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
		computer.setCompany(company);
		
		boolean success = DAOFactory.getInstance().getComputerDAO().update(computer);
		
		displayer.computerUpdate(success,computer.getId());		
	}
	
	
	private void delete() {	
		
		int input = displayer.enterId();		
		
		if (input != 0) {
			Computer computer = DAOFactory.getInstance().getComputerDAO().find(input);
			boolean success = DAOFactory.getInstance().getComputerDAO().delete(computer);	
			displayer.computerDeletion(success,computer.getId());		
		}
	}
	
}
