<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%--BOOTSTRAP--%>
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <%--ESTILO-MAQUETA--%>
        <link rel="stylesheet" href="../css/styleMaqueta.css"/>
        <%--TIPOS-LETRAS--%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">
        <%--JQUERY--%>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <%--TRANSICIONES--%>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="../js/tablas.js"></script>       
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/Componentes/SideBar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="/Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                    <%--CONTENIDO INICIO --%>
                    <div class="container">
                        <div class="row">
                            <div class="col-12">  
                                <div class="container">
                                    <div class="row">
                                        <div class="col-lg-10 col-md-10 col-sd-12"><h2 class="letra py-3">Informacion de Red</h2></div>
                                        <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="" width="70px" height="70px" alt="alt"/></div>
                                    </div>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-4 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-md-6 col-8"><b>Nueva Red:</b> </div>
                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalRed"><b>Formulario</b></button>
                                            </div>
                                        </div> 
                                        <div class="col-md-8 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                <input type="text" class="form-control" id="filtro1">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <section class="intro mb-2">
                                    <div class="bg-image" >
                                        <div class="mask d-flex align-items-center h-100">
                                            <div class="container tableContenido">
                                                <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                    <div class="col-12"> 
                                                        <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 200px">
                                                                <table id="tablaRed" class="table table-striped table-sm  mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr class="">
                                                                            <th class="text-white">Codigo</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Opcines</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>

                            <div class="col-12 mt-5">  
                                <div class="container">
                                    <div class="row">
                                        <div class="col-lg-10 col-md-10 col-sd-12"><h2 class="letra py-3">Informacion de Area</h2></div>
                                    </div>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-4 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-md-6 col-8"><b>Nueva Area:</b> </div>
                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalArea"><b>Formulario</b></button>
                                            </div>
                                        </div> 
                                        <div class="col-md-8 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                <input type="text" class="form-control" id="filtro1">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <section class="intro mb-2">
                                    <div class="bg-image" >
                                        <div class="mask d-flex align-items-center h-100">
                                            <div class="container tableContenido">
                                                <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                    <div class="col-12"> 
                                                        <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 200px">
                                                                <table id="tablaArea" class="table table-striped table-sm  mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr class="">
                                                                            <th class="text-white">Codigo</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Red</th>
                                                                            <th class="text-white">Opcines</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>  
                    </div>
                    <%--CONTENIDO FINAL --%>
                </main>
                <jsp:include page="/Componentes/footer2.jsp" ></jsp:include>
                </div>
            </div>
        <jsp:include page="../Componentes/modalGuardar.jsp" ></jsp:include>  

        <%--MENU--%>       
        <script src="../js/scriptMenu.js"></script>
        <%--BOOTSTRAP--%>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <%--TRANSICIONES--%>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <%--ALERTAS--%>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>
