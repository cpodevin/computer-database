package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.Company;
import model.Computer;

public class Main {
	public static void main(String ... args) {
		System.out.println("Hello World");
		Displayer displayer = new Displayer();
		displayer.printCompanyList();
		System.out.println("");
		displayer.printComputerList();
		System.out.println("");
		displayer.printComputerDetails(1);
		System.out.println("");
		Timestamp time = Timestamp.valueOf(LocalDateTime.now());
		Company decoy = new Company(1,"IDC");
		Computer test = new Computer(577, "Test", time, null, decoy);
		displayer.computerUpdate(test);
		//System.out.println(test.getId());
		System.out.println("");
		test = new Computer(578,"IDC");
		//displayer.computerDeletion(test);
	}
}
