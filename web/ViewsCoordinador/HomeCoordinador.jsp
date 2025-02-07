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
        <title>Menu Principal</title>
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
            $(document).ready(function () {
                // Evento para el botón "Eliminar todo"
                $('#btnEliminarAll').click(function () {
                    Swal.fire({
                        title: "Confirmación de eliminación",
                        text: "¿Está seguro de eliminar todos los elementos?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "¡Sí, eliminar todo!"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Realizar solicitud AJAX al servlet
                            $.ajax({
                                url: '../ElementosServlet',
                                type: 'POST',
                                data: {accion: 'eliminarAll'},
                                dataType: 'json',
                                success: function (response) {
                                    if (response.estado === "exito") {
                                        Swal.fire("¡Eliminado!", response.mensaje, "success").then(() => {
                                            cargarTabla(); // Recargar la tabla
                                        });
                                    } else {
                                        Swal.fire("Error", response.mensaje, "error");
                                    }
                                },
                                error: function () {
                                    Swal.fire("Error", "No se pudo conectar con el servidor.", "error");
                                }
                            });
                        }
                    });
                });

                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaElementos',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaElementos tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaElementos tbody').append('<tr><td colspan="3" class="text-center">No se encontraron redes de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, elem) {
                                    var row = '<tr>' +
                                            '<td>' + elem.codigo + '</td>' +
                                            '<td>' + elem.nombre + '</td>' +
                                            '<td>' + elem.cantidades + '</td>' +
                                            '</tr>';
                                    $('#tablaElementos tbody').append(row);
                                });
                            }
                        },
                        error: function (xhr, status, error) {
                            handleError('Error al obtener los datos: ' + error);
                        }
                    });
                }
                cargarTabla();
            });
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
                        <div class="row">
                            <%--CONTENIDO ELEMENTOS INICIO --%> 
                            <div class="col-12">  
                                <div class="container">
                                    <section class="section-0 d-flex justify-content-between">
                                        <h2 class="letra py-3"><strong>Gestion y control de elementos</strong></h2>
                                        <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                                    </section>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header">
                                                    <h5 class="card-title">Elementos Registrados</h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group p-2">
                                                        <div class="col-12 ">
                                                            <div class="input-group mb-2">    
                                                                <button id="btnEliminarAll" class="btn btn-danger">Eliminar Todo</button>
                                                                <input type="text" class="form-control" id="filtro" oninput="filtrarTabla(this.value, 'tablaElementos')">
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaElementos" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th>Nombre</th>
                                                                        <th>Cantidad</th>
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
                            <%--CONTENIDO ELEMENTOS FINAL --%> 
                        </div>  
                    </div>
                    <%--CONTENIDO FINAL --%>
                </main>
                <jsp:include page="/Componentes/footer2.jsp" ></jsp:include>
                </div>
            </div>
        <jsp:include page="../Componentes/modalGuardar.jsp" ></jsp:include> 
        <jsp:include page="../Componentes/modalOpciones.jsp" ></jsp:include> 

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
        <script>

        </script>

    </body>
</html>
<%    }
%>