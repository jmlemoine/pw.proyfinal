<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Proyecto Final</title>

    <!-- Bootstrap core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/blog-post.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/my-login.css">
    <link href="../css/blog-login.css" rel="stylesheet">
</head>

<body>
<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Post Content Column -->
        <div class="col-lg-8">

            <div class="lead">
                <!-- Title -->
                <form method="post" action="/guardarUsuario" style="width: 500px">

                    <div class="form-group">
                        <h1 class="mt-4">Registrarse</h1>

                        <label>Nombre de usuario</label>
                        <input type="text" class="form-control" placeholder="username" aria-label="Username"
                               name="usuario" required>
                    </div>

                    <div class="form-group">
                        <label>Nombre</label>
                        <input type="text" class="form-control" rows="3" placeholder="John Doe" name="nombre" required>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" id="password" class="form-control" rows="3" name="pass" required
                               data-eye>
                    </div>

                    <button style="float: right" type="submit" class="btn btn-primary">Registrar</button>
                </form>
            </div>


        </div>
        <!-- Sidebar Widgets Column -->
        <div class="col-md-4">

            <!-- Side Widget -->
            <div class="card my-4">
                <h5 class="card-header">Blog</h5>
                <div class="card-body">

                </div>
            </div>

        </div>

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<!-- Footer -->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white"></p>
    </div>
    <!-- /.container -->
</footer>

<!-- Bootstrap core JavaScript -->
<script src="../vendor/vendor/jquery/jquery.min.js"></script>
<script src="../vendor/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
