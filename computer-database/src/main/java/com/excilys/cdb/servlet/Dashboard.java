package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Service;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private Page<ComputerDTO> displayer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			getData();
		} catch (DAOException e) {
			response.sendRedirect("/views/500.html");
		}
		
		String tmp = request.getParameter("page");
		
		if (tmp != null) {
			displayer.setIndex(Integer.parseInt(tmp)-1);
		}
		
		tmp = request.getParameter("size");
		
		if (tmp != null) {
			displayer.setPageSize(Integer.parseInt(tmp));
		}
		
		if (!displayer.isLegal()) {
			displayer.setIndex(0);
		}
		
		request.setAttribute("nbPage", displayer.getNbLine());
		request.setAttribute("list",displayer.getPage());
		request.setAttribute("current", displayer.getIndex()+1);
		request.setAttribute("size", displayer.getPageSize());
		
		getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Service service = new Service();
		
		String selection = request.getParameter("selection");
		String[] idList = selection.split(",");
		try {
			for (String id : idList) {
				service.deleteComputer(Integer.parseInt(id));
			}
		} catch (DAOException e){
			/* TODO something*/
		}
		doGet(request, response);
	}

	private void getData() throws DAOException {
		Service service = new Service();
		List<Computer> computerList = service.getComputerList();
		List<ComputerDTO> computerData = new ArrayList<>();
		for (Computer computer : computerList) {
			computerData.add(new ComputerDTO(computer));
		}
		displayer = new Page<>(computerData,10);
	}
	
}
