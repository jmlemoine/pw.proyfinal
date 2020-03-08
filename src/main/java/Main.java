import encapsulacion.Usuario;
import io.jsonwebtoken.Claims;
import modelo.EntityServices.utils.DBService;

import java.util.ArrayList;
import java.util.List;

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


        }


    }


