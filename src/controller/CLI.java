package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import dao.CompanyDAO;
import dao.ComputerDAO;
import dao.DAOFactory;
import model.Company;
import model.Computer;
import vue.Displayer;

public class CLI {

	private Displayer displayer;
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;

	
	public CLI() {
		displayer = new Displayer();
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
		Computer computer = displayer.enterComputer();
		
		boolean success = computerDAO.create(computer);
		
		displayer.computerCreation(success,computer.getId());		
	}

	private void update() {	
		Computer computer = displayer.enterComputer();
		
		boolean success = computerDAO.update(computer);
		
		displayer.computerUpdate(success,computer.getId());		
	}
	
	
	private void delete() {	
		Computer computer = displayer.enterComputer();
		
		boolean success = computerDAO.delete(computer);
		
		displayer.computerDeletion(success,computer.getId());		
	}
	
}
