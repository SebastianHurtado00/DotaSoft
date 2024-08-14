$(document).ready(function () {
    // Inicializa Select2
    $('#AreaListaForGdDo').select2({
        placeholder: "-- Elija --",
        dropdownParent: $('#ModalDotacion'),
        width: 'resolve'
    });
    
      $('#ElementoListaForGdDo').select2({
        placeholder: "-- Elija --",
        dropdownParent: $('#ModalDotacion'),
        width: 'resolve'
    });

    $('#AreaListaForGdDotacion').select2({
        dropdownParent: $('#ModalDotacionOpciones'),
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
