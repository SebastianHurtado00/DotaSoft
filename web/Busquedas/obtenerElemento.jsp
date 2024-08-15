<%@page import="Entidades.Elementos"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.ElementosJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("elemento"));

    ElementosJpaController controlador = new ElementosJpaController();
    List lista = controlador.findElementosEntities();

    for (int i = 0; i < lista.size(); i++) {
        Elementos tipo2 = (Elementos) lista.get(i);

        if (tipo2.getIdelemento() == id) {

            out.print("<option value='" + tipo2.getIdelemento() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdelemento() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>