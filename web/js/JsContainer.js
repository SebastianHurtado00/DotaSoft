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
   
    let cajaTelefono = document.getElementById("Telefono");

    if (selectedValue == 1) {
        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";
        cajaEmail.style.display = "block";
        cajaCoordinador.style.display = "none";
        cajaTelefono.style.display = "none";

       

        // Hacer que Centro y Regional sean obligatorios
        cajaCentro.querySelector('select').required = true;
        cajaRegional.querySelector('select').required = true;
    
        cajaTelefono.querySelector('input').required = false;

        // Quitar el requisito de Coordinador
        cajaCoordinador.querySelector('select').required = false;

    } else if (selectedValue == 2) {
        cajaCoordinador.style.display = "block";
      
        cajaTelefono.style.display = "block";

        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";


        // Hacer que Coordinador, Email y Teléfono sean obligatorios
        cajaCoordinador.querySelector('select').required = true;
       
        cajaTelefono.querySelector('input').required = true;
        cajaCentro.querySelector('select').required = true;
        cajaRegional.querySelector('select').required = true;

    } else {
        cajaCoordinador.style.display = "none";
        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";
        
        cajaTelefono.style.display = "none";

        // Quitar el requisito de todos los campos
        cajaCentro.querySelector('select').required = false;
        cajaRegional.querySelector('select').required = false;
        cajaCoordinador.querySelector('select').required = false;
       
        cajaTelefono.querySelector('input').required = false;
    }
}

function rolesOpciones() {
    let select = document.getElementById("Rolesop");
    let selectedValue = select.options[select.selectedIndex].value;
    let cajaCentro = document.getElementById("CentroOpciones");
    let cajaRegional = document.getElementById("RegionalOpciones");
    let cajaCoordinador = document.getElementById("CoordinadorOpciones");
   
    let cajaTelefono = document.getElementById("TelefonoOpciones");

    if (selectedValue == 1) {
        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";
     
        cajaCoordinador.style.display = "none";
        cajaTelefono.style.display = "none";

        // Hacer que Centro y Regional sean obligatorios
        cajaCentro.querySelector('select').required = true;
        cajaRegional.querySelector('select').required = true;

        // Hacer que Email sea obligatorio y Teléfono no
      
        cajaTelefono.querySelector('input').required = false;

        // Quitar el requisito de Coordinador
        cajaCoordinador.querySelector('select').required = false;

    } else if (selectedValue == 2) {
        cajaCoordinador.style.display = "block";
        
        cajaTelefono.style.display = "block";

        cajaCentro.style.display = "block";
        cajaRegional.style.display = "block";

       
        // Hacer que Coordinador, Email y Teléfono sean obligatorios
        cajaCoordinador.querySelector('select').required = true;
       
        cajaTelefono.querySelector('input').required = true;
        cajaCentro.querySelector('select').required = true;
        cajaRegional.querySelector('select').required = true;

    } else {
        cajaCoordinador.style.display = "none";
        cajaCentro.style.display = "none";
        cajaRegional.style.display = "none";
     
        cajaTelefono.style.display = "none";

        // Quitar el requisito de todos los campos
        cajaCentro.querySelector('select').required = false;
        cajaRegional.querySelector('select').required = false;
        cajaCoordinador.querySelector('select').required = false;
        
        cajaTelefono.querySelector('input').required = false;
    }
}

function filtradoEntreDosSelects(idFiltrador, idFiltrado, atributoEnFiltrado) {
    // Obtenemos los elementos select
    let filtradorSelect = document.getElementById(idFiltrador);
    let filtradoSelect = document.getElementById(idFiltrado);

    // Obtenemos el valor seleccionado en el select filtrador
    let idFiltro = filtradorSelect.value;

    // Iteramos sobre todas las opciones del select filtrado
    for (let i = 0; i < filtradoSelect.options.length; i++) {
        let option = filtradoSelect.options[i];

        // Ignoramos la opción por defecto (vacía)
        if (option.value === "") {
            continue;
        }

        // Obtenemos el valor del atributo que relaciona la opción con el select filtrador
        let idRelacion = option.getAttribute(atributoEnFiltrado);

        // Mostrar u ocultar la opción según el valor seleccionado en el filtrador
        if (idFiltro === "" || idRelacion === idFiltro) {
            option.style.display = 'block';
        } else {
            option.style.display = 'none';
        }
    }

    // Reiniciamos la selección del select filtrado
    filtradoSelect.selectedIndex = 0;
}


