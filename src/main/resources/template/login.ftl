<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Proyecto Final</title>
    <!-- Bootstrap core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="../css/blog-login.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/my-login.css">
</head>
<body class="my-login-page">


<section class="h-100">
    <div class="container h-100">
        <div class="row justify-content-md-center h-100">
            <div class="card-wrapper">
                <div class="card fat">
                    <div class="card-body">
                        <h4 class="card-title">Login</h4>
                        <form method="POST" action="/iniciarSesion">
                            <#assign x="">
                            <div>
                                <div class="form-group">
                                    <label for="email">Usuario</label>

                                    <input id="email" type="text" class="form-control" name="usuario" required autofocus
                                           value=${user!x}>
                                </div>

                                <div class="form-group">
                                    <label for="password">Contraseña
                                    </label>
                                    <input id="password" type="password" class="form-control" name="password" required
                                           data-eye value=${pass!x}>
                                </div>

                                <div class="form-group no-margin">
                                    <button type="submit" class="btn btn-primary btn-block">
                                        Login
                                    </button>
                                </div>
                                <div class="form-group no-margin">
                                    <a href="/agregarUsuario" class="btn btn-primary btn-block"
                                       style="margin-top: 2vh">
                                        Registrarme
                                    </a>
                                </div>
                                <div class="form-group no-margin">
                                    <a href="/inicio/1" class="btn btn-primary btn-block"
                                       style="margin-top: 2vh;">
                                        Omitir
                                    </a>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
</section>

<script src="../vendor/bootstrap/js/jquery.min.js"></script>

<#--<script>-->
<#--$(document).ready(function () {-->
<#--$("#login").click(function () {-->
<#--var email = $("#email").val();-->
<#--var password = $("#password").val();-->
<#--$.post("http://localhost:4567/iniciarSesion/" + email + "/" + password, function (data) {-->
<#--console.log(data.token);-->
<#--sessionStorage.setItem("token", data.token);-->
<#--window.location.href = "/inicio/1"-->
<#--})-->
<#--})-->
<#--})-->
<#--</script>-->
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../vendor/bootstrap/js/my-login.js"></script>
</body>
</html>