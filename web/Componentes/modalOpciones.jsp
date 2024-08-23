<%@page import="Entidades.Elementos"%>
<%@page import="Controladores.ElementosJpaController"%>
<%@page import="Controladores.ClimaJpaController"%>
<%@page import="Entidades.Clima"%>
<%@page import="Entidades.Sexo"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Entidades.Area"%>
<%@page import="Controladores.AreaJpaController"%>
<%@page import="Entidades.Coordinador"%>
<%@page import="Controladores.CoordinadorJpaController"%>
<%@page import="Entidades.Centro"%>
<%@page import="Controladores.CentroJpaController"%>
<%@page import="Entidades.Regional"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<!-- MODALES DE RED GUARDAR OPCINES INICIO -->
<div class="modal fade" id="ModalRedOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Red</h5>
                <button type="button" id="btnCerrarRed" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioRedOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpRe" name="codigoElRe" required min="1" max="2147483647" readonly>
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpRe" name="nombreElRe" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarRed" style="background-color: #018E42;">Actualizar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE RED OPCINES FINAL -->
<!-- MODALES DE AREA OPCINES INICIO -->
<div class="modal fade" id="ModalAreaOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Área</h5>
                <button type="button" id="btnCerrarArea" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioAreaOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpArea" name="codigoElArea" required min="1" max="2147483647">
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpArea" name="nombreElArea" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <select name="RedesListaOp" id="RedesListaEl" class="form-select" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    RedJpaController cred = new RedJpaController();
                                    List<Red> listaRedes = cred.findRedEntities();
                                    for (Red obj_cr : listaRedes) {
                                        out.print("<option value='" + obj_cr.getIdred() + "'>");
                                        out.print(obj_cr.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                            <label class="text-small">Red</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarArea" style="background-color: #018E42;"><b>Actualizar</b></button>                      
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- MODALES DE AREA OPCINES FINAL -->
<!-- MODALES DE REGIONAL OPCINES INICIO -->
<div class="modal fade" id="ModalRegionalOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Regional</h5>
                <button type="button" id="btnCerrarReg" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioRegionalOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpReg" name="codigoElReg" required min="1" max="2147483647" readonly>
                            <label class="text-small text-black">Codigo</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpReg" name="nombreElReg" required min="1" maxlength="45">
                            <label class="text-small text-black">Nombre</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarReg" style="background-color: #018E42;">Actualizar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div
<!-- MODALES DE REGIONAL OPCINES FINAL -->
<!-- MODALES DE CENTRO OPCINES INICIO -->
<div class="modal fade" id="ModalCentroOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Centro</h5>
                <button type="button" id="btnCerrarCent" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioCentroOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpCent" name="codigoElCent" required min="1" max="2147483647">
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpCent" name="nombreElCent" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <select name="CentroListaOp" id="CentroListaEl" class="form-select" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
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
                            <label class="text-small">Regional</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarCent" style="background-color: #018E42;"><b>Actualizar</b></button>
                       
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- MODALES DE CENTRO OPCINES FINAL -->
<!-- MODALES DE SEXO OPCINES INICIO -->
<div class="modal fade" id="ModalSexoOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Sexo</h5>
                <button type="button" id="btnCerrarSexo" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioSexoOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpSexo" name="codigoElSexo" required min="1" max="2147483647" readonly>
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpSexo" name="nombreElSexo" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarSexo" style="background-color: #018E42;">Actualizar</button>

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- MODALES DE SEXO OPCINES FINAL -->
<!-- MODALES DE CLIMA OPCINES INICIO -->
<div class="modal fade" id="ModalClimaOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Clima</h5>
                <button type="button" id="btnCerrarClima" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioClimaOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoOpCli" name="codigoElCli" required min="1" max="2147483647" readonly>
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreOpCli" name="nombreElCli" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarClima" style="background-color: #018E42;">Actualizar</button>

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- MODALES DE CLIMA OPCINES FINAL -->
<!-- MODALES DE ELEMENTOS GUARDAR OPCINES INICIO -->
<div class="modal fade" id="ModalElementosOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Opciones Elemento</h5>
                <button type="button" id="btnCerrarElm" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="FormularioElementosOpciones" class="row g-2">
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="codigoElementoOp" name="codigoElElm" required min="1" max="2147483647" readonly>
                            <label class="text-small">Código</label>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nombreElementoOp" name="nombreElElm" required min="1" maxlength="45">
                            <label class="text-small">Nombre</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarElm" style="background-color: #018E42;">Actualizar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ELEMENTOS OPCINES FINAL -->
<!--  MODALES DE USUARIOS OPCIONES INICIO -->
<div class="modal fade" id="ModalModificarUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modificacion de usuarios</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="formularioEditarUsuarios" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 mb-2 form-floating">
                            <input name="CedulaUsuarioOp" type="number" class="form-control" id="CedulaUsuarioOp" readonly>
                            <label class="text-small text-black mx-2" style="font-size: 15px" for="InputCedula">N° Cedula Usuario</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="nombreOp" type="text" class="form-control mb-2" id="nombreOp" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Nombres </label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="apellidoOp" type="text" class="form-control mb-2" id="apellidoOp" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Apellidos</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2">
                            <select name="rolUsuarioOp" class="form-select mx-auto" id="Rolesop" onchange="rolesOpciones()" style="pointer-events: none; opacity: 0.5;">
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

                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2" id="RegionalOpciones" style="display: none">
                            <select name="regionalop" class="form-select mx-auto" id="RegionalSelectOp"  style="pointer-events: none; opacity: 0.5;">
                                <option value="" disabled selected hidden>Seleccione una regional</option>
                                <% for (Regional regi : listaRegional) {%>
                                <option value="<%=regi.getIdregional()%>"><%=regi.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black " style="font-size: 15px" for="Centro">Regional</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating text-center mb-2" id="CentroOpciones" style="display: none">
                            <select name="centroOp" class="form-select mx-auto" id="CentroSelectOp" " required>
                                <option value="" disabled selected hidden>Seleccione un centro</option>
                                <% for (Centro centro : listaCentro) {%>
                                <option value="<%=centro.getIdcentro()%>" data-fk-centroOp ="<%=centro.getRegionalIdregional()%>" ><%=centro.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black " style="font-size: 15px" for="Centro">Centro</label>
                        </div>
                        <%
                            CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
                            List<Coordinador> coordinadorList = controlCoordinador.findCoordinadorEntities();
                        %>
                        <div class="col-md-12 col-sm-12 form-floating text-center mt-2 mb-2" id="CoordinadorOpciones" style="display: none">
                            <select name="CoordinadorOp" class="form-select mx-auto" id="CoordinadorSelectOp"  required >
                                <option value="" disabled selected hidden>Seleccione un coordinador</option>
                                <% for (Coordinador coordinador : coordinadorList) {%>
                                <option value="<%=coordinador.getIdcoordinador()%>" data-centro-fk="<%=coordinador.getCentroIdcentro()%>" ><%=coordinador.getNombres() + " " + coordinador.getApellidos()%></option>
                                <% }%>
                            </select>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="Centro">Coordinador a cargo</label>
                        </div>
                        <div class="col-md-12 col-sm-12 form-floating mt-2" id="EmailOpciones">
                            <input name="emailOp" type="email" class="form-control mb-2" id="emailOp" >
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Correo</label>
                        </div>

                        <div class="col-md-12 col-sm-12 form-floating mt-2" id="TelefonoOpciones" style="display: none">
                            <input name="telefonoOp" type="number" class="form-control mb-2" id="TelefonoOp" >
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Telefono</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn botones text-white px-4" id="btnactualizarUser" style="background-color: #018E42;"><b>Actualizar</b></button>
                    <button type="button" class="btn btn-secondary" id="btnCerrarUser" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--  MODALES DE USUARIOS OPCIONES FINAL -->
<!-- MODALES DE RED DOTACION INICIO -->
<div class="modal fade" id="ModalDotacionOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioDotacionOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Dotacion</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoDotacionOp" name="codigoDotacionElm" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red:</b></div>
                            <select name="Red2ListaGdDo" id="ListaRedDotacion" class="from-selec col-8" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Area:</b></div>
                            <select name="AreaListaGdDotacion" id="AreaListaForGdDotacion"
                                    class="from-selec col-8"  required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Clima:</b></div>
                            <select name="ClimaListaGdDotacion" id="ClimaListaForGdDotacion"
                                    class="from-selec col-8"  required>
                                <option value="" disabled selected hidden>-- Elija --</option>
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
                            <div class="input-group-text col-4"><b>Sexo:</b></div>
                            <select name="SexoListaGdDotacion" id="SexoListaForGdDotacion"
                                    class="from-selec col-8"  required>
                                <option value="" disabled selected hidden>-- Elija --</option>
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
                            <div class="input-group-text col-4"><b>Elemento:</b></div>
                            <select name="ElementoListaGdDotacion" id="ElementoListaForGdDotacion"
                                    class="from-selec col-8"  required>
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
                            <input type="number" class="form-control" id="cantidadGdDotacion" name="cantidadDotacionEl" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarDta" style="background-color: #018E42;"><b>Actualizar</b></button>
         
                        <button type="button" class="btn btn-secondary" id="btnCerrarDta" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE RED DOTACION FINAL -->