package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

import dao.DAOFactory;
import model.Company;
import model.Computer;

public class CLI {

	private Displayer displayer;
	private Scanner scanner;
	
	public CLI() {
		displayer = new Displayer();
		scanner = new Scanner(System.in);
	}
	
	public void run() {
		
		
		int input;
		
		do {
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
			
			switch (input) {
			case 0 :
				System.out.println("Good Bye.");
				break;
			case 1 :
				displayer.printComputerList();
				break;
			case 2 :
				displayer.printCompanyList();
				break;
			case 3 :
				details();
				break;
			case 4 :
				create();
				break;
			default :
				System.out.println("Your entry match no proposition.");
			}
			
			System.out.println();
			
		} while (input != 0);
		
		scanner.close();
	}
	
	private void details() {
		int input;
		
		System.out.println("Please enter the id of the computer you want to see.");
		System.out.println("Enter 0 to go back to main menu.");

		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			input = 0;
		}

		if (input!=0) {
			displayer.printComputerDetails(input);
		}
	}
	
	private void create() {
		
		int id;
		String name;
		
		System.out.println("Please enter the name of the new computer.");
		name = scanner.nextLine();
		
		System.out.println("Please enter the id of th company.");
		System.out.println("Enter 0 if you don't know it.");
		
		try {
			id = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			id = 0;
		}
		
		Timestamp time = Timestamp.valueOf(LocalDateTime.now());
		Company company = DAOFactory.getInstance().getCompanyDAO().find(id);
		Computer computer = new Computer(0, name, time, null, company);
		
		displayer.computerCreation(computer);
		
		
		
	}
	
}
