/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;


import Controladores.CaracterizarInstructorJpaController;
import Controladores.DotacionJpaController;
import Entidades.CaracterizarInstructor;
import Entidades.Dotacion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**

 */
@WebServlet(name = "ConsultaCaracterizacion", urlPatterns = {"/ConsultaCaracterizacion"})
public class ConsultaCaracterizacion extends HttpServlet {

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
 Sebastian
        cargarTabla(request, response);
    }

    public void cargarTabla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        CaracterizarInstructorJpaController CaracterizacionController = new CaracterizarInstructorJpaController();
        List<CaracterizarInstructor> CarctList = CaracterizacionController.findCaracterizarInstructorEntities();

        // Crear una lista para almacenar los mapas de las dotaciones
        List<Map<String, String>> dotacionJsonList = new ArrayList<>();

        for (CaracterizarInstructor caracterizacion : CarctList) {
            // Crear un nuevo mapa para almacenar los datos de la dotación
            Map<String, String> jsonDotacion = new HashMap<>();
            /*Id de la caracterizacion*/
            jsonDotacion.put("idCaracterizacion", caracterizacion.getIdCaracterizarInstructor().toString());

            // Id y nombre del instructor
            jsonDotacion.put("idInstructor", caracterizacion.getInstructorIdinstructor().getIdinstructor().toString());
            jsonDotacion.put("InstructorNombreApellido", caracterizacion.getInstructorIdinstructor().getNombres() + " " + caracterizacion.getInstructorIdinstructor().getApellidos());

            // Año en que se realiza la caracterizacion
            jsonDotacion.put("anho", caracterizacion.getSexoIdsexo().getIdsexo().toString());

            // Agregar ID y nombre de Clima
            jsonDotacion.put("climaId", caracterizacion.getClimaIdclima().getIdclima().toString());
            jsonDotacion.put("climaNombre", caracterizacion.getClimaIdclima().getNombre());

            /* Red */
            jsonDotacion.put("redId", caracterizacion.getAreaIdarea().getRedIdred().getIdred().toString());
            jsonDotacion.put("redNombre", caracterizacion.getAreaIdarea().getRedIdred().getNombre().toString());

            /* Area */
            jsonDotacion.put("areaId", caracterizacion.getAreaIdarea().getIdarea().toString());
            jsonDotacion.put("areaNombre", caracterizacion.getAreaIdarea().getNombre());

            /* Sexo */
            jsonDotacion.put("SexoId", String.valueOf(caracterizacion.getSexoIdsexo().getIdsexo()));
            jsonDotacion.put("SexoNombre", String.valueOf(caracterizacion.getSexoIdsexo().getNombre()));

            /* Elementos asigandos */
            jsonDotacion.put("elementosAsignados", caracterizacion.getDescripcion());

            // Agregar el mapa a la lista de dotaciones JSON
            dotacionJsonList.add(jsonDotacion);
        }

        // Convertir la lista de mapas a una cadena JSON
        String json = new Gson().toJson(dotacionJsonList);

        // Imprimir JSON generado para depuración
        System.out.println("JSON generado: " + json);

        // Enviar la respuesta JSON al cliente
        response.getWriter().write(json);

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConsultaCaracterizacion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConsultaCaracterizacion at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
 master
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
