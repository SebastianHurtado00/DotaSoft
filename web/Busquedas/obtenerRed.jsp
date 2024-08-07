<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("red"));

    RedJpaController controlador = new RedJpaController();
    List lista = controlador.findRedEntities();

    for (int i = 0; i < lista.size(); i++) {
        Red tipo2 = (Red) lista.get(i);

        if (tipo2.getIdred() == id) {

            out.print("<option value='" + tipo2.getIdred() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdred() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>