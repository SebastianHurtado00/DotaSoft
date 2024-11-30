<%@page import="java.util.Map"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.servlet.ServletOutputStream"%>
<%@page import="Entidades.Usuarios"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%
    // Evitar el caché del navegador para este contenido
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Para proxies

    // Obtener el parámetro del área
    String areaParam = request.getParameter("ListaCentroReporte");
    int areaId = Integer.parseInt(areaParam);

    // Obtener la sesión actual y validar el usuario
    HttpSession sesion = request.getSession(false);
    Usuarios usu = (Usuarios) sesion.getAttribute("administrador");

    if (sesion == null || usu == null) {
        response.sendRedirect("../index.jsp");
        return; // Detener la ejecución si no hay sesión o usuario
    }

    // Parámetros para el reporte
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("num", areaId);

    // Conexión a la base de datos
    Connection conexion;
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    conexion = DriverManager.getConnection("jdbc:mysql://localhost/dotaciones_sena", "root", "27478426*cP");

    // Generar el archivo PDF del reporte Jasper
    File reportFile = new File(application.getRealPath("Reports/RegionaCentro.jasper"));
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, conexion);

    // Configurar la respuesta HTTP para enviar el PDF
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);

    // Enviar el contenido PDF al navegador
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
%>
