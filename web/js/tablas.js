function cargarTablaRed() {
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
                $.each(data, function (index, redes) {
                    var row = '<tr>' +
                            '<td>' + redes.codigo + '</td>' +
                            '<td>' + redes.nombre + '</td>' +
                            '<td>' +
                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalAreasOpciones" ' +
                            'onclick="obtenerDatosAreas(' + redes.codigo + ', \'' + redes.nombre + '\')">Opciones</button>' +
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
cargarTablaRed();
function cargarTablaAreas() {
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
                    var row = '<tr>' +
                            '<td>' + areas.codigo + '</td>' +
                            '<td>' + areas.nombre + '</td>' +
                            '<td>' + areas.redNombre + '</td>' +
                            '<td>' +
                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalAreasOpciones" ' +
                            'onclick="obtenerDatosAreas(' + areas.codigo + ', \'' + areas.nombre + '\', \'' + areas.redlId + '\')">Opciones</button>' +
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
cargarTablaAreas();
   