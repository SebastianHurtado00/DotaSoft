/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;

import Controladores.DotacionJpaController;
import Entidades.Dotacion;
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
 * @author ASUS
 */
@WebServlet(name = "FiltroJsonDotacion", urlPatterns = {"/FiltroJsonDotacion"})
public class FiltroJsonDotacion extends HttpServlet {

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
        cargarJsonFiltrado(request, response);
    }

    private void cargarJsonFiltrado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Obtener los parámetros de filtrado de la solicitud
        String filtroSexoId = request.getParameter("sexoId");
        String filtroClimaId = request.getParameter("climaId");
        String filtroAreaId = request.getParameter("areaId");

        DotacionJpaController dotacionController = new DotacionJpaController();
        List<Dotacion> dotaList = dotacionController.findDotacionEntities();

        // Crear una lista para almacenar los mapas de las dotaciones filtradas
        List<Map<String, String>> dotacionJsonList = new ArrayList<>();

        for (Dotacion dotacion : dotaList) {
            // Aplicar los filtros
       
            boolean matchesSexoId = (filtroSexoId == null || filtroSexoId.equals(dotacion.getSexoIdsexo().getIdsexo().toString()));
            boolean matchesClimaId = (filtroClimaId == null || filtroClimaId.equals(dotacion.getClimaIdclima().getIdclima().toString()));
            boolean matchesAreaId = (filtroAreaId == null || filtroAreaId.equals(dotacion.getAreaIdarea().getIdarea().toString()));

            if (matchesSexoId && matchesClimaId && matchesAreaId) {
                Map<String, String> jsonDotacion = new HashMap<>();
                jsonDotacion.put("idDotacion", dotacion.getIddotacion().toString());
                jsonDotacion.put("elementoId", dotacion.getElementosIdelemento().getIdelemento().toString());
                jsonDotacion.put("elementoNombre", dotacion.getElementosIdelemento().getNombre());
                jsonDotacion.put("sexoId", dotacion.getSexoIdsexo().getIdsexo().toString());
                jsonDotacion.put("sexoNombre", dotacion.getSexoIdsexo().getNombre());
                jsonDotacion.put("climaId", dotacion.getClimaIdclima().getIdclima().toString());
                jsonDotacion.put("climaNombre", dotacion.getClimaIdclima().getNombre());
                jsonDotacion.put("areaId", dotacion.getAreaIdarea().getIdarea().toString());
                jsonDotacion.put("areaNombre", dotacion.getAreaIdarea().getNombre());
                jsonDotacion.put("cantidad", String.valueOf(dotacion.getCantidad()));
                dotacionJsonList.add(jsonDotacion);
            }
        }

        String json = new Gson().toJson(dotacionJsonList);
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
