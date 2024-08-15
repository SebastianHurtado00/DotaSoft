<%-- 
    Document   : RestablecimientoPasswords
    Created on : 14/08/2024, 06:38:38 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Usuarios"%>
<%@page import="Controladores.UsuariosJpaController"%>
<%
    String cedulaStr = request.getParameter("cedula");
    if (cedulaStr != null) {

        UsuariosJpaController userController = new UsuariosJpaController();
        int cedula = Integer.parseInt(cedulaStr);
        String CC = "cedula";
        Usuarios usuarioEncontrado = userController.findUsuarios(cedula);
        if (usuarioEncontrado == null || usuarioEncontrado.getRol() == 2) {
            out.print("<td>No encontrado </td>");
            out.print("<td name = " + CC + "></td>");
            out.print("<td></td>");
        } else {
            out.print("<td name = " + CC + "  > " + usuarioEncontrado.getIdusuario() + "</td>");
            out.print("<td>" + usuarioEncontrado.getNombreCompleto() + "</td>");
            out.print("<td>" + usuarioEncontrado.getApelliodo() + "</td>");
        }

    }


%>