package com.excilys.cdb.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

	public ComputerMapper() { }
	
	public Computer getComputer(ComputerDTO dto) {
		
		Computer res = new Computer();
		
		res.setId(dto.getId());
		res.setName(dto.getName());
		try {
			res.setIntroduced(Date.valueOf(dto.getIntroduced()));
		} catch (IllegalArgumentException e) {
			res.setIntroduced(null);
		}
		try {
			res.setDiscontinued(Date.valueOf(dto.getDiscontinued()));
		} catch (IllegalArgumentException e) {
			res.setDiscontinued(null);
		}
		res.setCompany(dto.getCompanyId()!=0 ? new Company(dto.getCompanyId(),dto.getCompany()) : null);
		
		return res;
	}

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Computer(rs.getInt("c.id"), rs.getString("c.name"), rs.getDate("c.introduced"), rs.getDate("c.discontinued"), rs.getInt("c.company_id")!=0 ? new Company(rs.getInt("c.company_id"),rs.getString("d.name")) : null);
	}

}
