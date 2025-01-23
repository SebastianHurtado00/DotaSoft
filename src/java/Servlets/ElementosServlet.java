/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.CoordinadorJpaController;
import Controladores.ElementosJpaController;
import Controladores.ElementosanualJpaController;
import Entidades.Centro;
import Entidades.Coordinador;
import Entidades.Elementos;
import Entidades.Elementosanual;
import Entidades.Usuarios;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ElementosServlet", urlPatterns = {"/ElementosServlet"})
public class ElementosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "guardar":
                    botonGuardar(request, response);
                    break;
                case "actualizar":
                    botonEditar(request, response);
                    break;
                case "eliminar":
                    botonEliminar(request, response);
                    break;
                case "eliminarAll":
                    botonEliminarAll(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoElm"));
        String nombre = request.getParameter("nombreELm");

        ElementosJpaController se = new ElementosJpaController();
        Elementos guardar = new Elementos();

        try {
            // Verificar si el código ya existe en la base de datos
            if (se.findElementos(codigo) != null) {
                // El código ya existe, enviar notificación
                enviarRespuestaError(response, "¡Error! Ya existe un registro con ese Codigo.");
            } else {
                // El código no existe, proceder con la creación de la sede
                guardar.setIdelemento(codigo);
                guardar.setNombre(nombre);
                guardar.setCantidades(0);

                se.create(guardar);
                enviarRespuestaExito(response, "¡Registro guardado exitosamente!");
            }
        } catch (Exception e) {
            // Error al guardar el registro, enviar notificación de error
            enviarRespuestaError(response, "¡Error!");
        }
    }

    protected void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int codigo = Integer.parseInt(request.getParameter("codigoElElm"));
            ElementosJpaController Controller = new ElementosJpaController();
            Controller.destroy(codigo);
            enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoElElm"));
        String nombre = request.getParameter("nombreElElm");

        ElementosJpaController Controller = new ElementosJpaController();
        Elementos Existente = Controller.findElementos(codigo);

        try {
            if (Existente != null) {
                // La sede existe, proceder con la edición
                Existente.setNombre(nombre);
                Controller.edit(Existente);

                enviarRespuestaExito(response, "¡Registro Editado exitosamente!");
            }
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }
    }

    public void botonEliminarAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ElementosJpaController elementosController = new ElementosJpaController();
        ElementosanualJpaController elementosAnualController = new ElementosanualJpaController();
        CoordinadorJpaController coordinadorController = new CoordinadorJpaController();

        HttpSession sessionObtenida = request.getSession();

        // Verificar si la sesión contiene el objeto "coordinador"
        Usuarios useCoordinador = (Usuarios) sessionObtenida.getAttribute("coordinador");
        if (useCoordinador == null) {
            response.sendRedirect("../CerradoSession.jsp");
            return;
        }

        // Obtener el idusuario del objeto Usuarios
        int idUsuario = useCoordinador.getIdusuario();
        System.out.println("ID del coordinador (usuario): " + idUsuario);

        try {
            // Buscar el coordinador en la tabla Coordinador utilizando el idUsuario
            Coordinador coordinador = coordinadorController.findCoordinador(idUsuario);
            if (coordinador == null) {
                response.getWriter().write("No se encontró un coordinador con el ID proporcionado.");
                return;
            }

            // Obtener el nombre completo del coordinador y el centro asociado
            String nombreCoordinador = coordinador.getNombres() + " " + coordinador.getApellidos();
            Centro centro = coordinador.getCentroIdcentro();
            String nombreCentro = (centro != null) ? centro.getNombre() : "No asociado";
            System.out.println(nombreCoordinador + centro + nombreCentro);
            // Operaciones con Elementos y Elementosanual
            List<Elementos> elementos = elementosController.findElementosEntities();

            // Verificar si la lista de elementos está vacía
            if (elementos.isEmpty()) {
                response.getWriter().write("No se encontraron elementos en la base de datos.");
                return;
            }

            // Crear una lista de ElementoDTO para almacenar solo los datos necesarios
            List<Elementos> elementoDTOs = new ArrayList<>();
            for (Elementos elemento : elementos) {
                Elementos dto = new Elementos();
                dto.setIdelemento(elemento.getIdelemento());
                dto.setNombre(elemento.getNombre());
                dto.setCantidades(elemento.getCantidades());
                elementoDTOs.add(dto);
            }

            // Convertir la lista a JSON
            Gson gson = new Gson();
            String elementosJson = gson.toJson(elementoDTOs);

            // Obtener el año actual
            int anioActual = java.time.Year.now().getValue();

            // Crear y guardar el registro en Elementosanual
            Elementosanual elementosAnual = new Elementosanual();
            elementosAnual.setElemento(elementosJson);
            elementosAnual.setNombreCoordinador(nombreCoordinador);
            elementosAnual.setCentro(nombreCentro);
            elementosAnual.setAnio(anioActual);
            elementosAnualController.create(elementosAnual);

            // Establecer cantidades a 0
            for (Elementos elemento : elementos) {
                elemento.setCantidades(0);
               
                elementosController.edit(elemento);
            }

             enviarRespuestaExito(response, "¡Información guardada y cantidades actualizadas a 0!");

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespuestaError(response, "¡Error al guardar información o actualizar cantidades!");

        }
    }

    // Método para enviar una respuesta JSON de éxito
    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "exito");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // Método para enviar una respuesta JSON de error
    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "error");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
