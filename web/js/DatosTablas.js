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