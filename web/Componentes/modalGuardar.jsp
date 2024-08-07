<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%@page import="Controladores.RedJpaController"%>
<!-- MODALES DE RED GUARDAR INICIO -->
<div class="modal fade" id="ModalRed" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioRed" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Red</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdRe" name="codigoRe" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdRe" name="nombreRe" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarRed" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE RED GUARDAR FINAL -->
<!-- MODALES DE AREA GUARDAR INICIO -->
<div class="modal fade" id="ModalArea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioArea" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Area</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdSe" name="codigoSe" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdSe" name="nombreSe" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red</b></div>
                            <select name="RedesListaGd" id="RedesListaForGd"
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
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarArea" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE AREA GUARDAR FINAL -->

<!-- MODAL PARA GUARDAR USUARIOS INICIO -->
<div class="modal fade" id="GuardadoUsuarios" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/logica_usuarios" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Registro de Usuarios</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="CedulaUsuario" type="number" class="form-control" id="InputCedula" placeholder="Ingrese el numero de cedula" required>
                            <label class="text-small mx-2" style="font-size: 15px" for="InputCedula">N° Cedula Usuario</label>
                        </div>
                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="NombreCompleto" type="text" class="form-control mb-2" id="InputNombre" placeholder="Ingrese el nombre completo del usuario" required>
                            <label class="text-small mx-2" style="font-size: 15px" for="InputNombre">Nombres y apellidos</label>
                        </div>
                        <div class="col-12 form-floating text-center mt-2">
                           
                                <div class="form-floating">
                                    <select name="rolUsuario" class="form-select" id="Roles" required>
                                        <option value="" disabled selected hidden></option>
                                        <option value="0">Administrador</option>
                                        <option value="1">Coordinador</option>
                                        <option value="2">Instructor</option>
                                        <option value="3">Recursos Humanos</option>
                                    </select>
                                    <label for="Roles" class="text-small" style="font-size: 15px;">Rol</label>
                                </div>
                      

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="" name="action" value="guaradarUsuarios" class="btn btn-success">Registrar</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- MODALE PARA GUARDAR USUARIOS FINAL -->