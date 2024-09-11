/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/* Se debe referenciar por ids ya que como son dos modales que 
 * usaran el js los id de los select cambian Falta el refenrencaiado y 
 * Modificar donde se llama el metodo*/
function consultarDotacion(Idinput, IdSexo, IdClima, IdArea) {
    // Obtener valores de los filtros de los elementos de entrada (input, select, etc.)
    const sexoId = document.getElementById(IdSexo).value;
    const climaId = document.getElementById(IdClima).value;
    const areaId = document.getElementById(IdArea).value;

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


    $('#btnEditarCarct').click(function (event) {
        event.preventDefault();
        var formData = $('#FormularioEditarCaracterizacion').serialize();
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

   function eliminarCaract(id) {
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
                url: '../CaracterizacionServlet',
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
                    
                    // Imprimir detalles del error en la consola
                    console.error('Error en la solicitud AJAX:', error);
                    console.log('Estado:', status);
                    console.log('Respuesta del servidor:', xhr.responseText);
                }
            });
        }
    });
}
    
      // Activa el boton eliminar
    $('#tablaCaracterizacion').on('click', '.btn-outline-danger', function () {
        // Obtener el ID del usuario desde el atributo data del botón
        var id = $(this).data('id');
        eliminarCaract(id);
    });


    /*Falta Logica de eliminado*/
    function handleSuccessEliminar(response) {
        if (response.estado === "exito") {
            mostrarExito(response.mensaje);
            cargarTabla();
        } else {
            mostrarError(response.mensaje);
        }
    }

    function handleSuccessActualizar(response) {
        if (response.estado === "exito") {
            var boton = document.getElementById("btnCerrarCaractEdit");
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
                        // Procesar el contenido del popover si es una cadena de texto
                        var contenidoPopover = caract.elementosAsignados;

                        var row = '<tr>' +
                                '<td>' + caract.idCaracterizacion + '</td>' +
                                '<td>' + caract.InstructorNombreApellido + '</td>' +
                                '<td>' + caract.SexoNombre + '</td>' +
                                '<td>' + caract.climaNombre + '</td>' +
                                '<td class="text-center p-1" style="width: 140px;">' +
                                '<span tabindex="0" class="d-inline-block" data-bs-toggle="popover" data-bs-trigger="hover focus" ' +
                                'data-bs-content="' + contenidoPopover.replace(/"/g, '&quot;').replace(/'/g, "&#39;") + '" ' +
                                'style="white-space: pre-wrap;">' +
                                '<button class="btn btn-outline-success btn-sm mx-auto d-block" type="button" style="width: 100px; font-size: 12px; padding: 4px;">Ver Dotación</button>' +
                                '</span>' +
                                '</td>' +
                                '<td class="d-flex align-items-center">' +
                                '<button type="button" class="btn btn-outline-warning btn-sm bi bi-pencil-fill mx-2" ' +
                                'data-bs-toggle="modal" data-bs-target="#ModalEditarCaracterizacion" ' +
                                'onclick="obtenerDatosCaracterizacion(\'' + caract.redId + '\', ' +
                                '\'' + caract.idCaracterizacion + '\', ' +
                                '\'' + caract.areaId + '\', ' +
                                '\'' + caract.SexoId + '\', ' +
                                '\'' + caract.idInstructor + '\', ' +
                                '\'' + caract.climaId + '\', ' +
                                '\'' + caract.elementosAsignados.replace(/'/g, "\\'").replace(/\n/g, '\\n').replace(/\r/g, '\\r') + '\')">' +
                                '</button>' +
                                '<button type="button" class="btn btn-outline-danger btn-sm bi bi-trash-fill mx-2" ' +
                                'data-id="' + caract.idCaracterizacion + '"></button>' +
                                '</td>' +
                                '</tr>';
                        $('#tablaCaracterizacion tbody').append(row);
                    });

                    // Inicializar los popovers después de agregar las filas a la tabla
                    $('[data-bs-toggle="popover"]').popover({
                        html: true,
                        placement: 'top'
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