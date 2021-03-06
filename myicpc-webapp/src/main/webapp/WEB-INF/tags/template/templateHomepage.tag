<%@ tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" uri="http://myicpc.baylor.edu/tags" %>

<%@attribute name="head" fragment="true" %>
<%@attribute name="javascript" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="app.name" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <jsp:invoke fragment="head" />
</head>
<body class="home-page">
    <jsp:doBody/>
    <jsp:include page="/WEB-INF/views/includes/foot.jsp"/>
    <jsp:invoke fragment="javascript" />
</body>
</html>