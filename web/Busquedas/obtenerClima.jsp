<%@page import="Entidades.Clima"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.ClimaJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("clima"));

    ClimaJpaController controlador = new ClimaJpaController();
    List lista = controlador.findClimaEntities();

    for (int i = 0; i < lista.size(); i++) {
        Clima tipo2 = (Clima) lista.get(i);

        if (tipo2.getIdclima() == id) {

            out.print("<option value='" + tipo2.getIdclima() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdclima() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>