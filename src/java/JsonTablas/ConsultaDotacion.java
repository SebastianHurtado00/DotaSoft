/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;

import Controladores.DotacionJpaController;
import Entidades.Dotacion;
import Entidades.Sexo;
import com.google.gson.Gson;
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
@WebServlet(name = "ConsultaDotacion", urlPatterns = {"/ConsultaDotacion"})
public class ConsultaDotacion extends HttpServlet {

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
        cargarTabla(request, response);

    }

   private void cargarTabla(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;charset=UTF-8");

    DotacionJpaController dotacionController = new DotacionJpaController();
    List<Dotacion> dotaList = dotacionController.findDotacionEntities();

    // Crear una lista para almacenar los mapas de las dotaciones
    List<Map<String, String>> dotacionJsonList = new ArrayList<>();

    for (Dotacion dotacion : dotaList) {
        // Crear un nuevo mapa para almacenar los datos de la dotación
        Map<String, String> jsonDotacion = new HashMap<>();
        jsonDotacion.put("idDotacion", dotacion.getIddotacion().toString());
        
        // Agregar ID y nombre de Elementos
        jsonDotacion.put("elementoId", dotacion.getElementosIdelemento().getIdelemento().toString());
        jsonDotacion.put("elementoNombre", dotacion.getElementosIdelemento().getNombre());
        
        // Agregar ID y nombre de Sexo
        jsonDotacion.put("sexoId", dotacion.getSexoIdsexo().getIdsexo().toString());
        jsonDotacion.put("sexoNombre", dotacion.getSexoIdsexo().getNombre());
        
        // Agregar ID y nombre de Clima
        jsonDotacion.put("climaId", dotacion.getClimaIdclima().getIdclima().toString());
        jsonDotacion.put("climaNombre", dotacion.getClimaIdclima().getNombre());
        
        // Agregar ID y nombre de Área
        jsonDotacion.put("areaId", dotacion.getAreaIdarea().getIdarea().toString());
        jsonDotacion.put("areaNombre", dotacion.getAreaIdarea().getNombre());
        
        // Agregar la cantidad
        jsonDotacion.put("cantidad", String.valueOf(dotacion.getCantidad()));

        // Agregar el mapa a la lista de dotaciones JSON
        dotacionJsonList.add(jsonDotacion);
    }

    // Convertir la lista de mapas a una cadena JSON
    String json = new Gson().toJson(dotacionJsonList);

    // Imprimir JSON generado para depuración
    System.out.println("JSON generado: " + json);

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
