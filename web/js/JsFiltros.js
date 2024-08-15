// FILTRO AREA Y RED GUARDADO INICIO
$(document).ready(function () {
    // Cargar las Redes al cargar la página
    $.ajax({
        url: '../ConsultaRed', // URL del endpoint para obtener las redes
        method: 'GET', // Método de la solicitud HTTP
        success: function (response) {
            console.log("Respuesta de Red:", response); // Muestra la respuesta en la consola para depuración
            var listaRed = $('#ListaRedGuardado'); // Selecciona el elemento <select> para las redes
            // Itera sobre cada red en la respuesta
            $.each(response, function (index, red) {
                // Añade una opción al <select> para cada red
                listaRed.append('<option value="' + red.codigo + '">' + red.nombre + '</option>');
            });
        },
        error: function (xhr, status, error) {
            console.error("Error al cargar Redes:", status, error); // Muestra el error en la consola si falla la solicitud
        }
    });

    // Cargar las Áreas en función de la Red seleccionada
    $('#ListaRedGuardado').on('change', function () {
        var redId = $(this).val(); // Obtiene el ID de la red seleccionada
        console.log("Red seleccionada:", redId); // Muestra el ID de la red seleccionada en la consola para depuración
        $.ajax({
            url: '../ConsultaAreas', // URL del endpoint para obtener las áreas
            method: 'GET', // Método de la solicitud HTTP
            data: { redId: redId }, // Envía el ID de la red como parámetro en la solicitud
            success: function (response) {
                console.log("Respuesta de Área:", response); // Muestra la respuesta en la consola para depuración
                var listaArea = $('#AreaListaForGdDo'); // Selecciona el elemento <select> para las áreas
                listaArea.empty(); // Limpia las opciones previas del <select>
                listaArea.append('<option value="" disabled selected hidden>-- Elija --</option>'); // Añade una opción predeterminada
                // Itera sobre cada área en la respuesta
                $.each(response, function (index, area) {
                    // Añade una opción al <select> para cada área
                    listaArea.append('<option value="' + area.codigo + '">' + area.nombre + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar Áreas:", status, error); // Muestra el error en la consola si falla la solicitud
            }
        });
    });
});
// FILTRO AREA Y RED GUARDADO FINAL
// FILTRO AREA Y RED OPCIONES INICIO
$(document).ready(function () {
        // Cargar las Redes cuando se abre el ModalDotacionOpciones
        $('#ModalDotacionOpciones').on('shown.bs.modal', function () {
            $.ajax({
                url: '../ConsultaRed',
                method: 'GET',
                success: function (response) {
                    var listaRed = $('#ListaRedDotacion');
                    listaRed.empty(); // Limpiar las opciones previas
                    listaRed.append('<option value="" disabled selected hidden>-- Elija --</option>');
                    $.each(response, function (index, red) {
                        listaRed.append('<option value="' + red.codigo + '">' + red.nombre + '</option>');
                    });
                },
                error: function (xhr, status, error) {
                    console.error("Error al cargar Redes:", status, error);
                }
            });
        });

        // Cargar las Áreas en función de la Red seleccionada en el ModalDotacionOpciones
        $('#ListaRedDotacion').on('change', function () {
            var redId = $(this).val();
            $.ajax({
                url: '../ConsultaAreas',
                method: 'GET',
                data: { redId: redId },
                success: function (response) {
                    var listaArea = $('#AreaListaForGdDotacion');
                    listaArea.empty(); // Limpiar las opciones previas
                    listaArea.append('<option value="" disabled selected hidden>-- Elija --</option>');
                    $.each(response, function (index, area) {
                        listaArea.append('<option value="' + area.codigo + '">' + area.nombre + '</option>');
                    });
                },
                error: function (xhr, status, error) {
                    console.error("Error al cargar Áreas:", status, error);
                }
            });
        });
    });
    // FILTRO AREA Y RED OPCIONES FINAL