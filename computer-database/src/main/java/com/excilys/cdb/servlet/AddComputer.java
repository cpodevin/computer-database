package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.Main;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.Service;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	private List<Company> list;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        getData();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("list", list);
		
		getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		ComputerDTO dto = new ComputerDTO();
		dto.setName(computerName);
		dto.setIntroduced(introduced);
		dto.setDiscontinued(discontinued);
		dto.setCompanyId(Integer.parseInt(companyId));
		
		Service service = new Service();
		ComputerMapper mapper = new ComputerMapper();
		request.setAttribute("res", service.createComputer(mapper.getComputer(dto)));
		
		doGet(request, response);
	}

	private void getData() {
		Service service = new Service();
		list = service.getCompanyList();
	}
	
}
