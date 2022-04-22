<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String error = request.getParameter("error");
%>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
            if (error != null) {
        %>
        <h2>Neteisingas loginas</h2>
        <%
            }
        %>
        <div>Prisistatyk</div>
        <form action="./login" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            Vardas: <input type="text" name="username"><br>
            Slaptazodis: <input type="password" name="password"><br>
            <input type="submit" value="Login">
            <a href="./index.html">Cancel</a>
        </form>
    </body>
</html>
