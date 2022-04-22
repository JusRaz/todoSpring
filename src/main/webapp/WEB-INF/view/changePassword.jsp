<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pasikeiskite slaptazodi vartotojas <%=v.getVardas()%></h1>
        <form method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            Senas slaptazodis: <input type="password" name="oldPass"><br>
            Naujas slaptwazodis: <input type="password" name="newPass"><br>
            Pakartokite nauja slaptazodis: <input type="password" name="newPassCheck"><br>
            <input type="submit" value="Pakeisti">
            <a href="./todo">Cancel</a>
        </form>
    </body>
</html>
