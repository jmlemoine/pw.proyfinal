import encapsulacion.Usuario;
import freemarker.template.Configuration;
import freemarker.template.Version;
import io.jsonwebtoken.Claims;
import modelo.EntityServices.EntityServices.RutaService;
import modelo.EntityServices.EntityServices.UsuarioService;
import modelo.EntityServices.EntityServices.VisitaService;
import modelo.EntityServices.utils.Crypto;
import modelo.EntityServices.utils.DBService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



        }


    }


