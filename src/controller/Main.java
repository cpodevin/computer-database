package controller;

import java.sql.SQLException;
import java.util.List;

import dao.Requester;

public class Main {
	public static void main(String ... args) {
		System.out.println("Hello World");
		Requester req = new Requester();
		try {
			System.out.println(req.requestComputers().get(20));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
}
