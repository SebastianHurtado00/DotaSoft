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
import java.util.List;
import java.util.ArrayList;
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
    // Establece el tipo de contenido de la respuesta como JSON y codificación UTF-8
    response.setContentType("application/json;charset=UTF-8");

    // Obtiene el parámetro 'redId' de la solicitud HTTP
    String redIdParam = request.getParameter("redId");
    List<Area> areaList;

    // Crea instancias de los controladores JPA para las entidades Area y Red
    AreaJpaController areaController = new AreaJpaController();
    RedJpaController redController = new RedJpaController();
    
    // Verifica si el parámetro 'redId' está presente y no está vacío
    if (redIdParam != null && !redIdParam.isEmpty()) {
        // Convierte el parámetro 'redId' a un entero
        int redId = Integer.parseInt(redIdParam);
        // Obtiene la lista de áreas filtradas por 'redId' usando el controlador JPA
        areaList = areaController.findAreasByRedId(redId);
    } else {
        // Si 'redId' no está presente, obtiene todas las áreas
        areaList = areaController.findAreaEntities();
    }

    // Crea una lista para almacenar los datos de áreas en formato JSON
    List<Map<String, String>> areaJsonList = new ArrayList<>();
    
    // Itera sobre cada área obtenida
    for (Area area : areaList) {
        // Crea un mapa para almacenar los datos de cada área
        Map<String, String> jsonArea = new HashMap<>();
        // Agrega el código del área al mapa
        jsonArea.put("codigo", area.getIdarea().toString());
        // Agrega el nombre del área al mapa
        jsonArea.put("nombre", area.getNombre());

        // Obtiene la red asociada al área usando el controlador JPA
        Red red = redController.findRed(area.getRedIdred().getIdred());
        if (red != null) {
            // Agrega el ID de la red al mapa
            jsonArea.put("redId", red.getIdred().toString());
            // Agrega el nombre de la red al mapa
            jsonArea.put("redNombre", red.getNombre());
        }

        // Agrega el mapa con los datos del área a la lista de áreas JSON
        areaJsonList.add(jsonArea);
    }

    // Convierte la lista de mapas a una cadena JSON
    String json = new Gson().toJson(areaJsonList);
    // Escribe la cadena JSON en la respuesta HTTP
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
