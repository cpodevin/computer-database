<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="./dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${computer.id}"/>
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form action="./editComputer" method="POST" modelAttribute="computerDTO">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name">Computer name</form:label>
                                <form:input path="name" type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName" value="${computer.name}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced">Introduced date</form:label>
                                <form:input path="introduced" type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced" value="${computer.introduced}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued">Discontinued date</form:label>
                                <form:input path="discontinued" type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued" value="${computer.discontinued}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">Company</form:label>
                                <form:select path="companyId" class="form-control" id="companyId" name="companyId">
                                    <form:option value="0"> --</form:option>
                                    <c:forEach var="company" items="${list_company}">
                                    <c:choose>
                                    <c:when test="${computer.companyId == company.id}">
                                    <form:option value="${company.id}" selected="selected">${company.name}</form:option>
                                    </c:when>
                                    <c:otherwise>
                                    <form:option value="${company.id}">${company.name}</form:option>
                                    </c:otherwise>
                                    </c:choose>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="./dashboard" id="submit" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
            <p>
            	<c:out value="${res}" /> 
            </p>
        </div>
    </section>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/validator.js"></script>
</body>
</html>