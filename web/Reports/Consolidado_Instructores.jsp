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
    // Evitar el cach� del navegador para este contenido
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Para proxies

    // Obtener el par�metro del �rea
    String areaParam = request.getParameter("ListaInstruReporte");

    if (areaParam == null || areaParam.isEmpty()) {
        // Si el par�metro no est� presente, mostrar alerta y redirigir a la misma p�gina
        out.println("<script>");
        out.println("alert('Por favor, seleccione un instructor.');");
        out.println("window.location.href = document.referrer;"); // Redirige a la misma p�gina
        out.println("</script>");
        return; // Detener la ejecuci�n si no se ha proporcionado el par�metro
    }

    int areaId = Integer.parseInt(areaParam);

    // Obtener la sesi�n actual y validar el usuario
    HttpSession sesion = request.getSession(false);

    if (sesion == null) {
        response.sendRedirect("../index.jsp");
        return;
    }

    // Verificar si el usuario tiene alguno de los roles
    // Obtener los atributos de sesi�n para los diferentes roles
    Usuarios administrador = (Usuarios) sesion.getAttribute("administrador");
    Usuarios coordinador = (Usuarios) sesion.getAttribute("coordinador");
    Usuarios recursosHumanos = (Usuarios) sesion.getAttribute("user");

    // Verificar si al menos uno de los roles est� presente
    if (administrador == null && coordinador == null && recursosHumanos == null) {
        // Redirigir al index si ninguno de los roles est� presente
        response.sendRedirect("../index.jsp");
        return; // Detener la ejecuci�n si no hay administrador, coordinador ni recursosHumanos
    }

    // Par�metros para el reporte
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("id", areaId);

    // Conexi�n a la base de datos
    Connection conexion;
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    conexion = DriverManager.getConnection("jdbc:mysql://localhost/dotaciones_sena", "root", "27478426*cP");

    // Generar el archivo PDF del reporte Jasper
    File reportFile = new File(application.getRealPath("Reports/AllInstructores.jasper"));
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
