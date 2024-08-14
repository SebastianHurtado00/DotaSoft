<%@page import="Entidades.Area"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.AreaJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("area"));

    AreaJpaController controlador = new AreaJpaController();
    List lista = controlador.findAreaEntities();

    for (int i = 0; i < lista.size(); i++) {
        Area tipo2 = (Area) lista.get(i);

        if (tipo2.getIdarea() == id) {

            out.print("<option value='" + tipo2.getIdarea() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdarea() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>