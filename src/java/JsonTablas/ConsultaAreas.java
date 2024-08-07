/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;

import Controladores.AreaJpaController;
import Controladores.RedJpaController;
import Entidades.Area;
import Entidades.Red;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ConsultaAreas", urlPatterns = {"/ConsultaAreas"})
public class ConsultaAreas extends HttpServlet {

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
        cargarTabla(request, response);
    }

    protected void cargarTabla(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        // Lógica para consultar los datos de los estudiantes
        AreaJpaController areaController = new AreaJpaController();
        List<Area> area = areaController.findAreaEntities();
        RedJpaController redController = new RedJpaController();
        // Crear una lista para almacenar objetos JSON personalizados de estudiantes
        List<JsonObject> redJson = new ArrayList<>();

        for (Area areas : area) {
            // Crear un nuevo objeto JSON personalizado con los datos
            JsonObject objetoFormacionesJson = new JsonObject();
            objetoFormacionesJson.addProperty("codigo", areas.getIdarea());
            objetoFormacionesJson.addProperty("nombre", areas.getNombre());

            // Obtener el objeto de sede del estudiante y agregar sus datos al JSON
            Red sede = redController.findRed(areas.getRedIdred().getIdred());
            if (sede != null) {
                objetoFormacionesJson.addProperty("redlId", sede.getIdred());
                objetoFormacionesJson.addProperty("redNombre", sede.getNombre());
            }
            // Agregar el objeto JSON personalizado a la lista
            redJson.add(objetoFormacionesJson);
        }
        // Convertir la lista de objetos JSON personalizados a una cadena JSON
        String json = new Gson().toJson(redJson);

        // Enviar la respuesta JSON al cliente
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
