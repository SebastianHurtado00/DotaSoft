<%-- 
    Document   : pruebaFiltrado
    Created on : 20/08/2024, 07:05:22 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Instructor"%>
<%@page import="Controladores.InstructorJpaController"%>
<%@page import="Entidades.Centro"%>
<%@page import="Controladores.CentroJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Regional"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            RegionalJpaController RegionalController = new RegionalJpaController();
            List<Regional> listaRegipnal = RegionalController.findRegionalEntities();

            CentroJpaController centroController = new CentroJpaController();
            List<Centro> centrolIst = centroController.findCentroEntities();

            InstructorJpaController controlInstructor = new InstructorJpaController();
            List<Instructor> listInstr = controlInstructor.findInstructorEntities();

        %>
        <label>Regional</label>
        <select id="Regional" onchange="filtradoEntreDosSelects('Regional', 'Centro', 'data-id-Regional')">
            <%                for (Regional regi : listaRegipnal) {
            %>
            <option value="<%=regi.getIdregional()%>" "><%=regi.getNombre()%></option>
            <%}%>
        </select>
        <br>
        <br>
        <label>Centro</label>
        <select id="Centro" onchange="filtradoEntreDosSelects('Centro' , 'instructor' , 'data-id-Centro' )">
            <%
                for (Centro centro : centrolIst) {
            %>
            <option  selected="">opciones</option>
            <option  value="<%=centro.getIdcentro()%>" data-id-Regional="<%=centro.getRegionalIdregional()%>" ><%=centro.getNombre()%></option>
            <%
                }
            %>
        </select>
        <br>
        <br>
        <label>Instructor</label>
        <select id="instructor">
            <%
                for (Instructor inst : listInstr) {
            %>
            <option  selected="">opciones</option>
            <option value="<%=inst.getIdinstructor()%>" data-id-Centro="<%=inst.getCentroIdcentro()%>" ><%=inst.getNombres()%></option>
            <%
                }
            %>
        </select>


        <script src="js/JsContainer.js"></script>

    </body>
</html>
