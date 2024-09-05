/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/* Se debe referenciar por ids ya que como son dos modales que 
 * usaran el js los id de los select cambian Falta el refenrencaiado y 
 * Modificar donde se llama el metodo*/
function consultarDotacion(Idinput) {
    // Obtener valores de los filtros de los elementos de entrada (input, select, etc.)
    const sexoId = document.getElementById("SexoSelect").value;
    const climaId = document.getElementById("ClimaSelect").value;
    const areaId = document.getElementById("AreaSelect").value;

    // Realizar la petición AJAX
    $.ajax({
        url: '../FiltroJsonDotacion',
        method: 'GET',
        data: {
            sexoId: sexoId,
            climaId: climaId,
            areaId: areaId
        },
        dataType: 'json',
        success: function (data) {
            // Limpia el contenido del textarea
            $('#' + Idinput + '').val('');

            // Iterar sobre cada elemento en el JSON
            data.forEach(function (element) {
                // Agregar el texto de 'elementosAsignados' al textarea
                $('#' + Idinput + '').val(function (index, val) {
                    return val + element.cantidad + " - " + element.elementoNombre + '\n';
                });
            });
        },
        error: function (xhr, status, error) {
            console.error('Error en la consulta:', error);
        }
    });
}


$(document).ready(function () {
    // Manejador de evento para el botón de guardar en el formulario
    $('#btnGuardarCaracterizacion').click(function (event) {
        event.preventDefault();
        var formData = $('#FormularioCaracterizacion').serialize();
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
            url: '../CaracterizacionServlet',
            data: formData,
            success: function (response) {
                successCallback(response);
            },
            error: function (xhr, status, error) {
                errorCallback('Verifique que todos los campos hayan sido diligenciados');
            }
        });
    }

    function limpiarFormulario(formularioId) {
        $('#' + formularioId)[0].reset();
    }

    function handleSuccessGuardar(response) {
        if (response.estado === "exito") {
            mostrarExito(response.mensaje);
            limpiarFormulario('FormularioCaracterizacion');
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
            url: '../ConsultaCaracterizacion',
            dataType: 'json',
            success: function (data) {
                $('#tablaCaracterizacion tbody').empty();
                if (data.length === 0) {
                    // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                    $('#tablaCaracterizacion tbody').append('<tr><td colspan="7" class="text-center">No se encontraron Caracterizaciones en la base de datos.</td></tr>');
                } else {
                    $.each(data, function (index, caract) {
                        var row = '<tr>' +
                                '<td>' + caract.idCaracterizacion + '</td>' +
                                '<td>' + caract.InstructorNombreApellido + '</td>' +
                                '<td>' + caract.climaNombre + '</td>' +
                                '<td>' + caract.SexoNombre + '</td>' +
                                '<td>' + caract.elementosAsignados + '</td>' +
                                '<td class="d-flex align-items-center">' +
                                '<button type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" ' +
                                'data-bs-toggle="modal" data-bs-target="#ModalEditarCaracterizacion" ' +
                                'onclick="obtenerDatosCaracterizacion(\'' + caract.redId + '\', ' +
                                '\'' + caract.areaId + '\', ' +
                                '\'' + caract.SexoId + '\', ' +
                                '\'' + caract.idInstructor + '\', ' +
                                '\'' + caract.climaId + '\', ' +
                                '\'' + caract.elementosAsignados.replace(/'/g, "\\'").replace(/\n/g, '\\n').replace(/\r/g, '\\r') + '\')">' +
                                '</button>' +
                                '<button type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" ' +
                                'data-id="' + caract.idCaracterizacion + '" onclick="eliminarDotacion(' + caract.idCaracterizacion + ')"></button>' +
                                '</td>' +
                                '</tr>';
                        $('#tablaCaracterizacion tbody').append(row);
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