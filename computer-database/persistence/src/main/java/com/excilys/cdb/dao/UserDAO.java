package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.excilys.cdb.model.Authority;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.QAuthority;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QUser;
import com.excilys.cdb.model.User;

public class UserDAO {

	private JPAQueryFactory queryFactory;

	public JPAQueryFactory getQueryFactory() {
		return queryFactory;
	}

	public void setQueryFactory(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public User findUser(String username) {
		QUser qUser = QUser.user;
		User user = queryFactory.selectFrom(qUser).fetchAll().where(qUser.username.eq(username)).fetchOne();
		
		return user;
	}
	
	public List<Authority> getAuthorities(String username) {
		QAuthority qAuthority = QAuthority.authority1;
		List<Authority> authorities = queryFactory.selectFrom(qAuthority).fetchAll().where(qAuthority.username.username.eq(username)).fetch();
		return authorities;
		
	}
	
	public void addUser(User user) {
		Session session = sessionFactory.openSession();
		session.save(user);
		session.close();
	}
	
	public void addAuthority(Authority authority) {
		Session session = sessionFactory.openSession();
		session.save(authority);
		session.close();
	}
	
}
