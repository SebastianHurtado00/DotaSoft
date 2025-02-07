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
        <title>Red y Area</title>
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
        <%--RED--%>
        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarRed').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioRed').serialize();
                    formData += '&accion=guardar';
                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                function eliminar(id) {
                    Swal.fire({
                        title: "Confirmación de eliminación",
                        text: "¿Está seguro de eliminar este registro?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "Sí, Eliminar!"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Preparar datos para la solicitud AJAX
                            var formData = {
                                accion: 'eliminar',
                                codigoElRe: id
                            };

                            // Realizar la solicitud AJAX
                            $.ajax({
                                url: '../RedServlet',
                                type: 'POST',
                                data: formData,
                                dataType: 'json',
                                success: function (response) {
                                    // Llamar a la función handleSuccessEliminar con la respuesta del servidor
                                    handleSuccessEliminar(response);
                                },
                                error: function (xhr, status, error) {
                                    // Llamar a la función handleError con un mensaje de error
                                    handleError('Error al realizar la operación de eliminación.');
                                }
                            });
                        }
                    });
                }


                // Activa el boton eliminar
                $('#tablaRed').on('click', '.btn-outline-danger', function () {
                    // Obtener el ID del usuario desde el atributo data del botón
                    var id = $(this).data('id');
                    eliminar(id);
                });


                $('#btnEditarRed').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioRedOpciones').serialize();
                    formData += '&accion=actualizar';
                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../RedServlet',
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
                        limpiarFormulario('FormularioRed');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarRed");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarRed");
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
                        url: '../ConsultaRed',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaRed tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaRed tbody').append('<tr><td colspan="3" class="text-center">No se encontraron redes de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, red) {
                                    var row = '<tr>' +
                                            '<td>' + red.codigo + '</td>' +
                                            '<td>' + red.nombre + '</td>' +
                                            '<td class="d-flex align-items-center">' +
                                            '<li type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" data-bs-toggle="modal" data-bs-target="#ModalRedOpciones" ' +
                                            'onclick="obtenerDatosRed(' + red.codigo + ', \'' + red.nombre + '\')"></li>' +
                                            '<li type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" data-id="' + red.codigo + '"></li>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaRed tbody').append(row);
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
        <%--AREA--%>
        <script>
            $(document).ready(function () {
                $('#btnGuardarArea').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioArea').serialize();
                    formData += '&accion=guardar';
                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });


                function eliminar(id) {
                    Swal.fire({
                        title: "Confirmación de eliminación",
                        text: "¿Está seguro de eliminar este registro?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "Sí, Eliminar!"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Preparar datos para la solicitud AJAX
                            var formData = {
                                accion: 'eliminar',
                                codigoElArea: id
                            };

                            // Realizar la solicitud AJAX
                            $.ajax({
                                url: '../AreaServlet',
                                type: 'POST',
                                data: formData,
                                dataType: 'json',
                                success: function (response) {
                                    // Llamar a la función handleSuccessEliminar con la respuesta del servidor
                                    handleSuccessEliminar(response);
                                },
                                error: function (xhr, status, error) {
                                    // Llamar a la función handleError con un mensaje de error
                                    handleError('Error al realizar la operación de eliminación.');
                                }
                            });
                        }
                    });
                }


                // Activa el boton eliminar
                $('#tablaArea').on('click', '.btn-outline-danger', function () {
                    // Obtener el ID del usuario desde el atributo data del botón
                    var id = $(this).data('id');
                    eliminar(id);
                });


                $('#btnEditarArea').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioAreaOpciones').serialize();
                    formData += '&accion=actualizar';
                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../AreaServlet',
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
                        limpiarFormulario('FormularioArea');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarArea");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarArea");
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
                        url: '../ConsultaAreas',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaArea tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaArea tbody').append('<tr><td colspan="3" class="text-center">No se encontraron areas de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, areas) {
                                    console.log("Id Areas:" + areas.redId);
                                    var row = '<tr>' +
                                            '<td>' + areas.codigo + '</td>' +
                                            '<td>' + areas.nombre + '</td>' +
                                            '<td>' + areas.redNombre + '</td>' +
                                            '<td class="d-flex align-items-center">' +
                                            '<li type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" data-bs-toggle="modal" data-bs-target="#ModalAreaOpciones" ' +
                                            'onclick="obtenerDatosArea(' + areas.codigo + ', \'' + areas.nombre + '\', \'' + areas.redId + '\')"></li>' +
                                            '<li type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" data-id="' + areas.codigo + '"></li>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaArea tbody').append(row);
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
                            <%--CONTENIDO RED INICIO --%> 
                            <div class="col-12">  
                                <div class="container">
                                    <section class="section-0 d-flex justify-content-between">
                                        <h2 class="letra py-3">Gestiones de redes y areas</h2>
                                        <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                                    </section>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header">
                                                    <h5 class="card-title mb-0">Redes Registradas</h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-12">
                                                            <div class="input-group">                                        
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalRed"><b>Nueva Red</b></button>
                                                                <input type="text" class="form-control" id="filtro1" oninput="filtrarTabla(this.value ,  'tablaRed')">
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaRed" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th>Nombre</th>
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
                            <%--CONTENIDO RED FINAL --%> 
                            <%--CONTENIDO AREA INICIO --%>   
                            <div class="col-12">  
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header">
                                                    <h5 class="card-title mb-0">Areas Registradas</h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-12">
                                                            <div class="input-group">
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalArea"><b>Nueva Area</b></button>
                                                                <input type="text" class="form-control" id="filtro1" oninput="filtrarTabla(this.value , 'tablaArea')">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaArea" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th>Nombre</th>
                                                                        <th>Red</th>
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
                            <%--CONTENIDO AREA FINAL --%>    
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
        <script src="../js/FiltroTablas.js"></script>
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