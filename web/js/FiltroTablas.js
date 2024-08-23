/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function filtrarTabla(filtro, idTabla) {
    const filtroInput = filtro.trim().toLowerCase();
    const tabla = document.getElementById(idTabla);
    const filas = tabla.querySelectorAll("tbody tr");

    filas.forEach(function (fila) {
        const textoFila = fila.textContent.toLowerCase();
        if (textoFila.includes(filtroInput)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
}