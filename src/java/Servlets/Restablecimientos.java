/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "Restablecimientos", urlPatterns = {"/Restablecimientos"})
public class Restablecimientos extends HttpServlet {

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
        String btn = request.getParameter("BtnRestablecer");
        switch (btn) {
            case "RestablcerPasswordHome":
                restablecimientoHome(request, response);
                break;
            case "RestablercerAdmin":
                restablecimientoAdmin(request, response);
                break;
            case "RestablcerPasswordIndex":
                buscarCorreo(request, response);
                break;
            case "RestablecerNewPage":
                restablecerPaswordToken(request, response);
                break;
            default:
                break;
        }

    }

    private void restablecimientoHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuariosJpaController controlUsuarios = new UsuariosJpaController();
        Usuarios usuarioEncontrado = new Usuarios();

        /*Encontramos inputs*/
        int CedulaUser = Integer.parseInt(request.getParameter("numeroDocumentoCambio"));
        String passwordNueva = request.getParameter("PasswordNueva");
        String passwordConfirmar = request.getParameter("ConfirmacionPassword");

        usuarioEncontrado = controlUsuarios.findUsuarios(CedulaUser);
        String mensajeUrl;

        if (passwordNueva.equals(passwordConfirmar)) {
            try {
                usuarioEncontrado.setClave(usuarioEncontrado.EncryptarClave(passwordNueva));
                controlUsuarios.edit(usuarioEncontrado);
                mensajeUrl = "CambioClave";
                response.sendRedirect("index.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(Restablecimientos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mensajeUrl = "confirmacionFallida";
            response.sendRedirect("Home/Home.jsp?respuesta=" + mensajeUrl);
        }
    }

    private void restablecimientoAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UsuariosJpaController usuarioController = new UsuariosJpaController();
        Usuarios usuario = new Usuarios();

        String cedulaStr = request.getParameter("CC");
        String cedula = request.getParameter("cedula");
        String MensajeUrl;
        if (cedula == "" || cedulaStr == "") {
            MensajeUrl = "Vacios";
            response.sendRedirect("Views/Restablecimientos.jsp?respuesta=" + MensajeUrl);
        } else {
            int Cc = Integer.parseInt(cedulaStr);
            usuario = usuarioController.findUsuarios(Cc);
            if (usuario == null || usuario.getRol() == 2) {
                MensajeUrl = "UsuarioNoValido";
                response.sendRedirect("Views/Restablecimientos.jsp?respuesta=" + MensajeUrl);
            } else {
                //Extraemos CC
                String cedulaEncriptar = usuario.EncryptarClave(cedulaStr);
                //Asignamos contraseña
                usuario.setClave(cedulaEncriptar);
                try {
                    usuarioController.edit(usuario);
                    MensajeUrl = "restablecimientoExitoso";
                    response.sendRedirect("ViewsAdministrador/Restablecimientos.jsp?respuesta=" + MensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(Restablecimientos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void restablecerPaswordToken(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instanciamos controladores necesarios
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios usuarioEntrante = new Usuarios();

        //Obtenesmos datos y encontramos al usuario
        int numeroDocumento = Integer.parseInt(request.getParameter("numeroDocumentoCambio"));
        String contraseñaNueva = request.getParameter("PasswordNueva");
        String contraseñaConfirmar = request.getParameter("ConfirmacionPassword");

        usuarioEntrante = controlUsuario.findUsuarios(numeroDocumento);
        String MensajeUrl;

        if (contraseñaConfirmar.equals(contraseñaNueva)) {
            try {
                String contrasenhaEncriptada = usuarioEntrante.EncryptarClave(contraseñaConfirmar);
                usuarioEntrante.setClave(contrasenhaEncriptada);
                controlUsuario.edit(usuarioEntrante);
                MensajeUrl = "ContrasehaModificada";
                response.sendRedirect("index.jsp?respuesta=" + MensajeUrl);
            } catch (Exception e) {
            }

        } else {
            //las contraseñas no son iguales

            MensajeUrl = "ContrasenhaNoiguales";
            String urlDeRetorno = request.getHeader("referer");
            // Redirigimos a la página JSP que llamó al servlet

            // Verifica si la URL ya contiene un parámetro de respuesta
            if (urlDeRetorno.contains("respuesta=")) {
                // Elimina el parámetro de respuesta existente de la URL
                urlDeRetorno = urlDeRetorno.replaceAll("[?&]respuesta=.*?(?:&|$)", "");
            }

            // Ahora agrega el nuevo parámetro de respuesta
            if (urlDeRetorno.contains("?")) {
                // La URL ya tiene una cadena de consulta, agrega el parámetro adecuadamente
                response.sendRedirect(urlDeRetorno + "&respuesta=" + MensajeUrl);
            } else {
                // La URL no tiene una cadena de consulta, agrega el parámetro con "?"
                response.sendRedirect(urlDeRetorno + "?respuesta=" + MensajeUrl);
            }
        }
    }

    private void buscarCorreo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Aquí creas un nuevo objeto Usuarios para enviarlo por correo
        Usuarios usuario = new Usuarios();

        // Supongamos que obtienes la información necesaria para el usuario de alguna manera, por ejemplo, de los parámetros de la solicitud HTTP
        usuario.setIdusuario(Integer.parseInt(request.getParameter("numeroDocumentoCambio")));

        // Aquí configuras los demás atributos del usuario según lo que necesites
        int cedula = usuario.getIdusuario();

        // Crear instancias de los controladores JPA
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        boolean correoEnviado = false; // Variable para verificar si se envió el correo

        try {
            // Buscar al Usuario por cédula en la base de datos
            Usuarios Usuarios = controlUsuario.findUsuarios(cedula);

            // Verificar si se encontró el estudiante y obtener su correo electrónico
            if (Usuarios != null) {
                String correo = Usuarios.getEmail();
                // Ahora puedes enviar el correo electrónico al correo asociado
                enviarCorreo(correo, Usuarios, response, request); // Pasar el usuario como parámetro
                correoEnviado = true; // Actualizar la bandera
            }
            // Después de encontrar al usuario
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);

            if (!correoEnviado) {
                // Si no se envió el correo, significa que no se encontró ninguna cédula
                String mensaje = "UserNoExistente";
                response.sendRedirect("index.jsp?respuesta=" + mensaje);
            }
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir durante la búsqueda
            ex.printStackTrace();
            //response.sendRedirect("error.jsp");
        }
    }

    private void enviarCorreo(String correoDestino, Usuarios usuario, HttpServletResponse response,
            HttpServletRequest request) throws IOException {

        String correoOrigen = "pruebasdevssc@gmail.com";
        String password = "inob bnum bsms phmi";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587; // Puerto para TLS

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Usar TLS
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.debug", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoOrigen, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoOrigen));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject("Recuperación de contraseña");

            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now();
            long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            int validityMinutes = 5;
            long expirationMillis = nowMillis + (validityMinutes * 60 * 1000);

            String url = "http://localhost:8080/DotaSoft/restablecerPassword.jsp?token=" + token
                    + "&cedula=" + usuario.getIdusuario() + "&expiration=" + expirationMillis;

            String mensaje = "Hola " + usuario.getNombreCompleto() + " " + usuario.getApelliodo() + ""
                    + "<br>Hemos recibido una solicitud para restablecer tu contraseña."
                    + "Si no solicitaste esto, puedes ignorar este correo.<br>"
                    + "Para cambiar tu contraseña, haz clic en el siguiente enlace:<br>"
                    + "<a href=\"" + url + "\" rel=\"noopener\">Cambiar contraseña</a>"
                    + " Si tienes alguna pregunta, contáctanos. (Tendrás 5 minutos para realizar el cambio de la contraseña)<br><br>"
                    + "<div style='clear:both;font-family:monospace; margin-top:50px'>"
                    + "<img alt='Logo SENA' src='https://www.sena.edu.co/es-co/Noticias/PublishingImages/mail/Logo-Firma-Email-SENAgray.png' style='float:left;margin-right:10px;width:100px;height:auto;margin-top:22px'>"
                    + "<div style='border-right:1px solid #000;height:100px;float:left;margin-right:10px;'></div>"
                    + "<br><br>Enviado por: <br>"
                    + "Sistema automatico de recuperacion de contraseñas<br>"
                    + "<p style='margin:0px;padding:0px;color:rgb(118,119,124);letter-spacing:1px;font-family:Work Sans;font-size:12px'>"
                    + "Centro De La Innovación, La Tecnología Y Los Servicios</p>"
                    + "</div>";

            message.setContent(mensaje, "text/html; charset=UTF-8");
            Transport.send(message);

            String mensajeRedireccion = "correoEnviado";
            response.sendRedirect("index.jsp?respuesta=" + mensajeRedireccion);

        } catch (MessagingException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

        }
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
