/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function roles() {
    let select = document.getElementById("Roles");
    let selectedValue = select.options[select.selectedIndex].value;
    let cajaCentro = document.getElementById("Centro");
    let cajaRegional = document.getElementById("Regional");
    let cajaCoordinador = document.getElementById("Coordinador");
    let cajaEmail = document.getElementById("Email");
    let cajaTelefono = document.getElementById("Telefono");

    if (selectedValue == 1) {
        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";
        cajaEmail.style.display = "block";
        cajaCoordinador.style.display = "none";
        cajaTelefono.style.display = "none";

        cajaEmail.classList.remove("col-md-6");
        cajaEmail.classList.add("col-md-12");

        // Hacer que Centro y Regional sean obligatorios
        cajaCentro.querySelector('select').required = true;
        cajaRegional.querySelector('select').required = true;

        // Hacer que Email sea obligatorio y Teléfono no
        cajaEmail.querySelector('input').required = true;
        cajaTelefono.querySelector('input').required = false;

        // Quitar el requisito de Coordinador
        cajaCoordinador.querySelector('select').required = false;

    } else if (selectedValue == 2) {
        cajaCoordinador.style.display = "block";
        cajaEmail.style.display = "block";
        cajaTelefono.style.display = "block";

        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";

        cajaEmail.classList.remove("col-md-12");
        cajaEmail.classList.add("col-md-6");

        // Hacer que Coordinador, Email y Teléfono sean obligatorios
        cajaCoordinador.querySelector('select').required = true;
        cajaEmail.querySelector('input').required = true;
        cajaTelefono.querySelector('input').required = true;

        // Quitar el requisito de Centro y Regional
        cajaCentro.querySelector('select').required = false;
        cajaRegional.querySelector('select').required = false;

    } else {
        cajaCoordinador.style.display = "none";
        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";
        cajaEmail.style.display = "none";
        cajaTelefono.style.display = "none";

        // Quitar el requisito de todos los campos
        cajaCentro.querySelector('select').required = false;
        cajaRegional.querySelector('select').required = false;
        cajaCoordinador.querySelector('select').required = false;
        cajaEmail.querySelector('input').required = false;
        cajaTelefono.querySelector('input').required = false;
    }
}