/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.AreaJpaController;
import Controladores.CaracterizarInstructorJpaController;
import Controladores.ClimaJpaController;
import Controladores.InstructorJpaController;
import Controladores.SexoJpaController;
import Entidades.Area;
import Entidades.CaracterizarInstructor;
import Entidades.Clima;
import Entidades.Instructor;
import Entidades.Sexo;
import com.google.gson.Gson;
import groovyjarjarasm.asm.util.Printer;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CaracterizacionServlet", urlPatterns = {"/CaracterizacionServlet"})
public class CaracterizacionServlet extends HttpServlet {

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
        String btn = request.getParameter("accion");

        switch (btn) {
            case "guardar":
                saveCaracterizacion(request, response);
                break;
            case "actualizar":
                editCaracterizacion(request, response);
                break;
            case "eliminar":
                DeleteCaract(request, response);
                break;
            default:
                break;
        }

    }
    // Método para enviar una respuesta JSON de éxito

    public void saveCaracterizacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Instancioamos controladroes*/
        CaracterizarInstructorJpaController caracterizacionController = new CaracterizarInstructorJpaController();
        SexoJpaController sexoController = new SexoJpaController();
        ClimaJpaController climaControlador = new ClimaJpaController();
        InstructorJpaController instructorControlador = new InstructorJpaController();
        AreaJpaController areaController = new AreaJpaController();

        /*Obtencion y hallazgo de datos de formulario*/
        Area areaSeleccionada = areaController.findArea(Integer.parseInt(request.getParameter("area")));
        Sexo sexoSeleccionado = sexoController.findSexo(Integer.parseInt(request.getParameter("sexo")));
        Instructor instructorSeleccionado = instructorControlador.findInstructor(Integer.parseInt(request.getParameter("instructor")));
        Clima climaSeleccionado = climaControlador.findClima(Integer.parseInt(request.getParameter("clima")));
        String DotacionCorrespondiente = request.getParameter("dotacion");
        int year = LocalDate.now().getYear();

        try {
            CaracterizarInstructor nuevaCaracterizacion = new CaracterizarInstructor();
            nuevaCaracterizacion.setAno(year);
            nuevaCaracterizacion.setAreaIdarea(areaSeleccionada);
            nuevaCaracterizacion.setClimaIdclima(climaSeleccionado);
            nuevaCaracterizacion.setInstructorIdinstructor(instructorSeleccionado);
            nuevaCaracterizacion.setSexoIdsexo(sexoSeleccionado);
            nuevaCaracterizacion.setDescripcion(DotacionCorrespondiente);

            caracterizacionController.create(nuevaCaracterizacion);
            enviarRespuestaExito(response, "Caracterizacion guardada exitosamente");
        } catch (Exception e) {
            enviarRespuestaError(response, "Verifique que los campos esten diligenciados");
        }

    }

    public void editCaracterizacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaracterizarInstructorJpaController caracterizacionController = new CaracterizarInstructorJpaController();
        SexoJpaController sexoController = new SexoJpaController();
        ClimaJpaController climaControlador = new ClimaJpaController();
        InstructorJpaController instructorControlador = new InstructorJpaController();
        AreaJpaController areaController = new AreaJpaController();

        /*Obtencion y hallazgo de datos de formulario*/
        Area areaSeleccionada = areaController.findArea(Integer.parseInt(request.getParameter("areaEdit")));
        Sexo sexoSeleccionado = sexoController.findSexo(Integer.parseInt(request.getParameter("sexoEdit")));
        Instructor instructorSeleccionado = instructorControlador.findInstructor(Integer.parseInt(request.getParameter("instructorEdit")));
        Clima climaSeleccionado = climaControlador.findClima(Integer.parseInt(request.getParameter("climaEdit")));
        String DotacionCorrespondiente = request.getParameter("dotacionEdit");

        int idCaracterizacion = Integer.parseInt(request.getParameter("IdEdit"));
        CaracterizarInstructor oldCaract = caracterizacionController.findCaracterizarInstructor(idCaracterizacion);

        boolean cambios = false;
        if (!oldCaract.getAreaIdarea().getIdarea().equals(areaSeleccionada.getIdarea())
                || !oldCaract.getSexoIdsexo().getIdsexo().equals(sexoSeleccionado.getIdsexo())
                || !oldCaract.getInstructorIdinstructor().getIdinstructor().equals(instructorSeleccionado.getIdinstructor())
                || !oldCaract.getClimaIdclima().getIdclima().equals(climaSeleccionado.getIdclima())
                || !oldCaract.getDescripcion().equals(DotacionCorrespondiente)) {
            cambios = true;

        }
        if (cambios) {

            oldCaract.setAno(oldCaract.getAno());
            oldCaract.setAreaIdarea(areaSeleccionada);
            oldCaract.setClimaIdclima(climaSeleccionado);
            oldCaract.setInstructorIdinstructor(instructorSeleccionado);
            oldCaract.setSexoIdsexo(sexoSeleccionado);
            oldCaract.setDescripcion(DotacionCorrespondiente);
            try {
                caracterizacionController.edit(oldCaract);
                enviarRespuestaExito(response, "Caracterizacion Editada");
            } catch (Exception ex) {
                enviarRespuestaError(response, "Verifique que los campos esten diligenciados");
                ex.printStackTrace();
            }
        } else {
            enviarRespuestaError(response, "No existen cambios");
        }

    }

    public void DeleteCaract(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaracterizarInstructorJpaController controlCaract = new CaracterizarInstructorJpaController();

        int idEliminar = Integer.parseInt(request.getParameter("IdEliminar"));
        try {
            controlCaract.destroy(idEliminar);
            enviarRespuestaExito(response, "Caracterizacion eliminada exitosamente");
        } catch (Exception e) {
            enviarRespuestaError(response, "Error al eliminar la caracterizacion");
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
