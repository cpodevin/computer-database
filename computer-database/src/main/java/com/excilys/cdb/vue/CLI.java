package com.excilys.cdb.vue;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class CLI {

	private static final Logger logger = LoggerFactory.getLogger(CLI.class);

	private Scanner scanner;

	public enum Menu {
		Exit, ComputerList, CompanyList, ComputerDetails, CreateComputer, UpdateComputer, DeleteComputer, InvalidInput
	};

	public enum PageMenu {
		Close, Previous, Next, InvalidInput
	};

	public CLI() {
		scanner = new Scanner(System.in);
	}

	public Menu menu() {
		Menu res;

		System.out.println(" -- Main Menu --");
		System.out.println("Type 0 to exit.");
		System.out.println("Type 1 to see the computer list.");
		System.out.println("Type 2 to see the company list.");
		System.out.println("Type 3 to see details about a computer.");
		System.out.println("Type 4 to create a computer.");
		System.out.println("Type 5 to update a computer.");
		System.out.println("Type 6 to delete a computer.");

		try {
			int input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
			if (0 <= input && 6 >= input) {
				res = Menu.values()[input];
			} else {
				logger.warn("Invalid input");
				res = Menu.InvalidInput;
			}
		} catch (NumberFormatException e) {
			logger.warn("Invalid input");
			res = Menu.InvalidInput;
		}
		return res;
	}

	public void printCompanyList(Page<Company> page) {
		if (page.getNbLine() == 0) {
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
					logger.warn("Invalid input");
					input = -1;
				}

				if (input == 1) {
					if (!page.previous()) {
						System.out.println("No page before");
					}
				}

				if (input == 2) {
					if (!page.next()) {
						System.out.println("No page after");
					}
				}

			} while (input != 0);
		}
	}

	public void printComputerList(Page<Computer> page) {
		if (page.getNbLine() == 0) {
			System.out.println("Sorry, we found no computer.");
		} else {
			PageMenu menuInput;
			do {
				System.out.println("----- Computer List -----");
				System.out.println("1 : previous / 2 : next / 0 close");
				for (Computer comp : page.getPage()) {
					System.out.println(comp.getId() + " : " + comp.getName());
				}

				try {
					int input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
					if (0 <= input && 2 >= input) {
						menuInput = PageMenu.values()[input];
					} else {
						logger.warn("Invalid input");
						menuInput = PageMenu.InvalidInput;
					}
				} catch (NumberFormatException e) {
					logger.warn("Invalid input");
					menuInput = PageMenu.InvalidInput;
				}

				if (menuInput == PageMenu.Previous) {
					if (!page.previous()) {
						System.out.println("No page before");
					}
				}

				if (menuInput == PageMenu.Next) {
					if (!page.next()) {
						System.out.println("No page after");
					}
				}

			} while (menuInput != PageMenu.Close);
		}
	}

	public int enterId() {
		int input;
		System.out.println("Please enter the id of the computer.");
		System.out.println("Enter 0 to go back to main menu.");

		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			logger.warn("Invalid input");
			input = 0;
		}
		return input;
	}

	public void printComputerDetails(Optional<Computer> computer) {
		if (computer.isPresent()) {
			System.out.println(" -- Informations about computer number " + computer.get().getId() + " --");
			System.out.println("Name : " + computer.get().getName());
			System.out.println("Introduction Date : "
					+ ((computer.get().getIntroduced() == null) ? "Unknown" : computer.get().getIntroduced()));
			System.out.println("Discontinuation Date : "
					+ ((computer.get().getDiscontinued() == null) ? "Unknown" : computer.get().getDiscontinued()));
			System.out.println("Company Name : "
					+ ((computer.get().getCompany().isPresent()) ? computer.get().getCompany().get().getName() : "Unknown" ));
		} else {
			System.out.println("No computer with this number in the database.");
		}
	}

	public Optional<Computer> enterComputer(boolean isACreation) {

		System.out.println("Please enter the name of the computer.");
		String name = scanner.nextLine();

		int id = 0;

		if (!isACreation) {
			System.out.println("Please enter the id of the commputer.");
			System.out.println("Enter 0 if you don't know it.");

			try {
				id = Integer.parseInt(scanner.nextLine().split(" ")[0]);
			} catch (NumberFormatException e) {
				logger.warn("Invalid input");
				id = 0;
			}
			if (id==0) {
				System.out.println("You need a valid id to update a computer.");
				return Optional.empty();
			}
		}

		System.out.println("Please enter the introduction date (use format yyyy-mm-dd).");
		System.out.println("Enter 0 if you don't know it.");

		Date introduced;

		try {
			introduced = Date.valueOf(scanner.nextLine());
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid input");
			introduced = null;
		}

		System.out.println("Please enter the discontinuation date (use format yyyy-mm-dd).");
		System.out.println("Enter 0 if you don't know it.");

		Date discontinued;

		try {
			discontinued = Date.valueOf(scanner.nextLine());
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid input");
			discontinued = null;
		}

		Computer computer = new Computer(id, name, introduced, discontinued, null);
		return Optional.of(computer);
	}

	public int enterCompanyId() {
		int input;
		System.out.println("Please enter the id of the company.");
		System.out.println("Enter 0 if you don't know it.");

		try {
			input = Integer.parseInt(scanner.nextLine().split(" ")[0]);
		} catch (NumberFormatException e) {
			logger.warn("Invalid input");
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
