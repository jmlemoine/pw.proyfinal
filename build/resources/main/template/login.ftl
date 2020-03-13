<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Proyecto Final Web</title>
    <!--////////-->

</head>
<body>

<section>
    <div>
        <div>
            <div>
                <div>
                    <div>
                        <h4>Login</h4>
                        <form method="POST" action="/iniciarSesion">

                            <#assign x="">

                            <div>
                                <label for="email">Usuario</label>
                                <input id="email" type="text" name="usuario" required autofocus value="${user!x}">
                            </div>

                            <div>
                                <label for="password">Contraseña</label>
                                <input id="password" type="password" name="password" required data-eye value="${pass!x}">
                            </div>

                            <div>
                                <button type="submit">Login</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>