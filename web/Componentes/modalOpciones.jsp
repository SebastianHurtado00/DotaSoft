<%@page import="Entidades.Regional"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<!-- MODALES DE RED GUARDAR OPCINES INICIO -->
<div class="modal fade" id="ModalRedOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioRedOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Red</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpRe" name="codigoElRe" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpRe" name="nombreElRe" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarRed" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarRed" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarRed" data-bs-dismiss="modal">Cerrar</button>
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
            <div class="modal-body">
                <form id="FormularioAreaOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Area</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpArea" name="codigoElArea" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpArea" name="nombreElArea" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red</b></div>
                            <select name="RedesListaOp" id="RedesListaEl"
                                    class="from-selec col-8"  required>
                                <option value="" disabled selected hidden>-- Elija --</option>
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
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarArea" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarArea" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarArea" data-bs-dismiss="modal">Cerrar</button>
                    </div>
            </div>
            </form>
        </div>
    </div>
</div>
<!-- MODALES DE AREA OPCINES FINAL -->
<!-- MODALES DE REGIONAL OPCINES INICIO -->
<div class="modal fade" id="ModalRegionalOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioRegionalOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Regional</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpReg" name="codigoElReg" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpReg" name="nombreElReg" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarReg" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarReg" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarReg" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE REGIONAL OPCINES FINAL -->
<!-- MODALES DE CENTRO OPCINES INICIO -->
<div class="modal fade" id="ModalCentroOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioCentroOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Centro</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpCent" name="codigoElCent" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpCent" name="nombreElCent" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red</b></div>
                            <select name="CentroListaOp" id="CentroListaEl"
                                    class="from-selec col-8"  required>
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
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarCent" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarCent" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarCent" data-bs-dismiss="modal">Cerrar</button>
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
            <div class="modal-body">
                <form id="FormularioSexoOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Sexo</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpSexo" name="codigoElSexo" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpSexo" name="nombreElSexo" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarSexo" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarSexo" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarSexo" data-bs-dismiss="modal">Cerrar</button>
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
            <div class="modal-body">
                <form id="FormularioClimaOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Clima</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpCli" name="codigoElCli" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpCli" name="nombreElCli" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarClima" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarClima" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarClima" data-bs-dismiss="modal">Cerrar</button>
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
            <div class="modal-body">
                <form id="FormularioElementosOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Opciones Elemento</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoElementoOp" name="codigoElElm" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreElementoOp" name="nombreElElm" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarElm" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarElm" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarElm" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ELEMENTOS OPCINES FINAL -->