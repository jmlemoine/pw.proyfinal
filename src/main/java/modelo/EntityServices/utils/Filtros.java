package modelo.EntityServices.utils;

import encapsulacion.Usuario;

import static spark.Spark.before;

public class Filtros {

    public void filtros() {

        before((request, response) -> System.out.println("Ruta antes: " + request.pathInfo()));
        before("/inicio", (request, response) -> response.redirect("/inicio/1"));


        before( "/borrarUsuario/:id", (request, response) -> {
            Usuario usuario = request.session(  true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });
        before("/adminPanel/:pag/:pagl", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });

        before("/links_usuario/:id/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });
        before("/borrarusuario/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });

        before("/stats/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/inicio/1");
            }
        });

        before("/borrarlink/:id/:ruta", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            String ruta = request.params("ruta");
            String id = request.params("id");
            long userid = Integer.parseInt(id);
            long rutaid = Integer.parseInt(ruta);
            if (usuario == null /*|| usuario.getId() == RutaService.getInstancia().getById(rutaid).getUsuario().getId() */||!usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });
        before("/userlevel/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });
        before("/borrarlink2/:id/:ruta", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            String ruta = request.params("ruta");
            String id = request.params("id");
            long userid = Integer.parseInt(id);
            long rutaid = Integer.parseInt(ruta);
            if (usuario == null || usuario.getId() == userid || !usuario.getAdministrator()) {
                response.redirect("/inicio/1");
            }
        });

    }

}
