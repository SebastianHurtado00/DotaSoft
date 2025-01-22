<%@page import="Entidades.Regional"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page import="Entidades.Centro"%>
<%@page import="Controladores.CentroJpaController"%>
<%@page import="Entidades.Instructor"%>
<%@page import="Controladores.InstructorJpaController"%>
<%@page import="Entidades.Sexo"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Controladores.AreaJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%@page import="Entidades.Red"%>
<%@page import="Entidades.Area"%>
<%
    RegionalJpaController regionalControlador = new RegionalJpaController();
    List<Regional> listaRegional = regionalControlador.findRegionalEntities();
    
    CentroJpaController centroController = new CentroJpaController();
    List<Centro> listaCentro = centroController.findCentroEntities();
    
    InstructorJpaController instructorController = new InstructorJpaController();
    List<Instructor> listaInstructor = instructorController.findInstructorEntities();
%>
<script>
    function verReporte3() {
        // Obtener el valor seleccionado en el select
        var codigo = document.getElementById("ListaCentroReporte").value;
        // Redirigir al enlace con el ID seleccionado como parámetro
        window.open("<%= request.getContextPath()%>/Reports/ReporteRegionalCentro.jsp?ListaCentroReporte=" + codigo, "_blank");
    }
</script>
<script>
    function verReporte1() {
        // Obtener el valor seleccionado en el select
        var codigo = document.getElementById("ListaInstruReporte").value;
        // Redirigir al enlace con el ID seleccionado como parámetro
        window.open("<%= request.getContextPath()%>/Reports/Consolidado_Instructores.jsp?ListaInstruReporte=" + codigo, "_blank");
    }
</script>
<!-- MODAL REPORTE CENTRO INICIO -->
<div class="modal fade" id="ModalReporteRegionalCentro" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioReporteArea" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Reporte Instructores por regional y centro</h2>
                    <!-- Select para Red -->
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Regional:</b></div>
                            <select name="RedReporte" id="ListaRedReporte" class="form-select col-8" onchange="filtradoEntreDosSelects('ListaRedReporte', 'ListaAreaReporte', 'data-listado-red')">
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Regional regional : listaRegional) {%>
                                <option value="<%= regional.getIdregional() %>"><%= regional.getNombre()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <!-- Select para Area -->
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Centro:</b></div>
                            <select name="ListaCentroReporte" id="ListaCentroReporte" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Centro centro2 : listaCentro) {%>
                                
                                <option value="<%= centro2.getIdcentro() %>" data-listado-red="<%= centro2.getRegionalIdregional().getIdregional() %>">

                                    <%= centro2.getNombre()%>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <!-- Botones -->
                    <div class="col-12 text-center py-3 pt-3">
                        <!-- Enlace corregido -->
                        <a class="btn botones text-white px-4" style="background-color: #018E42;" onclick="verReporte3()">
                            <span class="align-middle"><b>Ver</b></span>
                        </a>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO CENTRO FINAL -->
<a href="../../src/java/JsonTablas/ConsultaCaracterizacion.java"></a>

<!-- MODAL REPORTE AREA FINAL -->


<!-- MODAL REGISTRO INSTRUCTOR INICIO -->
<div class="modal fade" id="ModalReporteInstructor" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioInstructor" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Reporte por Instructor</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Centro:</b></div>
                           <select name="ListaSexoReporte" id="ListaSexoReporte" class="form-select col-8" onchange="filtradoEntreDosSelects('ListaSexoReporte', 'ListaInstruReporte', 'data-listado-sexo')">
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Centro centro : listaCentro) {%>
                                <option value="<%= centro.getIdcentro() %>"><%= centro.getNombre()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Instructores:</b></div>
                           <select name="ListaInstruReporte" id="ListaInstruReporte" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Instructor ins : listaInstructor) {%>
                                <option value="<%= ins.getIdinstructor() %>" data-listado-sexo="<%= ins.getCentroIdcentro().getIdcentro() %>">
                                    <%= ins.getNombres() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <a class="btn botones text-white px-4" style="background-color: #018E42;" onclick="verReporte1()">
                            <span class="align-middle"><b>Ver</b></span>
                        </a>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO INSTRUCTOR FINAL -->

<!-- MODAL REGISTRO INSTRUCTOR INICIO -->
<div class="modal fade" id="ModalReporteInstructorAnno" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioInstructor" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Reporte por Instructor</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Centro:</b></div>
                           <select name="ListaSexoReporte" id="ListaSexoReporte" class="form-select col-8" onchange="filtradoEntreDosSelects('ListaSexoReporte', 'ListaInstruReporte', 'data-listado-sexo')">
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Centro centro : listaCentro) {%>
                                <option value="<%= centro.getIdcentro() %>"><%= centro.getNombre()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Instructores:</b></div>
                           <select name="ListaInstruReporte" id="ListaInstruReporte" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Instructor ins : listaInstructor) {%>
                                <option value="<%= ins.getIdinstructor() %>" data-listado-sexo="<%= ins.getCentroIdcentro().getIdcentro() %>">
                                    <%= ins.getNombres() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <a class="btn botones text-white px-4" style="background-color: #018E42;" onclick="verReporte1()">
                            <span class="align-middle"><b>Ver</b></span>
                        </a>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO INSTRUCTOR FINAL -->