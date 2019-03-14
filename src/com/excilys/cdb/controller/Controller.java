package com.excilys.cdb.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.vue.CLI;

public class Controller {

	private CLI displayer;
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;

	
	public Controller() {
		displayer = new CLI();
		companyDAO = DAOFactory.getInstance().getCompanyDAO();
		computerDAO = DAOFactory.getInstance().getComputerDAO();	
		
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
				displayer.printComputerList(computerDAO.list());
				break;
			case 2 :
				displayer.printCompanyList(companyDAO.list());
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
			displayer.printComputerDetails(computerDAO.find(input));
		}
	}


	
	private void create() {	
		Computer computer = displayer.enterComputer(true);
		Company company = companyDAO.find(displayer.enterCompanyId());
		computer.setCompany(company);
		
		boolean success = computerDAO.create(computer);
		
		displayer.computerCreation(success,computer.getId());		
	}

	private void update() {	
		Computer computer = displayer.enterComputer(false);
		Company company = companyDAO.find(displayer.enterCompanyId());
		computer.setCompany(company);
		
		boolean success = computerDAO.update(computer);
		
		displayer.computerUpdate(success,computer.getId());		
	}
	
	
	private void delete() {	
		
		int input = displayer.enterId();		
		
		if (input != 0) {
			Computer computer = computerDAO.find(input);
			boolean success = computerDAO.delete(computer);	
			displayer.computerDeletion(success,computer.getId());		
		}
	}
	
}
