/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;

import Controladores.CentroJpaController;
import Controladores.RegionalJpaController;
import Entidades.Centro;
import Entidades.Regional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
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
@WebServlet(name = "ConsultaCentro", urlPatterns = {"/ConsultaCentro"})
public class ConsultaCentro extends HttpServlet {

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
        CentroJpaController centroController = new CentroJpaController();
        List<Centro> centro = centroController.findCentroEntities();
        RegionalJpaController redController = new RegionalJpaController();
        // Crear una lista para almacenar objetos JSON personalizados de estudiantes
        List<JsonObject> redJson = new ArrayList<>();

        for (Centro centros : centro) {
            // Crear un nuevo objeto JSON personalizado con los datos
            JsonObject objetoFormacionesJson = new JsonObject();
            objetoFormacionesJson.addProperty("codigo", centros.getIdcentro());
            objetoFormacionesJson.addProperty("nombre", centros.getNombre());

            // Obtener el objeto de sede del estudiante y agregar sus datos al JSON
            Regional reg = redController.findRegional(centros.getRegionalIdregional().getIdregional());
            if (reg != null) {
                objetoFormacionesJson.addProperty("reglId", reg.getIdregional());
                objetoFormacionesJson.addProperty("regNombre", reg.getNombre());
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
