<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <link type="text/css"
          href="https://code.jquery.com/ui/1.8.18/themes/smoothness/jquery-ui.css" rel="stylesheet" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <script   src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"   integrity="sha256-5L9BFhGnFaV1LW6ANFzV+lZzGo/5blTlISAkM3ocaYQ="   crossorigin="anonymous"></script>
    <title>Add new user</title>
</head>

<body>
<script>
    $(function() {
        $('input[name=dateTime]').datepicker();
    });
</script>

<form method="POST" action='meals' name="frmAddUserMeal">
    ID : <input type="text" readonly="readonly" name="userMealId"
                     value="<c:out value="${meal.id}" />" /> <br />
    Date and Time : <input
        type="text" name="dateTime"
        value="<c:out value="${meal.dateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
