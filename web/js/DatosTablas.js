function obtenerDatosSexo(codigoSex, nombreSex) {
    $('#codigoOpSexo').val(codigoSex);
    $('#nombreOpSexo').val(nombreSex);
}

function obtenerDatosClima(codigoCli, nombreCli) {
    $('#codigoOpCli').val(codigoCli);
    $('#nombreOpCli').val(nombreCli);
}

function obtenerDatosRed(codigoRed, nombreRed) {
    $('#codigoOpRe').val(codigoRed);
    $('#nombreOpRe').val(nombreRed);
}

function obtenerDatosArea(codigoAre, nombreAre, ListaReds) {
    $('#codigoOpArea').val(codigoAre);
    $('#nombreOpArea').val(nombreAre);
    $("#RedesListaEl").val(ListaReds);
    /*Se seleccion automaticamnete el la red a la que corresponde centro*/
    $("#RedesListaEl").select(ListaReds);
}

function obtenerDatosRegional(codigoRegi, nombreRegi) {
    $('#codigoOpReg').val(codigoRegi);
    $('#nombreOpReg').val(nombreRegi);
}


function obtenerDatosCentro(codigoCen, nombreCen, ListaRegio) {
    $('#codigoOpCent').val(codigoCen);
    $('#nombreOpCent').val(nombreCen);
    $("#CentroListaEl").val(ListaRegio);
    /*Aqui es para que los selects se asignen*/
    $("#CentroListaEl").select(ListaRegio);

}


function obtenerDatosElem(codigoElem, nombreElem, cantidadElem) {
    $('#codigoElementoOp').val(codigoElem);
    $('#nombreElementoOp').val(nombreElem);
    $('#cantidadElementoOp').val(cantidadElem);
}
function obtenerDatosUsuarios(cedulaUsuario, nombreUsuario, apellido, Rol, correo) {
    $('#CedulaUsuarioOp').val(cedulaUsuario);
    $('#nombreOp').val(nombreUsuario);
    $('#apellidoOp').val(apellido);
    $('#emailOp').val(correo);

    // Asignar el valor al select de rol y activar el cambio
    $('#Rolesop').val(Rol.toString()).change();

    // Consultar datos adicionales
    $.ajax({
        url: '../Busquedas/BusquedaUsuario.jsp', // Ajusta el URL según la ubicación de tu JSP
        type: 'POST',
        data: {
            Cedula: cedulaUsuario,
            Rol: Rol
        },
        dataType: 'json',
        success: function (data) {
            let regional = (data.Regional != null) ? data.Regional : "";
            let centro = (data.Centro != null) ? data.Centro : "";
            let sexo = (data.Sexo != null) ? data.Sexo : "";
            let telefono = (data.Telefono != null) ? data.Telefono : "";
            let coordinador = (data.Coordinador != null) ? data.Coordinador : "";
            console.log(data);
            
            // Asignar valores a los campos
            $('#RegionalSelectOp').val(regional).change(); // Activar el cambio para aplicar el filtrado
           
            // Esperar a que el filtrado se complete antes de asignar los valores
            setTimeout(function () {
                $('#CentroSelectOp').val(centro).change(); // Activar el cambio para aplicar el filtrado de Coordinador
                $('#CoordinadorSelectOp').val(coordinador);
            }, 100); // Ajusta el tiempo según el comportamiento del filtrado
            $('#TelefonoOp').val(telefono);
            $('#SexoOp').val(sexo)
        },
        error: function (xhr, status, error) {
            console.error('Error al obtener los datos: ', error);
        }
    });

    // Activar el cambio en el select de Regional para ajustar el filtrado
    $('#RegionalSelectOp').change(function () {
        filtradoEntreDosSelects('RegionalSelectOp', 'CentroSelectOp', 'data-fk-centroOp');
    });

    // Activar el cambio en el select de Centro para ajustar el filtrado
    $('#CentroSelectOp').change(function () {
        filtradoEntreDosSelects('CentroSelectOp', 'CoordinadorSelectOp', 'data-centro-fk');

        // Asegurarse de que la opción correcta esté seleccionada en CoordinadorSelectOp
        let coordinadorSelect = document.getElementById('CoordinadorSelectOp');
        let coordinadorValue = $('#CoordinadorSelectOp').val();

        // Esperar a que se actualicen las opciones visibles
        setTimeout(function () {
            for (let i = 0; i < coordinadorSelect.options.length; i++) {
                let option = coordinadorSelect.options[i];
                if (option.value === coordinadorValue && option.style.display !== 'none') {
                    coordinadorSelect.value = coordinadorValue;
                    break;
                }
            }
        }, 100); // Ajusta el tiempo según el comportamiento del filtrado
    });
}
//FILTRADO DOTACION INICIO
$(document).ready(function () {
    // Inicializa Select2

    $('#ElementoListaForGdDo').select2({
        placeholder: "-- Elija --",
        dropdownParent: $('#ModalDotacion'),
        width: 'resolve'
    });

    $('#ElementoListaForGdDotacion').select2({
        dropdownParent: $('#ModalDotacionOpciones'),
        width: 'resolve'
    });

    // Filtrado de tabla
    $('#filtroDotacionr').on('input', function () {
        var valorFiltro = $(this).val().toLowerCase();
        $('#tablaDotacion tbody tr').filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1);
        });
    });
});
//FILTRADO DOTACION FINAL

function obtenerDatosDotacion(codigoDta, areaDta, sexoDta, climaDta, ElementoDta, cantidadDta) {
    $('#codigoDotacionOp').val(codigoDta);
    $('#AreaListaForGdDotacion').val(areaDta).trigger('change');
    $("#SexoListaForGdDotacion").val(sexoDta).trigger('change');
    $("#ClimaListaForGdDotacion").val(climaDta).trigger('change');
    $("#ElementoListaForGdDotacion").val(ElementoDta).trigger('change');
    $("#cantidadGdDotacion").val(cantidadDta);

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerArea.jsp",
        data: {area: areaDta},
        dataType: "html",
        success: function (data) {
            $("#AreaListaForGdDotacion").empty().append(data).trigger('change');
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSexo.jsp",
        data: {sexo: sexoDta},
        dataType: "html",
        success: function (data) {
            $("#SexoListaForGdDotacion").empty().append(data).trigger('change');
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerClima.jsp",
        data: {clima: climaDta},
        dataType: "html",
        success: function (data) {
            $("#ClimaListaForGdDotacion").empty().append(data).trigger('change');
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerElemento.jsp",
        data: {elemento: ElementoDta},
        dataType: "html",
        success: function (data) {
            $("#ElementoListaForGdDotacion").empty().append(data).trigger('change');
        }
    });
}
