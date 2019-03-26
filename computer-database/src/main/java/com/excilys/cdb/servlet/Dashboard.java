package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Service;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int pageSize = 10;
	private int pageNumber = 0;
    private Page<ComputerDTO> displayer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        getData();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String tmp = request.getParameter("page");
		if (tmp != null) {
			switch (tmp) {
			case "previous" :
				displayer.previous();
				pageNumber = displayer.getIndex();
				break;
			case "next" :
				displayer.next();
				pageNumber = displayer.getIndex();
				break;
			default : 
				pageNumber = Integer.parseInt(tmp)-1;
				if (!displayer.isLegal(pageNumber)) {
					pageNumber=0;
				}
				displayer.setIndex(pageNumber);
			}
		}
		
		tmp = request.getParameter("size");
		if (tmp != null) {
			pageNumber = Integer.parseInt(tmp);
			displayer.setPageSize(pageNumber);
			if (!displayer.isLegal(pageNumber)) {
				pageNumber = 0;
				displayer.setIndex(pageNumber);
			}
		}
		
		request.setAttribute("nbPage", displayer.getNbLine());
		request.setAttribute("list",displayer.getPage());
		
		getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void getData() {
		Service service = new Service();
		List<Computer> computerList = service.getComputerList();
		List<ComputerDTO> computerData = new ArrayList<>();
		for (Computer computer : computerList) {
			computerData.add(new ComputerDTO(computer));
		}
		displayer = new Page<>(computerData,pageSize);
	}
	
}
