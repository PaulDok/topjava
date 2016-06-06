<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<c:set var="meals" value="${requestScope.meals}"/>
<table>
    <tr>
        <th>Date and time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:if test="${!meal.exceed}">
            <tr bgcolor="#7cfc00">
                <td><c:out value="${fn:replace(meal.dateTime,'T',' ')}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:if>
        <c:if test="${meal.exceed}">
            <tr bgcolor="#dc143c">
                <td><c:out value="${fn:replace(meal.dateTime,'T',' ')}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:if>
    </c:forEach>
</table>

</body>
</html>
