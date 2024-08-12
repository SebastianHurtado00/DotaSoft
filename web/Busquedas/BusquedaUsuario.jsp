<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="Entidades.Instructor"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="Entidades.Coordinador"%>
<%@page import="Controladores.InstructorJpaController"%>
<%@page import="Controladores.CoordinadorJpaController"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>

<%
    response.setContentType("application/json;charset=UTF-8");

    // Obtener parámetros de la solicitud
    int cedula = Integer.parseInt(request.getParameter("Cedula"));
    int rol = Integer.parseInt(request.getParameter("Rol"));

    // Inicializar los controladores
    CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
    InstructorJpaController controlInstructor = new InstructorJpaController();

    // Crear un mapa para almacenar los datos a devolver
    Map<String, Object> datos = new HashMap<>();

    try {
        // Obtener datos según el rol
        switch (rol) {
            case 1: // Coordinador
                Coordinador coordinador = controlCoordinador.findCoordinador(cedula);
                if (coordinador != null) {
                    datos.put("Regional", coordinador.getCentroIdcentro().getRegionalIdregional().getIdregional());
                    datos.put("Centro", coordinador.getCentroIdcentro().getIdcentro()); // Asegúrate de que solo se obtengan datos simples
                    datos.put("Correo", coordinador.getCorreo());
                } else {
                    datos.put("error", "Coordinador no encontrado");
                }
                break;

            case 2: // Instructor
                Instructor instructor = controlInstructor.findInstructor(cedula);
                if (instructor != null) {
                    datos.put("Regional", instructor.getCoordinadorIdcoordinador().getCentroIdcentro().getRegionalIdregional().getIdregional());
                    datos.put("Centro", instructor.getCoordinadorIdcoordinador().getCentroIdcentro().getIdcentro()); // Asegúrate de que solo se obtengan datos simples
                    datos.put("Correo", instructor.getCorreo());
                    datos.put("Telefono", instructor.getTelefono());
                    datos.put("Coordinador", instructor.getCoordinadorIdcoordinador().getIdcoordinador()); // Asegúrate de que solo se obtengan datos simples
                } else {
                    datos.put("error", "Instructor no encontrado");
                }
                break;

            default:
                datos.put("error", "Rol no reconocido");
                break;
        }

        // Convertir el mapa de datos a JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(datos);
        response.getWriter().write(json);

    } catch (Exception e) {
        // Manejar cualquier excepción y devolver un mensaje de error en formato JSON
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error en el procesamiento: " + e.getMessage());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        response.getWriter().write(gson.toJson(error));
    }
%>
