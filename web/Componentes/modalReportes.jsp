<%@page import="Controladores.AreaJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%@page import="Entidades.Red"%>
<%@page import="Entidades.Area"%>
<%
    RedJpaController redController = new RedJpaController();
    List<Red> listaRed = redController.findRedEntities();

    AreaJpaController areaController = new AreaJpaController();
    List<Area> listaArea = areaController.findAreaEntities();
%>

<!-- MODAL REGISTRO AREA INICIO -->
<div class="modal fade" id="ModalReporteArea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioReporteArea" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Reporte Red y Area</h2>

                    <!-- Select para Red -->
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red:</b></div>
                            <select name="RedReporte" id="ListaRedReporte" class="form-select col-8" onchange="filtradoEntreDosSelects('ListaRedReporte', 'ListaAreaReporte', 'data-listado-red')" >
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Red red : listaRed) {%>
                                <option value="<%= red.getIdred()%>"><%= red.getNombre()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <!-- Select para Area -->
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Area:</b></div>
                            <select name="AreaReporte" id="ListaAreaReporte" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <% for (Area area : listaArea) {%>
                                <option value="<%= area.getIdarea()%>" data-listado-red="<%= area.getRedIdred().getIdred() %>">
                                    <%= area.getNombre()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>

                    <!-- Botones -->
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnVerReporteArea" style="background-color: #018E42;"><b>Ver</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO AREA FINAL -->
<a href="../../src/java/JsonTablas/ConsultaCaracterizacion.java"></a>