<%@page import="Entidades.Usuarios"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // ProxiesS
    // Obtiene la sesión actual sin crear una nueva si no existe
    HttpSession sesion = request.getSession(false);
    if (sesion == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
    
    Usuarios administrador = (Usuarios) sesion.getAttribute("administrador");
    Usuarios recursosHumanos = (Usuarios) sesion.getAttribute("user");
    
     
    // Verificar si al menos uno de los roles está presente
    if (administrador == null && recursosHumanos == null) {
        // Redirigir al index si ninguno de los roles está presente
        response.sendRedirect("../index.jsp");
        return; // Detener la ejecución si no hay administrador, coordinador ni recursosHumanos
    }
    
    Connection coneccion;
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    coneccion = DriverManager.getConnection("jdbc:mysql://localhost/dotaciones_sena", "root", "27478426*cP");
    File reportFile = new File(application.getRealPath("Reports/Elementos.jasper"));
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), null, coneccion);
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
%>   