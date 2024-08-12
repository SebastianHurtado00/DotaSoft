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

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerRed.jsp",
        data: {red: ListaReds},
        dataType: "html",
        success: function (data) {
            $("#RedesListaEl").empty().append(data);
        }
    });
}

function obtenerDatosRegional(codigoRegi, nombreRegi) {
    $('#codigoOpReg').val(codigoRegi);
    $('#nombreOpReg').val(nombreRegi);
}


function obtenerDatosCentro(codigoCen, nombreCen, ListaRegio) {
    $('#codigoOpCent').val(codigoCen);
    $('#nombreOpCent').val(nombreCen);
    $("#CentroListaEl").val(ListaRegio);

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerCentro.jsp",
        data: {centro: ListaRegio},
        dataType: "html",
        success: function (data) {
            $("#CentroListaEl").empty().append(data);
        }
    });
}

function obtenerDatosElem(codigoElem, nombreElem, cantidadElem) {
    $('#codigoElementoOp').val(codigoElem);
    $('#nombreElementoOp').val(nombreElem);
    $('#cantidadElementoOp').val(cantidadElem);
}
function obtenerDatosUsuarios(cedulaUsuario, nombreUsuario, apellido, Rol, Regional) {
    $('#CedulaUsuarioOp').val(cedulaUsuario);
    $('#nombreOp').val(nombreUsuario);
    $('#apellidoOp').val(apellido);

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
            let correo = (data.Correo != null) ? data.Correo : "";
            let telefono = (data.Telefono != null) ? data.Telefono : "";
            let coordinador = (data.Coordinador != null) ? data.Coordinador : "";

            // Asignar valores a los campos
            $('#RegionalSelectOp').val(regional).change(); // Activar el cambio para aplicar el filtrado
            $('#emailOp').val(correo);
            $('#TelefonoOp').val(telefono);
            
            // Esperar a que el filtrado se complete antes de asignar los valores
            setTimeout(function() {
                $('#CentroSelectOp').val(centro).change(); // Activar el cambio para aplicar el filtrado de Coordinador
                $('#CoordinadorSelectOp').val(coordinador);
            }, 100); // Ajusta el tiempo según el comportamiento del filtrado
        },
        error: function (xhr, status, error) {
            console.error('Error al obtener los datos: ', error);
        }
    });

    // Activar el cambio en el select de Regional para ajustar el filtrado
    $('#RegionalSelectOp').change(function() {
        filtradoEntreDosSelects('RegionalSelectOp', 'CentroSelectOp', 'data-fk-centroOp');
    });

    // Activar el cambio en el select de Centro para ajustar el filtrado
    $('#CentroSelectOp').change(function() {
        filtradoEntreDosSelects('CentroSelectOp', 'CoordinadorSelectOp', 'data-centro-fk');
        
        // Asegurarse de que la opción correcta esté seleccionada en CoordinadorSelectOp
        let coordinadorSelect = document.getElementById('CoordinadorSelectOp');
        let coordinadorValue = $('#CoordinadorSelectOp').val();
        
        // Esperar a que se actualicen las opciones visibles
        setTimeout(function() {
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