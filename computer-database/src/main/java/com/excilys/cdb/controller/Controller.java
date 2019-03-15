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
		
		int input;
		
		do {
			input = displayer.menu();
			
			switch (input) {
			case 0 :
				displayer.close();
				break;
			case 1 :
				displayer.printComputerList(new Page<Computer>(DAOFactory.getInstance().getComputerDAO().list()));
				break;
			case 2 :
				displayer.printCompanyList(new Page<Company>(DAOFactory.getInstance().getCompanyDAO().list()));
				break;
			case 3 :
				details();
				break;
			case 4 :
				create();
				break;
			case 5 :
				update();
				break;
			case 6 :
				delete();
				break;
			default :
				System.out.println("Your entry match no proposition.");
			}
			
			System.out.println();
			
		} while (input != 0);
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
