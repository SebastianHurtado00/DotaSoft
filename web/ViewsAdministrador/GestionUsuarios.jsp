<%-- 
    Document   : GestionUsuarios
    Created on : 6/08/2024, 05:22:25 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Coordinador"%>
<%@page import="Controladores.CoordinadorJpaController"%>
<%@page import="Controladores.RegionalJpaController"%>
<%@page import="Entidades.Regional"%>
<%@page import="Entidades.Centro"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.CentroJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion de usuarios</title>
        <link rel="shortcut icon" href="../assests/" type="image/x-icon">
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
        <script src="../js/tablas.js"></script>       
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/Componentes/SideBar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="/Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                        <section class="section-0 d-flex justify-content-between">
                            <h3><strong>Gestion y control de usuarios</strong></h3>
                            <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                        </section>
                        <!-- MODAL REGISTRO USUARIOS INICIO -->
                        <div class="modal fade" id="ModalGuardarUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Registros de usuarios</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <form method="post" action="<%=request.getContextPath()%>/logica_usuarios">
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
                                                RegionalJpaController regional = new RegionalJpaController();
                                                List<Regional> listaRegional = regional.findRegionalEntities();

                                            %>
                                            <div class="col-md-6 col-sm-12 form-floating text-center mt-2" id="Centro" style="display: none">
                                                <select name="centro" class="form-select mx-auto" id="Centro" >
                                                    <option value="" disabled selected hidden>Seleccione un centro</option>
                                                    <% for (Centro centro : listaCentro) {%>
                                                    <option value="<%=centro.getIdcentro()%>"><%=centro.getNombre()%></option>
                                                    <% }%>
                                                </select>
                                                <label class="text-small text-black mx-2 " style="font-size: 15px" for="Centro">Centro</label>
                                            </div>

                                            <div class="col-md-6 col-sm-12 form-floating text-center mt-2" id="Regional" style="display: none">
                                                <select name="regional" class="form-select mx-auto" id="Regional" >
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
                                                <select name="Coordinador" class="form-select mx-auto"  >
                                                    <option value="" disabled selected hidden>Seleccione un coordinador</option>
                                                    <% for (Coordinador coordinador : coordinadorList) {%>
                                                    <option value="<%=coordinador.getIdcoordinador()%>"><%=coordinador.getNombres() + " " + coordinador.getApellidos()%></option>
                                                    <% }%>
                                                </select>
                                                <label class="text-small text-black mx-2" style="font-size: 15px" for="Centro">Coordinador a cargo</label>
                                            </div>

                                            <div class="col-md-12 col-sm-12 form-floating mt-2" id="Email" style="display: none">
                                                <input name="email" type="email" class="form-control mb-2" id="InputCorreo" >
                                                <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Correo</label>
                                            </div>

                                            <div class="col-md-6 col-sm-12 form-floating mt-2" id="Telefono" style="display: none">
                                                <input name="telefono" type="number" class="form-control mb-2" id="InputTelefono" >
                                                <label class="text-small mx-2 text-black" style="font-size: 15px" for="InputCorreo">Telefono</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button name="action" value="guaradarUsuarios"  class="btn btn-success">Guardar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- MODAL REGISTRO USUARIOS FINAL -->
                    <div class="row">
                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                            <div class="card flex-fill w-100">
                                <div class="card-header">
                                    <h5 class="card-title mb-0">Usuarios Registrados: </h5>
                                </div>
                                <div class="card-body px-4">
                                    <div class="input-group mb-3 mt-2 p-2">
                                        <button class="btn btn-success hover py-2" style="background: #018E42 ; font-size: 12px" data-bs-toggle="modal" data-bs-target="#ModalGuardarUsuario"><a>Añadir Usuario </a></button>
                                        <input type="text" class="form-control" placeholder="Busqueda de Registros"
                                               name="Dato_User" id="filtro" style="font-size: 13px">
                                    </div>

                                    <div class="table-responsive" >
                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 400px">
                                            <table id="tablaUsuarios" class="table table-bordered table-hover table-striped">
                                                <thead class="sticky-top bg-success-subtle">
                                                    <tr>
                                                        <th class="" scope="col">N° Cedula</th>
                                                        <th>Nombre Completo</th>
                                                        <th scope="col">Rol</th>
                                                        <th scope="col">Estado</th>
                                                        <th scope="col" class="text-center" style="width: 100px;">Configuración</th>
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
                </main>
                <jsp:include page="../Componentes/modalGuardar.jsp" ></jsp:include> 
                <jsp:include page="/Componentes/footer2.jsp" ></jsp:include>
                </div>
            </div>
        <%--MENU--%>       
        <script src="../js/scriptMenu.js"></script>
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
        <script>
                                                        function cargarTablaUsuarios() {
                                                            $.ajax({
                                                                type: 'GET',
                                                                url: '../ConsultaUsuarios',
                                                                dataType: 'json',
                                                                success: function (data) {
                                                                    $('#tablaSexo tbody').empty();
                                                                    if (data.length === 0) {
                                                                        // Si no hay datos, agregar una fila indicando que no se encontraron usuarios
                                                                        $('#tablaUsuarios tbody').append('<tr><td colspan="3" class="text-center">No se encontraron redes de trabajo en la base de datos.</td></tr>');
                                                                    } else {
                                                                        $.each(data, function (index, usuario) {
                                                                            let estado = (usuario.Estado == 1) ? "Activo" : "Inactivo";
                                                                            let rol = (usuario.Rol == 0) ? "Administrador" : (usuario.Rol == 1) ? "Coordinador" : (usuario.Rol == 2) ? "Instructor" : "Recursos Humanos"
                                                                            var row = '<tr>' +
                                                                                    '<td>' + usuario.NumeroCC + '</td>' +
                                                                                    '<td>' + usuario.NombreCompleto + '</td>' +
                                                                                    '<td>' + rol + '</td>' +
                                                                                    '<td>' + estado + '</td>' +
                                                                                    '<td>' +
                                                                                    '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalAreasOpciones" ' +
                                                                                    'onclick="">Opciones</button>' +
                                                                                    '</td>' +
                                                                                    '</tr>';
                                                                            $('#tablaUsuarios tbody').append(row);
                                                                        });
                                                                    }
                                                                },
                                                                error: function (xhr, status, error) {
                                                                    handleError('Error al obtener los datos: ' + error);
                                                                }
                                                            });
                                                        }
                                                        cargarTablaUsuarios();
        </script>
    </body>
</html>
