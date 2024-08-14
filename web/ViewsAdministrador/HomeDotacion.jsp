<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenida = request.getSession();
    if (sessionObtenida.getAttribute("administrador") == null) {
        response.sendRedirect("../CerradoSession.jsp");
    } else {
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dotacion</title>
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
        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarDotacion').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioDotacion').serialize();
                    formData += '&accion=guardar';
                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                $('#btnEliminarDta').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioDotacionOpciones').serialize();
                    formData += '&accion=eliminar';
                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                $('#btnEditarDta').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioDotacionOpciones').serialize();
                    formData += '&accion=actualizar';
                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../DotacionServlet',
                        data: formData,
                        success: function (response) {
                            successCallback(response);
                        },
                        error: function (xhr, status, error) {
                            errorCallback('Error al conectar con el servlet: ' + error);
                        }
                    });
                }

                function limpiarFormulario(formularioId) {
                    $('#' + formularioId)[0].reset();
                }

                function handleSuccessGuardar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        limpiarFormulario('FormularioDotacion');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarDta");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarDta");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }
                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaDotacion',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaDotacion tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaDotacion tbody').append('<tr><td colspan="7" class="text-center">No se encontraron dotaciones de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, dota) {
                                    var row = '<tr>' +
                                            '<td>' + dota.idDotacion + '</td>' +
                                            '<td>' + dota.areaNombre + '</td>' +
                                            '<td>' + dota.sexoNombre + '</td>' +
                                            '<td>' + dota.climaNombre + '</td>' +
                                            '<td>' + dota.elementoNombre + '</td>' +
                                            '<td>' + dota.cantidad + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalDotacionOpciones" ' +
                                            'onclick="obtenerDatosDotacion(' + dota.idDotacion + ', \'' + dota.areaId + '\', \'' + dota.sexoId + '\'\n\
                                            , \'' + dota.climaId + '\', \'' + dota.elementoId + '\', \'' + dota.cantidad + '\')">Opciones</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaDotacion tbody').append(row);
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
        <%--STILO DE IMPUT FILTRADO--%>
        <style>
            .select2-container {
                width: 66% !important; /* Asegura que el contenedor de Select2 ocupe todo el ancho del contenedor padre */
            }
            .select2-container .select2-selection--single {
                height: calc(2.25rem + 2px); /* Ajusta la altura para coincidir con otros inputs */

            }
            .select2-container--default .select2-selection--single .select2-selection__rendered {
                line-height: 2.25rem; /* Alinea el texto verticalmente */
            }
            .select2-container--default .select2-selection--single .select2-selection__arrow {
                height: calc(2.25rem + 2px); /* Alinea la flecha verticalmente */
            }
        </style>

     
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
                                        <h2 class="letra py-3">Informacion de Dotacion</h2>
                                        <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                                    </section>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-md-4 col-sd-12">
                                                            <div class="input-group mb-2">
                                                                <div class="input-group-text col-md-6 col-8"><b>Nueva Dotacion:</b> </div>
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalDotacion"><b>Formulario</b></button>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-8 col-sd-12">
                                                            <div class="input-group mb-2">
                                                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                                <input type="text" class="form-control" id="filtroDotacionr">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaDotacion" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th> Área </th>
                                                                        <th>Sexo</th>
                                                                        <th>Clima</th>
                                                                        <th>Elemento</th>
                                                                        <th>Cantidad</th>
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
<% }%>

