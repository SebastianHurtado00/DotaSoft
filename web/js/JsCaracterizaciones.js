/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


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