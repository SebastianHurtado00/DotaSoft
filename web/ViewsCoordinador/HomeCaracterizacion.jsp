<%@page import="Controladores.CoordinadorJpaController"%>
<%@page import="Entidades.Usuarios"%>
<%@page import="Entidades.Coordinador"%>
<%@page import="Entidades.Clima"%>
<%@page import="Entidades.Sexo"%>
<%@page import="Entidades.Instructor"%>
<%@page import="Entidades.Red"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Area"%>
<%@page import="Controladores.ClimaJpaController"%>
<%@page import="Controladores.InstructorJpaController"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Controladores.AreaJpaController"%>
<%@page import="Controladores.RedJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenida = request.getSession();
    CoordinadorJpaController controllerCoordinador = new CoordinadorJpaController();
    Usuarios useCoordinador = new Usuarios();
    if (sessionObtenida.getAttribute("coordinador") == null) {
        response.sendRedirect("../CerradoSession.jsp");
    } else {
        useCoordinador = (Usuarios) sessionObtenida.getAttribute("coordinador");
        Coordinador coordinadorEntrante = controllerCoordinador.findCoordinador(useCoordinador.getIdusuario());

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Caracterizacion</title>
        <%--BOOTSTRAP--%>
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <%--ESTILO-MAQUETA--%>
        <link rel="stylesheet" href="../css/app.css"/>
        <%--TIPOS-LETRAS--%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">
        <%--JQUERY--%>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <%--TRANSICIONES--%>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <%--SELECT2 --%>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <%--ELEMENTOS--%>
        <script src="../js/JsCaracterizaciones.js"></script>
        <script src="../js/JsContainer.js"></script>
        <script src="../js/FiltroTablas.js"></script>
        <script>

        </script>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/Componentes/SideBar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="/Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                    <%--CONTENIDO INICIO --%>
                    <div class="container">
                        <%                            RedJpaController controllerRed = new RedJpaController();
                            AreaJpaController controllerArea = new AreaJpaController();
                            SexoJpaController controllerSexo = new SexoJpaController();
                            InstructorJpaController controllerInstructor = new InstructorJpaController();
                            ClimaJpaController controllerClima = new ClimaJpaController();

                            List<Red> listaRedes = controllerRed.findRedEntities();
                            List<Area> listaAreas = controllerArea.findAreaEntities();
                            List<Sexo> listaSexo = controllerSexo.findSexoEntities();
                            List<Instructor> listaInstructores = controllerInstructor.findInstructorEntities();
                            List<Clima> listaClimas = controllerClima.findClimaEntities();


                        %>
                        <!-- Modal para Registrar Caracterizacion -->
                        <div class="modal fade" id="ModalCaracterizacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-top" role="document">
                                <div class="modal-content">
                                    <!-- Encabezado del modal -->
                                    <div class="modal-header">
                                        <h5 class="modal-title">Registrar Caracterizacion</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <!-- Cuerpo del modal -->
                                    <div class="modal-body">
                                        <form id="FormularioCaracterizacion" class="row g-2">
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="red" class="form-select mx-auto" id="redSelect" onchange="filtradoEntreDosSelects('redSelect', 'AreaSelect', 'data-fk-red')" required>
                                                        <option value="" disabled selected hidden>Seleccione una red</option>
                                                        <% for (Red red : listaRedes) {%>
                                                        <option value="<%=red.getIdred()%>"><%=red.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Redes</label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="area" class="form-select mx-auto" id="AreaSelect" onclick="consultarDotacion('Dotacion', 'SexoSelect', 'ClimaSelect', 'AreaSelect')">
                                                        <option value="" disabled selected hidden>Seleccione un area</option>
                                                        <% for (Area area : listaAreas) {%>
                                                        <option value="<%=area.getIdarea()%>" data-fk-red="<%=area.getRedIdred().getIdred()%>"><%=area.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Areas    </label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="sexo" class="form-select mx-auto" id="SexoSelect"  onclick="consultarDotacion('Dotacion', 'SexoSelect', 'ClimaSelect', 'AreaSelect')" onchange="filtradoEntreDosSelects('SexoSelect', 'InstructorSelect', 'data-fk-sexo')">
                                                        <option value="" disabled selected hidden>Seleccione un Sexo</option>
                                                        <% for (Sexo sexo : listaSexo) {%>
                                                        <option value="<%=sexo.getIdsexo()%>"><%=sexo.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">sexo</label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="instructor" class="form-select mx-auto" id="InstructorSelect">
                                                        <option value="" disabled selected hidden>Seleccione un instructor asignado</option>
                                                        <% for (Instructor instru : listaInstructores) {
                                                                if (instru.getCoordinadorIdcoordinador().getIdcoordinador() == coordinadorEntrante.getIdcoordinador()) {
                                                        %>
                                                        <option value="<%=instru.getIdinstructor()%>" data-fk-sexo="<%=instru.getSexo().getIdsexo()%>"><%=instru.getNombres() + " " + instru.getApellidos()%></option>
                                                        <%  }
                                                            }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Instructores a su cargo</label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="clima" class="form-select mx-auto" id="ClimaSelect"  onclick="consultarDotacion('Dotacion', 'SexoSelect', 'ClimaSelect', 'AreaSelect')">
                                                        <option value="" disabled selected hidden>Seleccione un clima</option>
                                                        <% for (Clima clima : listaClimas) {%>
                                                        <option value="<%=clima.getIdclima()%>"><%=clima.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Clima</label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <textarea name="dotacion" class="form-control" placeholder="" id="Dotacion"  readonly style="height: 100px"></textarea>
                                                    <label for="floatingTextarea2">Dotacion Correspondiente</label>
                                                </div>
                                            </div>
                                            <input type="hidden" name="ListaElementosxCantidad" id="ListaElementosxCantidad">    
                                        </form>
                                    </div>
                                    <!-- Pie del modal -->
                                    <div class="modal-footer text-center py-3">
                                        <button type="submit" class="btn botones text-white px-4" name="accion" id="btnGuardarCaracterizacion" style="background-color: #018E42;">Guardar</button>               
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Editar Caracterizacion -->
                        <div class="modal fade" id="ModalEditarCaracterizacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-top" role="document">
                                <div class="modal-content">
                                    <!-- Encabezado del modal -->
                                    <div class="modal-header">
                                        <h5 class="modal-title">Editar Caracterizacion</h5>
                                        <button id="btnCerrarCaractEdit" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <!-- Cuerpo del modal -->
                                    <div class="modal-body">
                                        <form id="FormularioEditarCaracterizacion" class="row g-2">

                                            <input type="hidden" id="IdEdit" name="IdEdit">
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="redEdit" class="form-select mx-auto" id="redEditSelect" onchange="filtradoEntreDosSelects('redEditSelect', 'AreaEditSelect', 'data-fk-redEdit')" required>
                                                        <option value="" disabled selected hidden>Seleccione una red</option>
                                                        <% for (Red red : listaRedes) {%>
                                                        <option value="<%=red.getIdred()%>"><%=red.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Redes</label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="areaEdit" class="form-select mx-auto" id="AreaEditSelect" onclick="consultarDotacion('DotacionEdit', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect'); consultarElementos('ListaElementosEdicion', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect')">
                                                        <option value="" disabled selected hidden>Seleccione un area</option>
                                                        <% for (Area area : listaAreas) {%>
                                                        <option value="<%=area.getIdarea()%>" data-fk-redEdit="<%=area.getRedIdred().getIdred()%>"><%=area.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Areas    </label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="sexoEdit" class="form-select mx-auto" id="SexoSelectEdit"
                                                            onclick="consultarDotacion('DotacionEdit', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect'); consultarElementos('ListaElementosEdicion', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect');"
                                                            onchange="filtradoEntreDosSelects('SexoSelectEdit', 'InstructorSelectEdit', 'data-fk-sexo-Edit')">
                                                 
                                                    <option value="" disabled selected hidden>Seleccione un Sexo</option>
                                                    <% for (Sexo sexo : listaSexo) {%>
                                                    <option value="<%=sexo.getIdsexo()%>"><%=sexo.getNombre()%></option>
                                                    <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">sexo</label>
                                                </div>
                                            </div>

                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="instructorEdit" class="form-select mx-auto" id="InstructorSelectEdit">
                                                        <option value="" disabled selected hidden>Seleccione un instructor asignado</option>
                                                        <% for (Instructor instru : listaInstructores) {
                                                                if (instru.getCoordinadorIdcoordinador().getIdcoordinador() == coordinadorEntrante.getIdcoordinador()) {
                                                        %>
                                                        <option value="<%=instru.getIdinstructor()%>" data-fk-sexo-Edit="<%=instru.getSexo().getIdsexo()%>"><%=instru.getNombres() + " " + instru.getApellidos()%></option>
                                                        <%  }
                                                            }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Instructores a su cargo</label>
                                                </div>
                                            </div>

                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <select name="climaEdit" class="form-select mx-auto" id="ClimaSelectEdit" onclick="consultarDotacion('DotacionEdit', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect'); consultarElementos('ListaElementosEdicion', 'SexoSelectEdit', 'ClimaSelectEdit', 'AreaEditSelect')">

                                                        <option value="" disabled selected hidden>Seleccione un clima</option>
                                                        <% for (Clima clima : listaClimas) {%>
                                                        <option value="<%=clima.getIdclima()%>"><%=clima.getNombre()%></option>
                                                        <% }%>
                                                    </select>
                                                    <label class="text-small text-black " style="font-size: 15px" for="Centro">Clima</label>
                                                </div>
                                            </div>

                                            <div class="col-12">
                                                <div class="form-floating">
                                                    <textarea name="dotacionEdit" class="form-control" placeholder="" id="DotacionEdit"  readonly style="height: 100px"></textarea>
                                                    <label for="floatingTextarea2">Dotacion Correspondiente</label>
                                                </div>
                                            </div>
                                            <input type="hidden" name="ListaElementosEdicionAntiguos" id="ListaElementosEdicionAntiguos">    
                                            <input type="hidden" name="ListaElementosEdicion" id="ListaElementosEdicion">    
                                        </form>
                                    </div>
                                    <!-- Pie del modal -->
                                    <div class="modal-footer text-center py-3">
                                        <button id="btnEditarCarct" type="submit" class="btn botones text-white px-4" name="accion" id="btnGuardarCaracterizacion" style="background-color: #018E42;">Actualizar</button>               
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <%--CONTENIDO ELEMENTOS INICIO --%> 
                            <div class="col-12">  
                                <div class="container">
                                    <section class="section-0 d-flex justify-content-between">
                                        <h2 class="letra py-3"><strong>Gestion de Caracterizaciones</strong></h2>
                                        <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                                    </section>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header">
                                                    <h5 class="card-title mb-0">Caracterizaciones registradas</h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-md-12 col-sd-12">
                                                            <div class="input-group mb-2">
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalCaracterizacion"><b>Nueva Caracterizacion</b></button>                           
                                                                <input type="text" class="form-control" id="filtroCaracterizacion"  oninput="filtrarTabla(this.value , 'tablaCaracterizacion')">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaCaracterizacion" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th> Instructor </th>
                                                                        <th> Sexo </th>
                                                                        <th> Clima </th>
                                                                        <th> Elementos asignados </th>
                                                                        <th scope="col" class="text-center" style="width: 100px;">Opciones</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>  
                    </div>
                    <%--CONTENIDO FINAL --%>
                </main>
                <jsp:include page="/Componentes/footer2.jsp" ></jsp:include>
                </div>
            </div>

        <%--MENU--%>       
        <script src="../js/scriptMenu.js"></script>
        <script src="../js/DatosTablas.js"></script>
        <script src="../js/alertas.js"></script>
        <script src="../js/JsContainer.js"></script>
        <%--BOOTSTRAP--%>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <%--TRANSICIONES--%>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <%--ALERTAS--%>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>
<%
    }
%>