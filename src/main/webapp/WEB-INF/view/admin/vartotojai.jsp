<%@page import="java.util.List"%>
<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page import="org.springframework.data.jpa.repository.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    //EntityManager em = (EntityManager) request.getAttribute("em");
    
  //  Query q = em.createQuery("select v from Vartotojas v order by v.vardas");
   // Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    List<Vartotojas> list = (List) request.getAttribute("list");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vartotojai</title>
    </head>
    <body>
        <a href="../todo">Back</a>
        <h1>Vartotoju sarasas</h1>
        <ul>
            <%
                for (Vartotojas v : list) {
            %>
            <li>
                <form action="./vartotojasEdit" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="hidden" name="id" value="<%=v.getId()%>">
                    <%=v.getVardas()%>
                    <input type="checkbox" name="isAdmin" <%=v.getAdmin() ? "checked" : ""%>>
                    <input type="submit" value="Save">
                    <a href="./vartotojai/vartotojasDelete?id=<%=v.getId()%>">Delete</a>
                </form>
            </li>
            <%
                }
            %>
            <%
            %>
            <%
            %>
        </ul>
    </body>
</html>

