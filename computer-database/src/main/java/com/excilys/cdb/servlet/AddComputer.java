package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyService service = new CompanyService();
		List<Company> list = service.getList();
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
		
		ComputerService service = new ComputerService();
		ComputerMapper mapper = new ComputerMapper();
		try {
			service.create(mapper.getComputer(dto));
			request.setAttribute("res", "Success");
		} catch (DAOException e) {
			request.setAttribute("res", "DB Error");
		} catch (InvalidInputException e) {
			request.setAttribute("res", "Error : " + e.getMessage());
		}
		
		doGet(request, response);
	}
	
}
