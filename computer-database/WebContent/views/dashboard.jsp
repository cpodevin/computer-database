<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<title><spring:message code="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.min.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="./dashboard"> <spring:message code="appName"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nbPage}" /> &thinsp; <spring:message code="dashboard.computerFound"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="./dashboard" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="./addComputer"><spring:message code="dashboard.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="computerName"/>
                            <span > 
                            	<a href="./dashboard?sort=1&search=${search}"><i class="fas fa-sort-up"></i></a>
                           	 	<a href="./dashboard?sort=2&search=${search}"><i class="fas fa-sort-down"></i></a>
                           	 </span>
                        </th>
                        <th>
                            <spring:message code="introducedDate"/>
                            <span > 
                            	<a href="./dashboard?sort=3&search=${search}"><i class="fas fa-sort-up"></i></a>
                           	 	<a href="./dashboard?sort=4&search=${search}"><i class="fas fa-sort-down"></i></a>
                           	 </span>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="discontinuedDate"/>
                            <span > 
                            	<a href="./dashboard?sort=5&search=${search}"><i class="fas fa-sort-up"></i></a>
                           	 	<a href="./dashboard?sort=6&search=${search}"><i class="fas fa-sort-down"></i></a>
                           	 </span>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="company"/>
                            <span > 
                            	<a href="./dashboard?sort=7&search=${search}"><i class="fas fa-sort-up"></i></a>
                           	 	<a href="./dashboard?sort=8&search=${search}"><i class="fas fa-sort-down"></i></a>
                           	 </span>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                
                <tbody id="results">
	                <c:forEach var="computer" items="${list}">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="./editComputer?id=${computer.id}" onclick="">${computer.name}</a>
	                        </td>
	                        <td>${computer.introduced}</td>
	                        <td>${computer.discontinued}</td>
	                        <td>${computer.company}</td>
	                    </tr>
                    </c:forEach>                  
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
			<ul class="pagination">
				<li><a href="./dashboard?page=${current-1}&size=${size}&search=${search}&sort=${sort}" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="./dashboard?page=1&size=${size}&search=${search}&sort=${sort}">1</a></li>
				<li><a href="./dashboard?page=2&size=${size}&search=${search}&sort=${sort}">2</a></li>
				<li><a href="./dashboard?page=3&size=${size}&search=${search}&sort=${sort}">3</a></li>
				<li><a href="./dashboard?page=4&size=${size}&search=${search}&sort=${sort}">4</a></li>
				<li><a href="./dashboard?page=5&size=${size}&search=${search}&sort=${sort}">5</a></li>
				<li><a href="./dashboard?page=${current+1}&size=${size}&search=${search}&sort=${sort}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="./dashboard?size=10&search=${search}&sort=${sort}"><button type="button" class="btn btn-default">10</button></a>
            <a href="./dashboard?size=50&search=${search}&sort=${sort}"><button type="button" class="btn btn-default">50</button></a>
            <a href="./dashboard?size=100&search=${search}&sort=${sort}"><button type="button" class="btn btn-default">100</button></a>
        </div>

    </footer>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>

</body>
</html>