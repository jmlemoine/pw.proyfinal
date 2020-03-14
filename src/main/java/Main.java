import encapsulacion.Ruta;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import freemarker.template.Version;
import io.jsonwebtoken.Claims;
import modelo.EntityServices.EntityServices.RutaService;
import modelo.EntityServices.EntityServices.UsuarioService;
import modelo.EntityServices.EntityServices.VisitaService;
import modelo.EntityServices.utils.Crypto;
import modelo.EntityServices.utils.DBService;
import modelo.EntityServices.utils.Rest.JsonUtilidades;
import modelo.EntityServices.utils.TokenService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

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

            if (usuarioService.validateLogin("admin", "admin") == null){
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

                for(String key : cookies.keySet()){
                    System.out.println("llave: "+key+" valor: "+cookies.get(key));
                    llaveValor = cookies.get(key).split(",");
                    System.out.println(request.cookies());

                }

                if (llaveValor.length > 1){
                    Crypto crypto = new Crypto();

                    System.out.println(llaveValor[0] + " contra: "+llaveValor[1]);
                    String user = crypto.decrypt(llaveValor[0], iv, secretKeyUSer);
                    String contra = crypto.decrypt(llaveValor[1], iv, secretKeyContra);


                    Usuario usuario1 = UsuarioService.getInstancia().validateLogin(user, contra);
                    if (usuario1 != null){
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
                System.out.println(user+" pass : "+contra);
                Usuario usuario1 = UsuarioService.getInstancia().validateLogin(user, contra);

                Map<String, Object> jsonResponse = new HashMap<>();
                if (usuario1 != null){
                    if (recordar != null && recordar.equalsIgnoreCase("on")) {

                        Crypto crypto = new Crypto();
                        String userEncrypt = crypto.encrypt(user, iv, secretKeyUSer);
                        String contraEncrypt = crypto.encrypt(contra, iv, secretKeyContra);

                        System.out.println("user encryp: " + userEncrypt + " contra encryp: "+contraEncrypt);

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
                }
                else {

                }

                return jsonResponse;
            }, JsonUtilidades.json());

            get("/inicio/:pag", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Usuario usuario = request.session().attribute("usuario");
                String p = request.session().attribute("usuario");
                userLevel(attributes, usuario);
                int pagina = Integer.parseInt(p);
                Ruta n;
                if (usuario == null){
                    attributes.put("list", rutaService.getNullsPagination(pagina));
                    attributes.put("actual", pagina);
                    attributes.put("paginas", Math.ceil(rutaService.cantPagNulls()/5f));
                    attributes.put("ruta", rutaService.getNulls());

                }
                else {
                    attributes.put("list", rutaService.getPagination(pagina, usuario.getId()));
                    attributes.put("actual", pagina);
                    attributes.put("paginas", Math.ceil(rutaService.cantPag(usuario.getId())/5f));
                    attributes.put("ruta", rutaService.getByUser(usuario.getId()));
                    attributes.put("usuario", usuario);
                }
                return new ModelAndView(attributes, "inicio.ftl")

            });


        }

        private static void userLevel(Map<String, Object> attributes, Usuario usuario){
            attributes.put("usuario", usuario);
            if (usuario != null){
                attributes.put("admin", usuario.getAdministrator());
                attributes.put("usuarioName", usuario.getNombre());
            }

        }


    }


