import com.google.gson.Gson;
import encapsulacion.Ruta;
import encapsulacion.Usuario;
import encapsulacion.Visita;
import eu.bitwalker.useragentutils.UserAgent;
import freemarker.template.Configuration;
import freemarker.template.Version;
import io.jsonwebtoken.Claims;
import modelo.EntityServices.EntityServices.RutaService;
import modelo.EntityServices.EntityServices.UsuarioService;
import modelo.EntityServices.EntityServices.VisitaService;
import modelo.EntityServices.utils.Crypto;
import modelo.EntityServices.utils.DBService;
import modelo.EntityServices.utils.Filtros;
import modelo.EntityServices.utils.Rest.JsonUtilidades;
import modelo.EntityServices.utils.SOAP.Arranque;
import modelo.EntityServices.utils.TokenService;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static spark.Spark.*;


@SuppressWarnings("Duplicates")
    public class Main {

        static final String iv = "0123456789123456";  // 16 caracteres
        static final String secretKeyUSer = "qwerty987654321";
        static final String secretKeyContra = "123456789klk";

        public final static String ACCEPT_TYPE_JSON = "application/json";
        public final static String ACCEPT_TYPE_XML = "application/xml";
        public static List<Claims> tokens = new ArrayList<>();

        public static String[] direcciones = {"favicon.ico", "", "iniciarSesion", "inicio", "borrarlink", "borrarlink2", "agregarlink",
                "guardarUsuario", "agregarUsuario", "adminPanel", "links_usuario", "stats", "inicio", "iniciarSesion"};


        public static void main(String[] args) throws Exception {

            staticFiles.location("/template");

            DBService.getInstancia().iniciarDn();

            //Entrando el admin
            Usuario usuarioStart = new Usuario("admin", "admin", "admin", true);


            //Clases que representan los servicios
            UsuarioService usuarioService = UsuarioService.getInstancia();
            RutaService rutaService = RutaService.getInstancia();
            VisitaService visitaService = VisitaService.getInstancia();

            if (usuarioService.validateLogIn("admin", "admin") == null) {
                usuarioService.insert(usuarioStart);
            }

            Configuration configuration = new Configuration(new Version(2, 3, 0));
            configuration.setClassForTemplateLoading(Main.class, "/template");

            FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

            get("/", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Map<String, String> cookies = request.cookies();

                String[] llaveValor = new String[2];
                request.cookie("login");

                for (String key : cookies.keySet()) {
                    System.out.println("llave: " + key + " valor: " + cookies.get(key));
                    llaveValor = cookies.get(key).split(",");
                    System.out.println(request.cookies());

                }

                if (llaveValor.length > 1) {
                    Crypto crypto = new Crypto();

                    System.out.println(llaveValor[0] + " contra: " + llaveValor[1]);
                    String user = crypto.decrypt(llaveValor[0], iv, secretKeyUSer);
                    String contra = crypto.decrypt(llaveValor[1], iv, secretKeyContra);


                    Usuario usuario1 = UsuarioService.getInstancia().validateLogIn(user, contra);
                    if (usuario1 != null) {
                        request.session(true);
                        request.session().attribute("usuario", usuario1);
                        response.redirect("/inicio");
                    }

                }

                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            post("/iniciarSesion", (request, response) -> {

                String user = request.queryParams("usuario");
                String contra = request.queryParams("password");
                String recordar = request.queryParams("remember");

                System.out.println(recordar);
                System.out.println(user + " pass : " + contra);
                Usuario usuario1 = UsuarioService.getInstancia().validateLogIn(user, contra);

                Map<String, Object> jsonResponse = new HashMap<>();
                if (usuario1 != null) {
                    if (recordar != null && recordar.equalsIgnoreCase("on")) {

                        Crypto crypto = new Crypto();
                        String userEncrypt = crypto.encrypt(user, iv, secretKeyUSer);
                        String contraEncrypt = crypto.encrypt(contra, iv, secretKeyContra);

                        System.out.println("user encryp: " + userEncrypt + " contra encryp: " + contraEncrypt);

                        //Incluyendo el path del cookie.
                        response.cookie("/", "login", userEncrypt + "," + contraEncrypt, 604800, false);

                    }

                    jsonResponse.put("usuario", usuario1);
                    String token = TokenService.createJWT(UUID.randomUUID().toString(),
                            usuario1.getAdministrator() ? "admin" : "user",
                            usuario1.getUsername(), 604800000);
                    jsonResponse.put("token", token);
                    request.session(true);
                    request.session().attribute("usuario", usuario1);
                    response.header("Content-Type", "application/json");
                    System.out.println("funca");
                    response.redirect("/inicio/1");
                    for (Ruta r : rutaService.getNulls()) {
                        r.setUsuario(usuario1);
                        rutaService.update(r);
                    }
                } else {

                }

                return jsonResponse;
            }, JsonUtilidades.json());

            get("/inicio/:pag", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                String p = request.params("pag");
                userLevel(attributes, usuario);
                int pagina = Integer.parseInt(p);
                Ruta n;
                if (usuario == null) {
                    attributes.put("list", rutaService.getNullsPagination(pagina));
                    attributes.put("actual", pagina);
                    attributes.put("paginas", Math.ceil(rutaService.cantPagNulls() / 5f));
                    attributes.put("ruta", rutaService.getNulls());

                } else {
                    attributes.put("list", rutaService.getPagination(pagina, usuario.getId()));
                    attributes.put("actual", pagina);
                    attributes.put("paginas", Math.ceil(rutaService.cantPag(usuario.getId()) / 5f));
                    attributes.put("ruta", rutaService.getByUser(usuario.getId()));
                    attributes.put("usuario", usuario);
                }
                return new ModelAndView(attributes, "inicio.ftl");
            }, freeMarkerEngine);

            get("/stats/:id", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                userLevel(attributes, usuario);
                String id = request.params("id");
                int rid = Integer.parseInt(id);
                attributes.put("link", rutaService.getById(rid));
                return new ModelAndView(attributes, "stats.ftl");
            }, freeMarkerEngine);

            get("/links_usuario/:id/:pag", (request, response) -> {
                String id = request.params("id");
                long userid = Integer.parseInt(id);
                String p = request.params("pag");
                int pagina = Integer.parseInt(p);
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                userLevel(attributes, usuario);
                attributes.put("actual", pagina);
                attributes.put("paginas", Math.ceil(rutaService.cantPagNulls() / 5f));
                attributes.put("list", rutaService.getPagination(pagina, userid));
                attributes.put("ruta", rutaService.getPagination(pagina, userid));
                attributes.put("user", usuarioService.getById(userid));
                return new ModelAndView(attributes, "links_usuario.ftl");

            }, freeMarkerEngine);

            get("/adminPanel/:pag/:pagl", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                userLevel(attributes, usuario);
                String p = request.params("pag");
                int pagina = Integer.parseInt(p);
                String pl = request.params("pagl");
                int paginal = Integer.parseInt(pl);

//            //-----------paginacion usuarios
                attributes.put("actual", pagina);
                attributes.put("paginas", Math.ceil(usuarioService.getAll().size() / 5f));
                attributes.put("list", usuarioService.getPagination(pagina));
                attributes.put("user", usuarioService.getPagination(pagina));
//            //-----------paginacion links
                attributes.put("actuall", paginal);
                attributes.put("list2", rutaService.getPagAll(paginal));
                attributes.put("paginasl", Math.ceil(rutaService.getAll().size() / 5f));
                attributes.put("ruta2", rutaService.getPagAll(paginal));

                return new ModelAndView(attributes, "admin_panel.ftl");
            }, freeMarkerEngine);

            get("/agregarUsuario", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                userLevel(attributes, usuario);
                for (Ruta r : rutaService.getNulls()) {
//                    rutaService.update(r.getId(),r.getRuta(),r.getRuta_acortada(),usuario);
//                    n = new Ruta(r.getRuta(), r.getRuta_acortada(), usuario);
                    r.setUsuario(usuario);
                    rutaService.update(r);
//                    rutaService.delete(r);
                }
                return new ModelAndView(attributes, "agregarUsuario.ftl");
            }, freeMarkerEngine);

            post("/guardarUsuario", (request, response) -> {
                String username = request.queryParams("usuario");
                String nombre = request.queryParams("nombre");
                String pass = request.queryParams("pass");
                Usuario u = new Usuario(username, nombre, pass, false);
                UsuarioService.getInstancia().insert(u);
                System.out.println(username + " pass : " + pass);
                Usuario usuario1 = UsuarioService.getInstancia().validateLogIn(username, pass);
                if (usuario1 != null) {
                    request.session(true);
                    request.session().attribute("usuario", usuario1);
                    response.redirect("/inicio/1");
                }
                return "";
            });

            post("/agregarlink", (request, response) -> {
                String link = request.queryParams("link");
                Usuario usuario = request.session().attribute("usuario");
                Ruta r = new Ruta(link, "roselem.me/test", usuario);
                RutaService.getInstancia().insert(r);
                shortUrl(r.getId());
                response.redirect("/inicio/1");
                return "";
            });

            get("/borrarlink/:id/:pag", (request, response) -> {
                String pag = request.params("pag");
                String id = request.params("id");
                long rutaid = Integer.parseInt(id);
                long pagnum = Integer.parseInt(pag);
                Ruta r = rutaService.getById(rutaid);
                System.out.println("id " + r.getId());
                rutaService.delete(r);
                if (pagnum == 1) {
                    response.redirect("/inicio/1");
                } else if (pagnum == 2) {
                    response.redirect("/adminPanel/1/1");
                }
                return "";
            });

            get("/borrarlink2/:ruta/:id", (request, response) -> {
                String ruta = request.params("ruta");
                String id = request.params("id");
                long userid = Integer.parseInt(id);
                long rutaid = Integer.parseInt(ruta);
                Ruta r = rutaService.getById(rutaid);
                rutaService.delete(r);
                response.redirect("/links_usuario/" + userid + "/1");
                return "";
            });

            get("/borrarusuario/:id", (request, response) -> {
                String id = request.params("id");
                long userid = Integer.parseInt(id);
                Usuario u = usuarioService.getById(userid);
                usuarioService.delete(u);
                response.redirect("/adminPanel/1/1");
                return "";
            });

            get("/logOut", (request, response) -> {

                Session session = request.session(true);
                session.invalidate();
                response.removeCookie("/", "login");
                response.redirect("/inicio/1");
                return "";
            });

            get("/userlevel/:id", (request, response) -> {
                String id = request.params("id");
                long userid = Integer.parseInt(id);
                Usuario u = usuarioService.getById(userid);
                Boolean level = !u.getAdministrator();
                Usuario nu = new Usuario(u.getId(), u.getUsername(), u.getNombre(), u.getPassword(), level);
                usuarioService.update(nu);
                response.redirect("/adminPanel/1/1");
                return "";
            });

            get("/:corto", (request, response) -> {
                String corto = request.params("corto");
                for (String ruta : direcciones) {
                    if (ruta.contains(corto) && corto.length() > 4) {
                        System.out.println("ignora");
                        return "";
                    }
                }

                Ruta r = rutaService.getById(Long.parseLong(corto, 16));
                Visita v = new Visita();
                Date date = new Date();
                v.setFecha(date);
                v.setIp(request.ip());

                //System info
                String os, browser;
                UserAgent osagent = UserAgent.parseUserAgentString(request.headers("User-Agent"));
                UserAgent.parseUserAgentString(request.headers("REMOTE_ADDR")).toString();
                String[] parts = osagent.toString().split("-");

                browser = parts[1];
                v.setNavegador(browser);
                v.setRuta(r);
                visitaService.insert(v);
                if (r.getRuta().contains("https://")) {
                    response.redirect(r.getRuta());
                } else {
                    response.redirect("https://" + r.getRuta());
                }
                return "";


            });


            //Rutas Servicios RESTFUL
            path("/rest", () -> {
                //Filtros específicos
                afterAfter("/*", (request, response) -> {   //indicando que todas las llamadas retorna un json.

                    if (request.headers("Accept") != null && request.headers("Accept").equalsIgnoreCase(ACCEPT_TYPE_XML)) {
                        response.header("Content-Type", ACCEPT_TYPE_XML);
                    } else {
                        response.header("Content-Type", ACCEPT_TYPE_JSON);
                    }

                });

            });

            get("/", (request, response) -> {
                return "RUTA API REST";
            });

            //RUTAS DEL API
            path("/usuarios", () -> {

                //Listar todos los usuarios.
                get("/", (request, response) -> {
                    return usuarioService.getAll();
                }, JsonUtilidades.json());

                //Retorna un usuario
                get("/:id", (request, response) -> {
                    Usuario usuario = usuarioService.getById(Integer.parseInt(request.params("id")));
                    if (usuario == null) {
                        throw new RuntimeException("No existe el cliente");
                    }
                    return usuario;
                }, JsonUtilidades.json());

                //Crea un usuario
                post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Usuario usuario = null;
                    usuario = new Gson().fromJson(request.body(), Usuario.class);
                    usuarioService.insert(usuario);
                    return true;
                }, JsonUtilidades.json());

                //
                post("/", Main.ACCEPT_TYPE_XML, (request, response) -> {
                    return true;
                }, JsonUtilidades.json());

                //Actualiza un usuario
                put("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
                    usuarioService.update(usuario);
                    return true;
                }, JsonUtilidades.json());

                //Elimina un usuario
                delete("/:id", (request, response) -> {
                    String id = request.params("id");
                    Usuario usuario = usuarioService.getById(Long.parseLong(id));
                    usuarioService.delete(usuario);
                    return true;
                }, JsonUtilidades.json());

            });


            path("/rutas", () -> {

                //Lista todos las rutas.
                get("/", (request, response) -> {
                    return rutaService.getAll();
                }, JsonUtilidades.json());

                //Retorna una ruta
                get("/:id", (request, response) -> {
                    Ruta ruta = rutaService.getById(Integer.parseInt(request.params("id")));
                    if (ruta == null) {
                        throw new RuntimeException("No existe el cliente");
                    }
                    return ruta;
                }, JsonUtilidades.json());

                //Retorna las visitas de una ruta
                get("/:id/visitas", (request, response) -> {
                    Ruta ruta = rutaService.getById(Integer.parseInt(request.params("id")));
                    long rutaid = ruta.getId();
                    System.out.println(ruta.getId());
                    return visitaService.getByRuta(rutaid);

                }, JsonUtilidades.json());
                //Crea una ruta
                post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {

                    Ruta ruta = null;

                    ruta = new Gson().fromJson(request.body(), Ruta.class);
                    rutaService.insert(ruta);
                    return true;
                }, JsonUtilidades.json());

                //
                post("/", Main.ACCEPT_TYPE_XML, (request, response) -> {
                    return true;
                }, JsonUtilidades.json());

                //Actualiza una ruta
                put("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Ruta ruta = new Gson().fromJson(request.body(), Ruta.class);
                    rutaService.update(ruta);
                    return true;
                }, JsonUtilidades.json());

                //Elimina una ruta
                delete("/:id", (request, response) -> {
                    Ruta ruta = rutaService.getById(Long.parseLong(request.params("id")));
                    rutaService.delete(ruta);
                    return true;
                }, JsonUtilidades.json());

            });

            path("/visitas", () -> {

                //Lista todas las rutas
                get("/", (request, response) -> {
                    return visitaService.getAll();
                }, JsonUtilidades.json());

                //Retorna una ruta
                get("/:id", (request, response) -> {

                    Visita visita = visitaService.getById(Integer.parseInt(request.params("id")));
                    if (visita == null) {
                        throw new RuntimeException("No existe el cliente");
                    }
                    return visita;
                }, JsonUtilidades.json());

                //Crea una ruta
                post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {

                    Visita visita = null;
                    visita = new Gson().fromJson(request.body(), Visita.class);

                    visitaService.insert(visita);
                    return true;
                }, JsonUtilidades.json());

                //
                post("/", Main.ACCEPT_TYPE_XML, (request, response) -> {
                    return true;
                }, JsonUtilidades.json());

                //Actualiza una ruta
                put("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Visita visita = new Gson().fromJson(request.body(), Visita.class);
                    visitaService.update(visita);
                    return true;
                }, JsonUtilidades.json());

                //Elimina una ruta
                delete("/:id", (request, response) -> {
                    Visita visita = new Gson().fromJson(request.body(), Visita.class);
                    visitaService.delete(visita);
                    return true;
                }, JsonUtilidades.json());

            });

            Arranque.init();

            new Filtros().filtros();


        }

        private static String getClientIp(HttpServletRequest request){
            String remoteAddr = "";

            if (request != null){
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)){
                    remoteAddr = request.getRemoteAddr();
                }
            }
            return remoteAddr;

        }

        private static void userLevel(Map<String, Object> attributes, Usuario usuario){
            attributes.put("usuario", usuario);
            if (usuario != null){
                attributes.put("admin", usuario.getAdministrator());
                attributes.put("usuarioName", usuario.getNombre());
            }

        }

        public static void shortUrl(long ruta_id){
            Ruta old = RutaService.getInstancia().getById(ruta_id);
            String ruta_acortada = Long.toHexString(ruta_id);
            Ruta r;

            if (old.getRuta().contains("https://")) {
                r = new Ruta(old.getId(), old.getRuta(), ruta_acortada, old.getUsuario());
            }
            else {
                r = new Ruta(old.getId(), "https://"+old.getRuta(), ruta_acortada, old.getUsuario());
            }
            RutaService.getInstancia().update(r);


        }


    }


