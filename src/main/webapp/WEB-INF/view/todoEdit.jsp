<%@page import="lt.bit.todo.data.MazaUzduotis"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page import="lt.bit.todo.data.Uzduotis"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    Uzduotis u = (Uzduotis) request.getAttribute("uzduotis");

//    EntityManager em = (EntityManager) request.getAttribute("em");
//
//    String todoIdStr = request.getParameter("todoId");
//    Integer todoId = null;
//    try {
//        todoId = Integer.valueOf(todoIdStr);
//    } catch (NumberFormatException ex) {
//        // Reikia kurti nauja uzduoti
//    }
//    Uzduotis u = null;
//    if (todoId != null) {
//        Query q = em.createQuery("select u from Uzduotis u where u.id = :todoId and u.vartotojas = :vartotojas");
//        q.setParameter("todoId", todoId);
//        q.setParameter("vartotojas", v);
//        List<Uzduotis> list = q.getResultList();
//        if (!list.isEmpty()) {
//            u = list.get(0);
//        } else {
//            this.log("Uzduoties su id (" + todoId + ") nera arba priklauso kitam vartotojui");
//            response.sendRedirect("./todo");
//            return;
//        }
//    }
    Map<String, String> klaidos = (Map) request.getAttribute("klaidos");
    if (klaidos == null) {
        klaidos = new HashMap();
    }
    Map<String, String> mazosKlaidos = (Map) request.getAttribute("mazosKlaidos");
    if (mazosKlaidos == null) {
        mazosKlaidos = new HashMap();
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Viena uzduotis</title>
    </head>
    <body>
        <form action="./todoSave" method="POST">
            <%            if (u != null) {
            %>
            <input type="hidden" name="todoId" value="<%=u.getId()%>">
            <%
                }
            %>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            Pavadinimas: <input name="pavadinimas" value="<%= (u != null) ? u.getPavadinimas() : ""%>">
            <%
                if (klaidos.get("pavadinimas") != null) {
            %>
            <span><%=klaidos.get("pavadinimas")%></span>
            <%
                }
            %>
            <br>
            Aprasymas: <input name="aprasymas" value="<%= (u != null) ? u.getAprasymas() : ""%>"><br>
            Iki kada: <input type="date" name="ikiKada" value="<%= (u != null) ? u.getIkiKadaStr() : ""%>">
            <%
                if (klaidos.get("ikiKada") != null) {
            %>
            <span><%=klaidos.get("ikiKada")%></span>
            <%
                }
            %>
            <br>
            Statusas: <input type="number" name="statusas" value="<%= (u != null) ? u.getStatusas() : "0"%>">
            <%
                if (klaidos.get("statusas") != null) {
            %>
            <span><%=klaidos.get("statusas")%></span>
            <%
                }
            %>
            <br>
            Atlikta: <input type="date" name="atlikta" value="<%= (u != null) ? u.getAtliktaStr() : ""%>">
            <%
                if (klaidos.get("atlikta") != null) {
            %>
            <span><%=klaidos.get("atlikta")%></span>
            <%
                }
            %>
            <br>
            <input type="submit" value="Save"><br>
            <a href="../todo">Cancel</a>
        </form>

        <%
            if (u != null) {
        %>
        <%
            if (u.getAtlikta() == null) {
        %>
        <form action="./todoDetailAdd" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="todoId" value="<%=u.getId()%>">
            <input name="pavadinimas">
            <%
                if (mazosKlaidos.get("pavadinimas") != null
                        && mazosKlaidos.get("todoDetailId") == null) {
            %>
            <span><%=mazosKlaidos.get("pavadinimas")%></span>
            <%
                }
            %>
            <input name="aprasymas">
            <input type="submit" value="Add">
        </form>
        <%
            }
        %>
        <%
            if (!u.getMazosUzduotys().isEmpty()) {
        %>
        <ul>
            <%
                for (MazaUzduotis mu : u.getMazosUzduotys()) {
            %>
            <li>
                <form action="./todoDetailSave" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="hidden" name="todoId" value="<%=u.getId()%>">
                    <input type="hidden" name="todoDetailId" value="<%=mu.getId()%>">
                    <input name="pavadinimas" value="<%=mu.getPavadinimas()%>" <%=(u.getAtlikta() != null) ? "disabled" : ""%> >
                    <%
                        if (mazosKlaidos.get("pavadinimas") != null
                                && mu.getId().toString().equals(mazosKlaidos.get("todoDetailId"))) {
                    %>
                    <span><%=mazosKlaidos.get("pavadinimas")%></span>
                    <%
                        }
                    %>
                    <input name="aprasymas" value="<%=(mu.getAprasymas() != null) ? mu.getAprasymas() : ""%>" <%=(u.getAtlikta() != null) ? "disabled" : ""%> >
                    <input type="date" name="atlikta" value="<%=mu.getAtlikta()%>" <%=(u.getAtlikta() != null) ? "disabled" : ""%> >
                    <%
                        if (mazosKlaidos.get("atlikta") != null
                                && mu.getId().toString().equals(mazosKlaidos.get("todoDetailId"))) {
                    %>
                    <span><%=mazosKlaidos.get("atlikta")%></span>
                    <%
                        }
                    %>
                    <%
                        if (u.getAtlikta() == null) {
                    %>
                    <input type="submit" value="Save">
                    <%
                        if (mu.getAtlikta() == null) {
                    %>
                    <a href="./todoDetailDone?todoDetailId=<%=mu.getId()%>">Done</a>
                    <%
                        }
                    %>
                    <a href="./todoDetailDelete?todoDetailId=<%=mu.getId()%>">Delete</a>
                    <%
                        }
                    %>
                </form>
            </li>
            <%
                }
            %>
        </ul>
        <%
            }
        %>
        <%
            }
        %>
    </body>
</html>
