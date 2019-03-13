package controller;

import java.util.List;

import dao.CompanyDAO;
import dao.ComputerDAO;
import dao.DAOFactory;
import model.Company;
import model.Computer;

public class Displayer {
	
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	public Displayer() {
		companyDAO = DAOFactory.getInstance().getCompanyDAO();
		computerDAO = DAOFactory.getInstance().getComputerDAO();
	}
	
	public void printCompanyList() {
		List<Company> list = companyDAO.list();
		if (list.size()==0) {
			System.out.println("Sorry, we found no company.");
		} else {
			System.out.println("----- Company List -----");
			for (Company comp : list) {
			System.out.println(comp.getId() + " : " + comp.getName());
			}
		}
	}
	
	public void printComputerList() {
		List<Computer> list = computerDAO.list();
		if (list.size()==0) {
			System.out.println("Sorry, we found no computer.");
		} else {
			System.out.println("----- Computer List -----");
			for (Computer comp : list) {
			System.out.println(comp.getId() + " : " + comp.getName());
			}
		}
	}
	
	public void printComputerDetails(int id) {		
		Computer res = computerDAO.find(id);
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
	
	public void computerCreation(Computer computer) {
		if (computerDAO.create(computer)) {
			System.out.println("Computer number " + computer.getId() + " successfully added");
		} else {
			System.out.println("Error while adding your computer");
		}
	}
	
	public void computerDeletion(Computer computer) {
		if (computerDAO.delete(computer)) {
			System.out.println("Computer number " + computer.getId() + " successfully deleted");
		} else {
			System.out.println("Error while deleting your computer");
		}
	}
	
	public void computerUpdate(Computer computer) {
		if (computerDAO.update(computer)) {
			System.out.println("Computer number " + computer.getId() + " successfully updated");
		} else {
			System.out.println("Error while updating your computer");
		}
	}
	
}
