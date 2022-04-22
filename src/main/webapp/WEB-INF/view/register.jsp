<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Naujas vartotojas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>Prisistatyk</div>
        <form action="./register" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            Vardas: <input type="text" name="vardas"><br>
            Slaptazodis: <input type="password" name="slaptazodis"><br>
            Pakartok: <input type="password" name="slaptazodis2"><br>
            <input type="submit" value="Register">
            <a href="./index.html">Cancel</a>
        </form>
    </body>
</html>
