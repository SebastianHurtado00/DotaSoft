<%@page import="Entidades.Centro"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.CentroJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("centro"));

    CentroJpaController controlador = new CentroJpaController();
    List lista = controlador.findCentroEntities();

    for (int i = 0; i < lista.size(); i++) {
        Centro tipo2 = (Centro) lista.get(i);

        if (tipo2.getIdcentro() == id) {

            out.print("<option value='" + tipo2.getIdcentro() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdcentro() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>