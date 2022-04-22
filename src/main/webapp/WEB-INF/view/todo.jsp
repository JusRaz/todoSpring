<%@page import="lt.bit.todo.data.MazaUzduotis"%>
<%@page import="java.util.List"%>
<%@page import="lt.bit.todo.data.Uzduotis"%>
<%@page import="javax.persistence.Query"%>
<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    List<Uzduotis> list = (List) request.getAttribute("list");
    
%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
        <form action="./logout" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input name="submit" type="submit" value="logout" />
        </form>
        <%
            if (v.getAdmin()) {
        %>
        <a href="./admin/vartotojai">Vartotojai</a>
        <%
            }
        %>
        <a href="./changePassword">Slaptazodis</a>
        <h1>${vartotojas.vardas}</h1>
        <a href="./todo/todoEdit">New</a>
        <ul>
            <%
                for(Uzduotis u : list) {
            %>
            <li>
                
                <b>Uzduotis id :</b> <%=u.getId()%> <b>Pavadinimas:</b> <%=u.getPavadinimas()%> <b>Aprašymas:</b> <%=u.getAprasymas()%> <b>iki Kada:</b> <%=u.getIkiKada()%> <b>Statusas:</b> <%=u.getStatusas()%> <b>Atlikta:</b> <%=u.getAtlikta()%>
                <%
                    if (u.getAtlikta() == null) {
                %>
                <a href="./todo/todoDone?todoId=<%=u.getId()%>">Done</a>
                <%
                    }
                %>
                <a href="./todo/todoEdit?todoId=<%=u.getId()%>">Edit</a>
                <a href="./todo/todoDelete?todoId=<%=u.getId()%>">Delete</a>
                <%
                    if (!u.getMazosUzduotys().isEmpty()) {
                %>
            <ul>
                <%
                    for (MazaUzduotis mu: u.getMazosUzduotys()) {
                %>
                <li>
                    MazaUzduotis id : <%=mu.getId()%> Pavadinimas: <%=mu.getPavadinimas()%> Aprašymas: <%=mu.getAprasymas()%> Atlikta: <%=mu.getAtlikta()%>
                    
                <%
                    }
                %>
            </ul>
                <%
                    }
                %>
                
            </li>
            <%
                }
            %>
        </ul>
        </div>
    </body>
</html>
