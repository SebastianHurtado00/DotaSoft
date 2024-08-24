<%@page import="Entidades.Area"%>
<%@page import="Controladores.AreaJpaController"%>
<%@page import="Entidades.Clima"%>
<%@page import="Controladores.ClimaJpaController"%>
<%@page import="Entidades.Sexo"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Entidades.Elementos"%>
<%@page import="Controladores.ElementosJpaController"%>
<%@page import="Entidades.Coordinador"%>
<%@page import="Controladores.CoordinadorJpaController"%>
<%@page import="Entidades.Centro"%>
<%@page import="Controladores.CentroJpaController"%>
<%@page import="Entidades.Regional"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%@page import="Controladores.RedJpaController"%>
<!-- MODALES DE RED GUARDAR INICIO -->
<!-- Modal para Registrar Red -->
<div class="modal fade" id="ModalRed" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Red</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioRed" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdRe" name="codigoRe" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdRe" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdRe" name="nombreRe" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdRe" class="text-small">Nombre</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarRed" style="background-color: #018E42;">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Registrar Area -->
<div class="modal fade" id="ModalArea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Area</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioArea" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdSe" name="codigoAre" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdSe" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdSe" name="nombreAre" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdSe" class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <select name="RedesListaGd" id="RedesListaForGd" class="form-select" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <%
                                    RedJpaController cred = new RedJpaController();
                                    List listaccregi = cred.findRedEntities();
                                    for (int i = 0; i < listaccregi.size(); i++) {
                                        Red obj_cr = (Red) listaccregi.get(i);
                                        out.print("<option value='" + obj_cr.getIdred() + "'>");
                                        out.print((obj_cr.getNombre()));
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                            <label for="RedesListaForGd" class="text-small">Red</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarArea" style="background-color: #018E42;">Guardar</button>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE AREA GUARDAR FINAL -->
<!-- MODALES DE REGIONAL GUARDAR INICIO -->
<div class="modal fade" id="ModalRegional" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Regional</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioRegional" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdReg" name="codigoReg" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdReg" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdReg" name="nombreReg" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdReg" class="text-small">Nombre</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarReg" style="background-color: #018E42;">Guardar</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE REGIONAL GUARDAR FINAL -->
<!-- MODALES DE CENTRO GUARDAR INICIO -->
<div class="modal fade" id="ModalCentro" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Registros de Centros</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioCentro" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdCent" name="codigoCent" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdCent" class="text-small"  style="font-size: 15px">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating is-invalid">
                            <input type="text" class="form-control" id="nombreGdCent" name="nombreCent" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdCent" class="text-small"  style="font-size: 15px">Nombre</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <select name="CentroListaGd" id="CentroListaForGd" class="form-select" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <%
                                    RegionalJpaController regional = new RegionalJpaController();
                                    List<Regional> listaRegional = regional.findRegionalEntities();
                                    for (int i = 0; i < listaRegional.size(); i++) {
                                        Regional obj_cr = (Regional) listaRegional.get(i);
                                        out.print("<option value='" + obj_cr.getIdregional() + "'>");
                                        out.print((obj_cr.getNombre()));
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                            <label for="CentroListaForGd" class="text-small"  style="font-size: 15px">Red</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarCentro" style="background-color: #018E42;">Guardar</button>

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE CENTRO GUARDAR FINAL -->
<!-- MODALES DE SEXO GUARDAR INICIO -->
<!-- Modal para Registrar Sexo -->
<div class="modal fade" id="ModalSexo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Sexo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioSexo" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdSexo" name="codigoSexo" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdSexo" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdSexo" name="nombreSexo" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdSexo" class="text-small">Nombre</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarSexo" style="background-color: #018E42;">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Registrar Clima -->
<div class="modal fade" id="ModalClima" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Clima</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioClima" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdCli" name="codigoCli" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdCli" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdCli" name="nombreCli" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdCli" class="text-small">Nombre</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarCli" style="background-color: #018E42;">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Registrar Elemento -->
<div class="modal fade" id="ModalElementos" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <!-- Encabezado del modal -->
            <div class="modal-header">
                <h5 class="modal-title">Registrar Elemento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Cuerpo del modal -->
            <div class="modal-body">
                <form id="FormularioElementos" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoGdElm" name="codigoElm" placeholder="Codigo" required min="1" max="2147483647">
                            <label for="codigoGdElm" class="text-small">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreGdElm" name="nombreELm" placeholder="Nombre" required min="1" maxlength="45">
                            <label for="nombreGdElm" class="text-small">Nombre</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Pie del modal -->
            <div class="modal-footer text-center py-3">
                <button type="submit" class="btn botones text-white px-4" id="btnGuardarElm" style="background-color: #018E42;">Guardar</button>               
            </div>
        </div>
    </div>
</div>

<!-- MODALES DE ELEMENTOS GUARDAR FINAL -->   
<!-- MODAL REGISTRO USUARIOS INICIO -->
<div class="modal fade" id="ModalGuardarUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Registros de usuarios</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="formularioUsuarios" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 mb-2 form-floating">
                            <input name="CedulaUsuario" type="number" class="form-control" id="InputCedula" required>
                            <label class="text-small text-black mx-2" style="font-size: 15px" for="InputCedula">N° Cedula Usuario</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="nombre" type="text" class="form-control mb-2" id="InputNombre" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Nombres </label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="apellido" type="text" class="form-control mb-2" id="InputApellido" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Apellidos</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2">
                            <select name="rolUsuario" class="form-select mx-auto" id="Roles" onchange="roles()" required>
                                <option value="" disabled selected hidden></option>
                                <option value="0">Administrador</option>
                                <option value="1">Coordinador</option>
                                <option value="2">Instructor</option>
                                <option value="3">Recursos Humanos</option>
                            </select>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="Roles">Rol</label>
                        </div>


                        <%
                            CentroJpaController controlCentro = new CentroJpaController();
                            List<Centro> listaCentro = controlCentro.findCentroEntities();

                        %>

                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2" id="Regional" style="display: none">
                            <select name="regional" class="form-select mx-auto" id="RegionalSelect" onchange="filtradoEntreDosSelects('RegionalSelect', 'CentroSelect', 'data-fk')" required>
                                <option value="" disabled selected hidden>Seleccione una regional</option>
                                <% for (Regional regi : listaRegional) {%>
                                <option value="<%=regi.getIdregional()%>"><%=regi.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black " style="font-size: 15px" for="Centro">Regional</label>
                        </div>


                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2" id="Centro" style="display: none">
                            <select name="centro" class="form-select mx-auto" id="CentroSelect" onchange="filtradoEntreDosSelects('CentroSelect', 'CoordinadorSelect', 'data-centro-fk')" required>
                                <option value="" disabled selected hidden>Seleccione un centro</option>
                                <% for (Centro centro : listaCentro) {%>
                                <option value="<%=centro.getIdcentro()%>" data-fk="<%=centro.getRegionalIdregional()%>" ><%=centro.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black " style="font-size: 15px" for="Centro">Centro</label>
                        </div>
                        <%
                            CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
                            List<Coordinador> coordinadorList = controlCoordinador.findCoordinadorEntities();
                        %>
                        <div class="col-md-12 col-sm-12 form-floating text-center mt-2 mb-2" id="Coordinador" style="display: none">
                            <select name="Coordinador" class="form-select mx-auto" id="CoordinadorSelect"  required >
                                <option value="" disabled selected hidden>Seleccione un coordinador</option>
                                <% for (Coordinador coordinador : coordinadorList) {%>
                                <option value="<%=coordinador.getIdcoordinador()%>" data-centro-fk="<%=coordinador.getCentroIdcentro()%>" ><%=coordinador.getNombres() + " " + coordinador.getApellidos()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="Centro">Coordinador a cargo</label>
                        </div>
                        <div class="col-md-12 col-sm-12 form-floating mt-2" id="Email">
                            <input name="email" type="email" class="form-control mb-2" id="InputCorreo" >
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Correo</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating mt-2" id="Telefono" style="display: none">
                            <input name="telefono" type="number" class="form-control mb-2" id="InputTelefono" >
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Telefono</label>
                        </div>
                        <div class="col-md-6 col-sm-12 form-floating mt-2" id="Sexo" style="display: none">
                            <select name="sexoInstructor" class="form-select mx-auto" id="sexo">
                                <option value="" disabled selected hidden></option>
                                <%
                                    SexoJpaController sexoController = new SexoJpaController();
                                    List<Sexo> sexList = sexoController.findSexoEntities();
                                    for (Sexo sex : sexList) {%>
                                <option value="<%=sex.getIdsexo()%>" ><%=sex.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black " style="font-size: 15px" for="Centro">Sexo</label>                
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button  type="submit" id="saveAdmin" class="btn btn-success" style="background-color: #018E42;">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO USUARIOS FINAL -->
<div class="modal fade" id="ModalDotacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioDotacion" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Dotacion</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red:</b></div>
                            <select name="Red2ListaGdDo" id="ListaRedGuardado" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Area:</b></div>
                            <select name="AreaListaGdDo" id="AreaListaForGdDo" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Sexo:</b></div>
                            <select name="SexoListaGdDo" id="SexoListaForGdDo" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <%
                                    SexoJpaController sexo = new SexoJpaController();
                                    List listaSexo = sexo.findSexoEntities();
                                    for (int i = 0; i < listaSexo.size(); i++) {
                                        Sexo obj_cr = (Sexo) listaSexo.get(i);
                                        out.print("<option value='" + obj_cr.getIdsexo() + "'>");
                                        out.print((obj_cr.getNombre()));
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4">Clima</div>
                            <select name="ClimaListaGdDo" id="ClimaListaForGdDo" class="form-select col-8" required>
                                <option value="" disabled selected hidden>Seleccione</option>
                                <%
                                    ClimaJpaController clima = new ClimaJpaController();
                                    List listaClima = clima.findClimaEntities();
                                    for (int i = 0; i < listaClima.size(); i++) {
                                        Clima obj_cr = (Clima) listaClima.get(i);
                                        out.print("<option value='" + obj_cr.getIdclima() + "'>");
                                        out.print((obj_cr.getNombre()));
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4">Elemento</div>
                            <select name="ElementoListaGdDo" id="ElementoListaForGdDo" class="form-select col-8" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    ElementosJpaController elem = new ElementosJpaController();
                                    List listaElementos = elem.findElementosEntities();
                                    for (int i = 0; i < listaElementos.size(); i++) {
                                        Elementos obj_cr = (Elementos) listaElementos.get(i);
                                        out.print("<option value='" + obj_cr.getIdelemento() + "'>");
                                        out.print((obj_cr.getNombre()));
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Cantidad:</b></div>
                            <input type="number" class="form-control" id="cantidadGdDo" name="cantidadDo" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarDotacion" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
