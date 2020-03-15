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

    <#--Google Charts-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script type="text/javascript">
        google.charts.load("current", {packages: ["corechart"]});
        google.charts.setOnLoadCallback(drawChart);
        google.charts.setOnLoadCallback(drawAxisTickColors);

        function drawAxisTickColors() {
            const Http = new XMLHttpRequest();
            const url = 'https://roselem.me/rest/rutas/${link.id}/visitas';
            Http.open("GET", url);
            Http.send();
            let fecha = new Date();
            Http.onreadystatechange = (e) => {
                var data = new google.visualization.DataTable();
                data.addColumn('date', 'Day');
                data.addColumn('number', 'Visitas');
                var o = JSON.parse(Http.response);
                //console.log(o);
                let cont = 0;
                for (let i = 0; i < o.length; i++) {
                    let pos = '' + i + '';
                    //console.log(o[pos].fecha);
                    cont++;
                    fechas = o[pos].fecha;
                    d = fechas;
                    var d = new Date(o[pos].fecha);
                    fecha = new Date(d);
                    //console.log(d);
                    data.addRow([new Date(o[pos].fecha), i]);

                    // console.log(fecha);
                    var classicOptions = {
                        title: 'Visitas por mes',
                        width: 1080,
                        height: 500,
                        // Gives each series an axis that matches the vAxes number below.
                        series: {
                            0: {targetAxisIndex: 0}
                        },
                        vAxes: {
                            // Adds titles to each axis.
                            0: {title: 'Visitas'},
                        },
                        hAxes: {
                            // Adds titles to each axis.
                            0: {title: 'Fecha'},
                        },
                        hAxis: {
                            ticks: [
                                new Date(2019, 4, 12),
                                new Date(2019, 4, 13)
                            ]
                        },
                        vAxis: {
                            viewWindow: {
                                max: 30
                            }
                        }
                    };
                    var chart = new google.visualization.LineChart(document.getElementById('myPieChart'));
                    chart.draw(data, classicOptions);
                }
            };
            // data.addRows([
            //     [new Date(2014, 0), 5],
            //     [new Date(2014, 1), 7],
            //     [new Date(2014, 2), 1],
            //     [new Date(2014, 3), 12],
            //     [new Date(2014, 4), 4],
            //     [new Date(2014, 5), 12],
            //     [new Date(2014, 6), 10],
            //     [new Date(2014, 7), 9],
            //     [new Date(2014, 8), 8],
            //     [new Date(2014, 9), 0],
            //     [new Date(2014, 10), 1],
            //     [new Date(2014, 11), 2]
            // ]);

        }


        function drawChart() {
            var chart = new google.visualization.BarChart(document.getElementById("barchart_values"));
            chart.draw(view, options);
        }

    </script>
</head>

<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/inicio/1">Inicio</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <#if admin?? && admin == true>
                    <li class="nav-item">
                        <a class="nav-link" href="/adminPanel/1/1">Panel de Admin</a>
                    </li>
                </#if>
                <#if usuario??>
                    <li class="nav-item">
                        <a class="nav-link" href="/logOut">Salir</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Log In</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <!-- Post Content Column -->
            <div class="col-lg-8">
                <h2 class="mt-4">Visitas</h2>
                <div class="lead">
                    <div id="myPieChart"/>
                </div>

            </div>


            <!-- /.row -->
        </div>

    </div>
    <!-- Sidebar Widgets Column -->
    <div class="col-md-8">
        <div id="barchart_values">

        </div>

    </div>
    <div class="col-md-4">
        <!-- Side Widget -->
        <div class="card my-4">
            <h5 class="card-header">Codigo QR</h5>
            <div class="container-fluid">
                <div class="text-center">
                    <img src="https://chart.googleapis.com/chart?cht=qr&chl=${link.ruta}&chs=160x160&chld=L|0"
                         class="qr-code img-thumbnail img-responsive" alt="">
                </div>
            </div>
        </div>

    </div>
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
