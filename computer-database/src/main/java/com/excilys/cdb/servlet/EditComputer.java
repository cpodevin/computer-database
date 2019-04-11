package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 3L;
       
	ComputerService computerService;
	CompanyService companyService;
	ComputerMapper computerMapper;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	computerService = (ComputerService) context.getBean("computerService");
    	companyService = (CompanyService) context.getBean("companyService");
    	computerMapper = (ComputerMapper) context.getBean("computerMapper");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Optional<Computer> computer = computerService.find(id);
			if (computer.isPresent()) {
				ComputerDTO dto = new ComputerDTO(computer.get());
				request.setAttribute("computer", dto);
			} else {
				request.setAttribute("res", "Error while looking for your computer");
			}
		} catch (NumberFormatException | DAOException e) {
			request.setAttribute("res", "Error while looking for your computer");
		}
		
		List<Company> list = companyService.getList();
		request.setAttribute("list_company", list);

		getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		ComputerDTO dto = new ComputerDTO();
		dto.setId(Integer.parseInt(id));
		dto.setName(computerName);
		dto.setIntroduced(introduced);
		dto.setDiscontinued(discontinued);
		dto.setCompanyId(Integer.parseInt(companyId));

		try {
			computerService.update(computerMapper.getComputer(dto));
			request.setAttribute("res", "Success");
		} catch (DAOException e) {
			request.setAttribute("res", "DB Error");
		} catch (InvalidInputException e) {
			request.setAttribute("res", "Error : " + e.getMessage());
		}
		
		doGet(request, response);
	}

}
