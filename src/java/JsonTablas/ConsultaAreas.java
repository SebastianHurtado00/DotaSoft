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
import java.util.HashMap;
import java.util.List;
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

// Lógica para consultar los datos de las áreas
        AreaJpaController areaController = new AreaJpaController();
        List<Area> areaList = areaController.findAreaEntities();
        RedJpaController redController = new RedJpaController();

// Crear una lista para almacenar los mapas de las áreas
        List<Map<String, String>> areaJsonList = new ArrayList<>();

        for (Area area : areaList) {
            // Crear un nuevo mapa para almacenar los datos del área
            Map<String, String> jsonArea = new HashMap<>();
            jsonArea.put("codigo", area.getIdarea().toString());
            jsonArea.put("nombre", area.getNombre());

            // Obtener la red asociada al área y agregar sus datos al mapa si existe
            Red red = redController.findRed(area.getRedIdred().getIdred());
            if (red != null) {
                jsonArea.put("redId", red.getIdred().toString());
                jsonArea.put("redNombre", red.getNombre());
            }

            // Agregar el mapa a la lista de áreas JSON
            areaJsonList.add(jsonArea);
        }

        // Convertir la lista de mapas a una cadena JSON
        String json = new Gson().toJson(areaJsonList);

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
