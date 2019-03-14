package com.excilys.cdb.vue;


import java.sql.Date;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class CLI {
	
	private Scanner scanner;
	
	public CLI() {
		scanner = new Scanner(System.in);
	}
	
	
	public int menu() {
		int input;
		System.out.println(" -- Main Menu --");
		System.out.println("Type 0 to exit.");
		System.out.println("Type 1 to see the computer list.");
		System.out.println("Type 2 to see the company list.");
		System.out.println("Type 3 to see details about a computer.");
		System.out.println("Type 4 to create a computer.");
		System.out.println("Type 5 to update a computer.");
		System.out.println("Type 6 to delete a computer.");
		
		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			input = -1;
		}
		return input;
	}
	
	public void printCompanyList(Page<Company> page) {
		if (page.getNbLine()==0) {
			System.out.println("Sorry, we found no company.");
		} else {
			int input;
			do {
				System.out.println("----- Company List -----");
				System.out.println("1 : previous / 2 : next / 0 close");
				for (Company comp : page.getPage()) {
					System.out.println(comp.getId() + " : " + comp.getName());
				}
				
				try {
					input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
				} catch (NumberFormatException e) {
					input = -1;
				}
				
				if (input==1) {
					if (!page.previous()) {
						System.out.println("No page before");
					}
				}
				
				if (input==2) {
					if (!page.next()) {
						System.out.println("No page after");
					}
				}
				
			} while (input!=0);
		}
	}
	
	public void printComputerList(Page<Computer> page) {
		if (page.getNbLine()==0) {
			System.out.println("Sorry, we found no computer.");
		} else {
			int input;
			do {
				System.out.println("----- Computer List -----");
				System.out.println("1 : previous / 2 : next / 0 close");
				for (Computer comp : page.getPage()) {
					System.out.println(comp.getId() + " : " + comp.getName());
				}
				
				try {
					input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
				} catch (NumberFormatException e) {
					input = -1;
				}
				
				if (input==1) {
					if (!page.previous()) {
						System.out.println("No page before");
					}
				}
				
				if (input==2) {
					if (!page.next()) {
						System.out.println("No page after");
					}
				}
				
			} while (input!=0);
		}
	}
	
	public int enterId() {
		int input;
		System.out.println("Please enter the id of the computer.");
		System.out.println("Enter 0 to go back to main menu.");

		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			input = 0;
		}
		return input;
	}
	
	public void printComputerDetails(Computer res) {		
		if (res==null) {
			System.out.println("No computer with this number in the database.");
		} else {
		System.out.println(" -- Informations about computer number " + res.getId() + " --");
		System.out.println("Name : " + res.getName());
		System.out.println("Introduction Date : " + ((res.getIntroduced()==null) ? "Unknown" : res.getIntroduced()));
		System.out.println("Discontinuation Date : " + ((res.getDiscontinued()==null) ? "Unknown" : res.getDiscontinued()));
		System.out.println("Company Name : " + ((res.getCompany()==null) ? "Unknown" : res.getCompany().getName()));	
		}
	}
	
	public Computer enterComputer(boolean isACreation) {
		
		System.out.println("Please enter the name of the computer.");
		String name = scanner.nextLine();
		
		int id = 0;
		
		if (!isACreation) {
			System.out.println("Please enter the id of the commputer.");
			System.out.println("Enter 0 if you don't know it.");
			


			try {
				id = Integer.parseInt(scanner.nextLine().split(" ")[0]);
			} catch (NumberFormatException e) {
				id = 0;
			}
		}	
		
		System.out.println("Please enter the introduction date (use format yyyy-mm-dd).");
		System.out.println("Enter 0 if you don't know it.");
		
		Date introduced;

		try {
			introduced = Date.valueOf(scanner.nextLine());
		} catch (IllegalArgumentException e) {
			introduced = null;
		}
		
		System.out.println("Please enter the discontinuation date (use format yyyy-mm-dd).");
		System.out.println("Enter 0 if you don't know it.");
		
		Date discontinued;

		try {
			discontinued = Date.valueOf(scanner.nextLine());
		} catch (IllegalArgumentException e) {
			discontinued = null;
		}
		
		Computer computer = new Computer(id, name, introduced, discontinued, null);
		return computer;
	}

	public int enterCompanyId() {
		int input;
		System.out.println("Please enter the id of the company.");
		System.out.println("Enter 0 if you don't know it.");

		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			input = 0;
		}
		return input;
	}
	
	
	public void computerCreation(boolean success, int id) {
		if (success) {
			System.out.println("Computer number " + id + " successfully created");
		} else {
			System.out.println("Error while creating your computer");
		}
	}
	
	public void computerDeletion(boolean success, int id) {
		if (success) {
			System.out.println("Computer number " + id + " successfully deleted");
		} else {
			System.out.println("Error while deleting your computer");
		}
	}
	
	public void computerUpdate(boolean success, int id) {
		if (success) {
			System.out.println("Computer number " + id + " successfully updated");
		} else {
			System.out.println("Error while updating your computer");
		}
	}

	public void close() {
		System.out.println("Good Bye");
		scanner.close();
	}
	
}
