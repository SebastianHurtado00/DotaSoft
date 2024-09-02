<!DOCTYPE html>
<html>
<head>
    <title>Prueba Filtro</title>
    <!-- Incluir la librería de jQuery desde un CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

    <table border="1">
        <thead>
            <tr>
                <th>ID Dotación</th>
                <th>ID Elemento</th>
                <th>Nombre Elemento</th>
                <th>ID Sexo</th>
                <th>Nombre Sexo</th>
                <th>ID Clima</th>
                <th>Nombre Clima</th>
                <th>ID Área</th>
                <th>Nombre Área</th>
                <th>Cantidad</th>
            </tr>
        </thead>
        <tbody id="resultados">
            <!-- Aquí se agregarán las filas de la tabla con los resultados -->
        </tbody>
    </table>

    <!-- Código JavaScript -->
    <script>
        $(document).ready(function () {
            // Función para realizar la consulta AJAX y procesar la respuesta
            function consultarDotacion() {
                // Obtener valores de los filtros de los elementos de entrada (input, select, etc.)
                const elementoId = 11510027;
                const sexoId = 1010;
                const climaId = 1010;
                const areaId = 2000;

                // Realizar la petición AJAX
                $.ajax({
                    url: '/FiltroJsonDotacion',
                    method: 'GET',
                    data: {
                     
                        sexoId: sexoId,
                        climaId: climaId,
                        areaId: areaId
                    },
                    dataType: 'json',
                    success: function (data) {
                        // Limpia el contenedor donde se mostrarán los resultados
                        $('#resultados').empty();
                        // Recorrer la respuesta y agregar los datos al DOM
                        data.forEach(function (dotacion) {
                            const row = `
                                <tr>
                                    <td>${dotacion.idDotacion}</td>
                                    <td>${dotacion.elementoId}</td>
                                    <td>${dotacion.elementoNombre}</td>
                                    <td>${dotacion.sexoId}</td>
                                    <td>${dotacion.sexoNombre}</td>
                                    <td>${dotacion.climaId}</td>
                                    <td>${dotacion.climaNombre}</td>
                                    <td>${dotacion.areaId}</td>
                                    <td>${dotacion.areaNombre}</td>
                                    <td>${dotacion.cantidad}</td>
                                </tr>
                            `;
                            $('#resultados').append(row);
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Error en la consulta:', error);
                    }
                });
            }

            // Llamar a la función para realizar la consulta al cargar la página
            consultarDotacion();
        });
    </script>

</body>
</html>
