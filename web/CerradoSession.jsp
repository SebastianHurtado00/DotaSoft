<%-- 
    Document   : CerradoSession
    Created on : 13/08/2024, 12:40:37 PM
    Author     : ASUS
--%>

<%
    HttpSession sessionCerrar = request.getSession(false); // Obt�n la sesi�n sin crear una nueva si no existe.
    if (sessionCerrar != null) {
        sessionCerrar.invalidate(); // Invalida la sesi�n actual
    }
    response.sendRedirect("index.jsp"); // Redirige al �ndice
%>
