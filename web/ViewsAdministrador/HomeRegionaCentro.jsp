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
        <title>Regional y Centro</title>
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
        <%--REGIONAL--%>
        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarReg').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioRegional').serialize();
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
                                codigoElReg: id
                            };

                            // Realizar la solicitud AJAX
                            $.ajax({
                                url: '../RegionalServlet',
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
                $('#tablaRegional').on('click', '.btn-outline-danger', function () {
                    // Obtener el ID del usuario desde el atributo data del botón
                    var id = $(this).data('id');
                    eliminar(id);
                });


                $('#btnEditarReg').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioRegionalOpciones').serialize();
                    formData += '&accion=actualizar';
                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../RegionalServlet',
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
                        limpiarFormulario('FormularioRegional');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarReg");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarReg");
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
                        url: '../ConsultaRegional',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaRegional tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaRegional tbody').append('<tr><td colspan="3" class="text-center">No se encontraron redes de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, regio) {
                                    var row = '<tr>' +
                                            '<td>' + regio.codigo + '</td>' +
                                            '<td>' + regio.nombre + '</td>' +
                                            '<td class="d-flex align-items-center">' +
                                            '<li type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" data-bs-toggle="modal" data-bs-target="#ModalRegionalOpciones" ' +
                                            'onclick="obtenerDatosRegional(' + regio.codigo + ', \'' + regio.nombre + '\')"></li>' +
                                            '<li type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" data-id="' + regio.codigo + '"></li>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaRegional tbody').append(row);
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
        <%--CENTRO--%>
        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarCentro').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioCentro').serialize();
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
                                codigoElCent: id
                            };

                            // Realizar la solicitud AJAX
                            $.ajax({
                                url: '../CentroServlet',
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
                $('#tablaCentro').on('click', '.btn-outline-danger', function () {
                    // Obtener el ID del usuario desde el atributo data del botón
                    var id = $(this).data('id');
                    eliminar(id);
                });
                $('#btnEditarCent').click(function (event) {
                    event.preventDefault();
                    var formData = $('#FormularioCentroOpciones').serialize();
                    formData += '&accion=actualizar';
                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../CentroServlet',
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
                        limpiarFormulario('FormularioCentro');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarCent");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarCent");
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
                        url: '../ConsultaCentro',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaCentro tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaCentro tbody').append('<tr><td colspan="3" class="text-center">No se encontraron centros en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, centro) {
                                    var row = '<tr>' +
                                            '<td>' + centro.codigo + '</td>' +
                                            '<td>' + centro.nombre + '</td>' +
                                            '<td>' + centro.regNombre + '</td>' +
                                            '<td class="d-flex align-items-center">' +
                                            '<li type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" data-bs-toggle="modal" data-bs-target="#ModalCentroOpciones" ' +
                                            'onclick="obtenerDatosCentro(' + centro.codigo + ', \'' + centro.nombre + '\', \'' + centro.reglId + '\')"></li>' +
                                            '<li type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" data-id="' + centro.codigo + '"></li>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaCentro tbody').append(row);
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
                                        <h2 class="letra py-3"><strong>Gestion de regionales y centro</strong></h2>
                                        <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                                    </section>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header">
                                                    <h5 class="card-title mb-0">Regionales Registradas</h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-12">
                                                            <div class="input-group mb-2">
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalRegional"><b>Nueva regional</b></button>
                                                                <input type="text" class="form-control" id="filtro1" oninput="filtrarTabla(this.value, 'tablaRegional')" >
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaRegional" class="table table-bordered table-hover table-striped">
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
                            <%--CONTENIDO CENTRO INICIO --%> 
                            <div class="col-12">  

                                <div class="container">
                                    <div class="row">
                                        <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                            <div class="card flex-fill w-100">
                                                <div class="card-header mb-0">
                                                    <h5 class="card-title mb-0">Centros Registrados: </h5>
                                                </div>
                                                <div class="card-body px-4" style="min-height: 200px; max-height: 500px; overflow: auto;">
                                                    <div class="input-group mb-3 mt-2 p-2">
                                                        <div class="col-12 col-sd-12">
                                                            <div class="input-group mb-2">
                                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalCentro"><b>Nuevo Centro</b></button>
                                                                <input type="text" class="form-control" id="filtro2" oninput="filtrarTabla(this.value, 'tablaCentro')">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                            <table id="tablaCentro" class="table table-bordered table-hover table-striped">
                                                                <thead class="sticky-top bg-success-subtle">
                                                                    <tr>
                                                                        <th scope="col">Codigo</th>
                                                                        <th>Nombre</th>
                                                                        <th>Regional</th>
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
                            <%--CONTENIDO CENTRO FINAL --%>
                        </div>  
                    </div>
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
<%}%>