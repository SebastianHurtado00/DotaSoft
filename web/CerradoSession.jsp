<%-- 
    Document   : CerradoSession
    Created on : 13/08/2024, 12:40:37 PM
    Author     : ASUS
--%>

<%
    HttpSession sessionCerrar = request.getSession(false); // Obtén la sesión sin crear una nueva si no existe.
    if (sessionCerrar != null) {
        sessionCerrar.invalidate(); // Invalida la sesión actual
    }
    response.sendRedirect("index.jsp"); // Redirige al índice
%>
