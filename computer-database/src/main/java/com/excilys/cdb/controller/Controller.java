package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
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
				try {
					displayer.printComputerList(new Page<Computer>(DAOFactory.getInstance().getComputerDAO().list()));
				} catch (DAOException e) {
					displayer.printComputerList(new Page<Computer>(new ArrayList<Computer>()));
				}
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
			try {
				displayer.printComputerDetails(DAOFactory.getInstance().getComputerDAO().find(input));
			} catch (DAOException e) {
				displayer.printComputerDetails(Optional.empty());
			}
		}
	}


	
	private void create() {	
		Optional<Computer> computer = displayer.enterComputer(true);
		
		
		if (computer.isPresent()) {
			Optional<Company> company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			try {
				DAOFactory.getInstance().getComputerDAO().create(computer.get());
				displayer.computerCreation(true,computer.get().getId());
			} catch (DAOException e) {
				displayer.computerCreation(false,  0);
			}
			
		} else {
			displayer.computerCreation(false,  0);
		}
		
		
				
	}

	private void update() {	
		Optional<Computer> computer = displayer.enterComputer(false);
		
		if (computer.isPresent()) {
			Optional<Company> company = DAOFactory.getInstance().getCompanyDAO().find(displayer.enterCompanyId());
			computer.get().setCompany(company);
			
			try {	
				DAOFactory.getInstance().getComputerDAO().update(computer.get());		
				displayer.computerUpdate(true,computer.get().getId());
			} catch (DAOException e) {
				displayer.computerUpdate(false,  0);
			}
		} else {
			displayer.computerUpdate(false, 0);
		}
				
	}
	
	
	private void delete() {	
		
		int input = displayer.enterId();		
		
		if (input != 0) {
			Optional<Computer> computer;
			try {
				computer = DAOFactory.getInstance().getComputerDAO().find(input);
			} catch (DAOException e1) {
				computer = Optional.empty();
			}
			if (computer.isPresent()) {
				 try {
					 DAOFactory.getInstance().getComputerDAO().delete(computer.get());
					 displayer.computerDeletion(true, computer.get().getId());
				 } catch (DAOException e) {
					 displayer.computerDeletion(false,0);
				 }
			} else {
				displayer.computerDeletion(false, 0);
			}
				
		}
	}
	
}
