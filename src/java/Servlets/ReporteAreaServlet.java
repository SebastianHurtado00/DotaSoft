package Servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Peralta
 */
@WebServlet(urlPatterns = {"/ReporteAreaServlet"})
public class ReporteAreaServlet extends HttpServlet {

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
       // Obtener el parámetro "AreaReport"
        String areaIdParam = request.getParameter("AreaReport");

        if (areaIdParam != null && !areaIdParam.isEmpty()) {
            try {
                // Convertir el parámetro a un entero
                int areaId = Integer.parseInt(areaIdParam);

                // Establecer la conexión a la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/dotaciones_sena2", "root", "27478426*cP");

                // Parámetros para el informe Jasper
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("num", areaId);  // Parámetro para el reporte Jasper

                // Ruta del archivo .jasper
                File reporte = new File(getServletContext().getRealPath("Reports/XArea.jasper"));

                // Generar el reporte en PDF
                byte[] bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parametros, conexion);

                // Enviar el reporte PDF como respuesta
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        } else {
            // Si el parámetro "AreaReport" no está presente o es inválido
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se ha seleccionado un área válida.");
        }
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
