<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>table</title>
    <style>
        CAPTION {
            caption-side: top;
            text-align: center;
            padding: 10px 0;
            font-size: 20px;
        }

        BODY {
            background-color: whitesmoke;
        }

        TABLE {
            border: 3px solid black;
            width: auto;
            border-collapse: separate;
            border-spacing: initial;
            margin: auto;
        }

        TD, TH {
            padding: 8px;
            border: 1px solid black;
            text-align: center;
        }

        tr:hover td {
            background: #ccddff;
        }
    </style>
</head>
<body>
<table>
    <caption>Users</caption>
    <thead>
    <tr>
        <th>ip</th>
        <th>user_agent</th>
        <th>visit_time</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="users" scope="request" type="java.util.List"/>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><strong>${user.ip}</strong></td>
            <td><strong>${user.userAgent}</strong></td>
            <td>${user.visit}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>