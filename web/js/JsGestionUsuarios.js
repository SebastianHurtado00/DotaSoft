/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    // Manejador de evento para el botón de guardar en el formulario
    $('#saveAdmin').click(function (event) {
        event.preventDefault();
        var formData = $('#formularioUsuarios').serialize();
        formData += '&accion=guaradarUsuarios';
        enviarPeticion(formData, handleSuccessGuardar, handleError);
    });


    $('#btnactualizarUser').click(function (event) {
        event.preventDefault();
        var formData = $('#formularioEditarUsuarios').serialize();
        formData += '&accion=actualizar';
        enviarPeticion(formData, handleSuccessActualizar, handleError);
    });

    function eliminar(id) {
        Swal.fire({
            title: "Confirmación de eliminación",
            text: "¿Está seguro de eliminar este usuario?",
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
                    IdEliminar: id
                };

                // Realizar la solicitud AJAX
                $.ajax({
                    url: '../logica_usuarios',
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
    $('#tablaUsuarios').on('click', '.btn-outline-danger', function () {
        // Obtener el ID del usuario desde el atributo data del botón
        var id = $(this).data('id');
        eliminar(id);
    });

    function enviarPeticion(formData, successCallback, errorCallback) {
        $.ajax({
            type: 'POST',
            url: '../logica_usuarios',
            data: formData,
            success: function (response) {
                successCallback(response);
            },
            error: function (xhr, status, error) {
                errorCallback('Error al guardar' + error);
            }
        });
    }

    function limpiarFormulario(formularioId) {
        $('#' + formularioId)[0].reset();
    }

    function handleSuccessGuardar(response) {
        if (response.estado === "exito") {
            mostrarExito(response.mensaje);
            limpiarFormulario('formularioUsuarios');
            cargarTablaUsuarios();
        } else {
            mostrarError(response.mensaje);
        }
    }

    function handleSuccessActualizar(response) {
        if (response.estado === "exito") {
            var boton = document.getElementById("btnCerrarUser");
            boton.click();
            mostrarExito(response.mensaje);
            cargarTablaUsuarios();
        } else {
            mostrarError(response.mensaje);
        }
    }

    function handleSuccessEliminar(response) {
        if (response.estado === "exito") {
            mostrarExito(response.mensaje);
            cargarTablaUsuarios()();
        } else {
            mostrarError(response.mensaje);
        }
    }

    function handleError(errorMessage) {
        mostrarError(errorMessage);
    }
    function cargarTablaUsuarios() {
        $.ajax({
            type: 'GET',
            url: '../ConsultaUsuarios',
            dataType: 'json',
            success: function (data) {
                $('#tablaUsuarios tbody').empty();
                if (data.length === 0) {
                    // Si no hay datos, agregar una fila indicando que no se encontraron usuarios
                    $('#tablaUsuarios tbody').append('<tr><td colspan="5" class="text-center">No se encontraron usuarios en la base de datos.</td></tr>');
                } else {
                    $.each(data, function (index, usuario) {
                        let estado = (usuario.Estado == 1) ? "Activo" : "Inactivo";
                        let rol = (usuario.Rol == 0) ? "Administrador" : (usuario.Rol == 1) ? "Coordinador" : (usuario.Rol == 2) ? "Instructor" : "Recursos Humanos"
                        var row = '<tr>' +
                                '<td>' + usuario.NumeroCC + '</td>' +
                                '<td>' + usuario.NombreCompleto + '</td>' +
                                '<td>' + usuario.Apellido + '</td>' +
                                '<td>' + rol + '</td>' +
                                '<td>' + estado + '</td>' +
                                '<td>' +
                                '<li type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" data-bs-toggle="modal" data-bs-target="#ModalModificarUsuario" ' +
                                'onclick="obtenerDatosUsuarios(\'' + usuario.NumeroCC + '\', \'' + usuario.NombreCompleto + '\', \'' + usuario.Apellido + '\', \'' + usuario.Rol + '\')"></li>' +
                                '<li type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" data-id="' + usuario.NumeroCC + '"></li>' +
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
});


