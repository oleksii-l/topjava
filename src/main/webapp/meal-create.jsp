<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Meal</title>
</head>
<body>

<form action="" method="post">
    <input type="hidden" name="id" value="<c:out value="${meal.getId()}"/>">
    Дата и время <input type="datetime-local" name="dateTime" value="<c:out value="${meal.getDateTime()}"/>">
    <br/><br/>
    Описание <input type="text" name="description" value="<c:out value="${meal.getDescription()}"/>">
    <br/><br/>
    Калории <input type="text" name="calories" value="<c:out value="${meal.getCalories()}"/>">
    <br/>
    <br/>
    <br/>
    <input type="submit" value="Добавить">
</form>

</body>
</html>
