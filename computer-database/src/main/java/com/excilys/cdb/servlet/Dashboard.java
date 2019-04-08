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
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

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
	    Page<ComputerDTO> displayer = new Page<ComputerDTO>(new ArrayList<>());
		
	    
	    String search = request.getParameterMap().containsKey("search") ? request.getParameter("search") : "";
	    
	    int sort = request.getParameterMap().containsKey("sort") ? Integer.parseInt(request.getParameter("sort")) : 0;
	    
	    
	    try {
			ComputerService service = new ComputerService();
			List<Computer> computerList = service.search(search,sort);
			List<ComputerDTO> computerData = new ArrayList<>();
			for (Computer computer : computerList) {
				computerData.add(new ComputerDTO(computer));
			}
			displayer = new Page<>(computerData,10);
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
			if (displayer.getIndex()==-1) {
				displayer.setIndex((displayer.getNbLine()-1)/displayer.getPageSize());
			} else {
				displayer.setIndex(0);
			}	
		}
		
		request.setAttribute("nbPage", displayer.getNbLine());
		request.setAttribute("list",displayer.getPage());
		request.setAttribute("current", displayer.getIndex()+1);
		request.setAttribute("size", displayer.getPageSize());
		request.setAttribute("search", search);
		request.setAttribute("sort", sort);
		
		getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ComputerService service = new ComputerService();
		
		String selection = request.getParameter("selection");
		String[] idList = selection.split(",");
		try {
			for (String id : idList) {
				service.delete(Integer.parseInt(id));
			}
		} catch (DAOException e){
			/* TODO something*/
		}
		doGet(request, response);
	}

}
