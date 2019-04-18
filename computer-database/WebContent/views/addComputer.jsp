<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
<title><spring:message code="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
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
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer"/></h1>
                    <form:form action="./addComputer" method="POST" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name"><spring:message code="computerName"/></form:label>
                                <form:input path="name" type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName"/>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced"><spring:message code="introducedDate"/></form:label>
                                <form:input path="introduced" type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced"/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued"><spring:message code="discontinuedDate"/></form:label>
                                <form:input path="discontinued" type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued"/>
                            </div>
                            <div class="form-group">
                                <form:label path="companyId"><spring:message code="company"/></form:label>
                                <form:select path="companyId" class="form-control" id="companyId" name="companyId">
                                    <form:option value="0">--</form:option>
                                    <c:forEach var="company" items="${list}">
                                    <form:option value="${company.id}">${company.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="submit" value="<spring:message code="add"/>" class="btn btn-primary"/>
                            <spring:message code="or"/>
                            <a href="./dashboard" class="btn btn-default"><spring:message code="cancel"/></a>
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