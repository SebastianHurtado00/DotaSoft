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
    $('#codigoOpCli').val(codigoAre);
    $('#nombreOpCli').val(nombreAre);
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



