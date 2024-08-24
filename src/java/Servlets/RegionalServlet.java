/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.RegionalJpaController;
import Entidades.Regional;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "RegionalServlet", urlPatterns = {"/RegionalServlet"})
public class RegionalServlet extends HttpServlet {

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
                default:
                    // Acción no válida
                    break;
            }
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoReg"));
        String nombre = request.getParameter("nombreReg");

        RegionalJpaController se = new RegionalJpaController();
        Regional guardar = new Regional();

        try {
            // Verificar si el código ya existe en la base de datos
            if (se.findRegional(codigo) != null) {
                // El código ya existe, enviar notificación
                enviarRespuestaError(response, "¡Error! Ya existe un registro con ese Codigo.");
            } else {
                // El código no existe, proceder con la creación de la sede
                guardar.setIdregional(codigo);
                guardar.setNombre(nombre);

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
            int codigo = Integer.parseInt(request.getParameter("codigoElReg"));
            RegionalJpaController Controller = new RegionalJpaController();
            Controller.destroy(codigo);
            enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Controladores.exceptions.IllegalOrphanException e) {
            // Captura específica de la excepción IllegalOrphanException
            enviarRespuestaError(response, "¡No se puede eliminar este registro porque tiene dependencias asociadas!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");

        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoElReg"));
        String nombre = request.getParameter("nombreElReg");

        RegionalJpaController Controller = new RegionalJpaController();
        Regional Existente = Controller.findRegional(codigo);

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
