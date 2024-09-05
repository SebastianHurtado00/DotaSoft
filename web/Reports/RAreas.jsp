<%@page import="Logic.Usuarios"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // ProxiesS
    // Obtiene la sesión actual sin crear una nueva si no existe
    HttpSession cecion = request.getSession(false);
    Usuarios usu = (Usuarios) cecion.getAttribute("usuario");
    if (cecion == null || usu == null) {
        response.sendRedirect("../index.jsp");
        return; // Detiene la ejecución del código restante
    }

    int Rinstructor = Integer.parseInt(request.getParameter("Rarea")); // Recuperar el parámetro enviado desde el primer JSP
    // Configuración de la conexión a la base de datos
    Connection coneccion;
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    coneccion = DriverManager.getConnection("jdbc:mysql://localhost/dotaciones_sena2", "root", "27478426*cP");

    // Parámetros a pasar al informe Jasper
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("num", Rinstructor); // Pasar el parámetro num al informe Jasper

    // Ruta al archivo Jasper
    File reportFile = new File(application.getRealPath("Reports/XArea.jasper"));

    // Generar el informe Jasper con los parámetros
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, coneccion);

    // Configurar la respuesta para enviar el PDF generado
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
%>
