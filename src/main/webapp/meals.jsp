<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Список еды</title>

    <style>
        .meal-tbl {
            background: lightseagreen;
            width: 80%;
            border: 1px;
        }

        .meal-tbl__exceed {
            background: red;
        }

        .meal-tbl__notexceed {
            background: lightgreen
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Список еды</h2>
<table class="meal-tbl">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${mealWithExceed}">
        <tr
                class="<c:out value="${meal.isExceed() ? 'meal-tbl__exceed' : 'meal-tbl__notexceed'}" />"
        >
            <td>
                <javatime:format value="${meal.getDateTime()}" style="MS"/>
            </td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>