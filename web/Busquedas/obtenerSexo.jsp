<%@page import="Entidades.Sexo"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.SexoJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("sexo"));

    SexoJpaController controlador = new SexoJpaController();
    List lista = controlador.findSexoEntities();

    for (int i = 0; i < lista.size(); i++) {
        Sexo tipo2 = (Sexo) lista.get(i);

        if (tipo2.getIdsexo() == id) {

            out.print("<option value='" + tipo2.getIdsexo() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdsexo() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>