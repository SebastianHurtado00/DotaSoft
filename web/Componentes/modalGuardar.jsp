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
<!-- MODALES DE REGIONAL GUARDAR INICIO -->
<div class="modal fade" id="ModalRegional" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioRegional" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Regional</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdReg" name="codigoReg" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdReg" name="nombreReg" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarReg" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE REGIONAL GUARDAR FINAL -->
<!-- MODALES DE CENTRO GUARDAR INICIO -->
<div class="modal fade" id="ModalCentro" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioCentro" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Centro</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdCent" name="codigoCent" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdCent" name="nombreCent" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Red</b></div>
                            <select name="CentroListaGd" id="CentroListaForGd"
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
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarCentro" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE CENTRO GUARDAR FINAL -->
<!-- MODALES DE SEXO GUARDAR INICIO -->
<div class="modal fade" id="ModalSexo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioSexo" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Sexo</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdSexo" name="codigoSexo" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdSexo" name="nombreSexo" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarSexo" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE SEXO GUARDAR FINAL -->
<!-- MODALES DE SEXO GUARDAR INICIO -->
<div class="modal fade" id="ModalClima" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioClima" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Clima</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdCli" name="codigoCli" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-4"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdCli" name="nombreCli" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarCli" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE SEXO GUARDAR FINAL -->

<!-- MODAL REGISTRO USUARIOS INICIO -->
<div class="modal fade" id="ModalGuardarUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Registros de usuarios</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="action">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="CedulaUsuario" type="number" class="form-control" id="InputCedula" required>
                            <label class="text-small text-black mx-2" style="font-size: 15px" for="InputCedula">N� Cedula Usuario</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="nombre" type="text" class="form-control mb-2" id="InputNombre" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Nombres </label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating">
                            <input name="apellido" type="text" class="form-control mb-2" id="InputApellido" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputNombre">Apellidos</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating text-center">
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
                        <div class="col-md-6 col-sm-12 form-floating text-center mt-2" id="Centro" style="display: none">
                            <select name="centro" class="form-select mx-auto" id="Centro" required>
                                <option value="" disabled selected hidden>Seleccione un centro</option>
                                <% for (Centro centro : listaCentro) {%>
                                <option value="<%=centro.getIdcentro()%>"><%=centro.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small text-black mx-2 " style="font-size: 15px" for="Centro">Centro</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating text-center mt-2" id="Regional" style="display: none">
                            <select name="regional" class="form-select mx-auto" id="Regional" required>
                                <option value="" disabled selected hidden>Seleccione una regional</option>
                                <% for (Regional regi : listaRegional) {%>
                                <option value="<%=regi.getIdregional()%>"><%=regi.getNombre()%></option>
                                <% }%>
                            </select>
                            <label class="text-small text-black mx-2 " style="font-size: 15px" for="Centro">Regional</label>
                        </div>

                        <%
                            CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
                            List<Coordinador> coordinadorList = controlCoordinador.findCoordinadorEntities();
                        %>
                        <div class="col-md-12 col-sm-12 form-floating text-center mt-2" id="Coordinador" style="display: none">
                            <select name="Coordinador" class="form-select mx-auto" id="Centro" required>
                                <option value="" disabled selected hidden>Seleccione un coordinador</option>
                                <% for (Coordinador coordinador : coordinadorList) {%>
                                <option value="<%=coordinador.getIdcoordinador()%>"><%=coordinador.getNombres() + " " + coordinador.getApellidos()%></option>
                                <% }%>
                            </select>
                            <label class="text-small text-black mx-2" style="font-size: 15px" for="Centro">Coordinador a cargo</label>
                        </div>

                        <div class="col-md-12 col-sm-12 form-floating mt-2" id="Email" style="display: none">
                            <input name="email" type="email" class="form-control mb-2" id="InputCorreo" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Correo</label>
                        </div>

                        <div class="col-md-6 col-sm-12 form-floating mt-2" id="Telefono" style="display: none">
                            <input name="telefono" type="number" class="form-control mb-2" id="InputTelefono" required>
                            <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Telefono</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- MODAL REGISTRO USUARIOS FINAL -->

<script>

 function roles() {
    let select = document.getElementById("Roles");
    let selectedValue = select.options[select.selectedIndex].value;
    let cajaCentro = document.getElementById("Centro");
    let cajaRegional = document.getElementById("Regional");
    let cajaCoordinador = document.getElementById("Coordinador");
    let cajaEmil = document.getElementById("Email");
    let cajaTelefono = document.getElementById("Telefono");

    if (selectedValue == 1) {
        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";
        cajaEmil.style.display = "block";
        cajaCoordinador.style.display = "none";
        cajaTelefono.style.display = "none";
        
        // Restaurar la clase a `col-md-12` si fue cambiada anteriormente
        cajaEmil.classList.remove("col-md-6");
        cajaEmil.classList.add("col-md-12");

    } else if (selectedValue == 2) {
        cajaCoordinador.style.display = "block";
        cajaEmil.style.display = "block";
        cajaTelefono.style.display = "block";

        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";

        // Cambiar las clases de `cajaEmil`
        cajaEmil.classList.remove("col-md-12");
        cajaEmil.classList.add("col-md-6");
        
    } else {
        cajaCoordinador.style.display = "none";
        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";
        cajaEmil.style.display = "none";
        cajaTelefono.style.display = "none";
    }
}

</script>