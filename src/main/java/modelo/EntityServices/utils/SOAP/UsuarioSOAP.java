package modelo.EntityServices.utils.SOAP;

import encapsulacion.Ruta;
import encapsulacion.Usuario;
import modelo.EntityServices.EntityServices.RutaService;
import modelo.EntityServices.EntityServices.UsuarioService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebService
public class UsuarioSOAP {
    @WebMethod
    public List<Ruta> getUrl(Long usuario) {
        RutaService rutaService = RutaService.getInstancia();
        List<Ruta> rutaList = rutaService.getByUser(usuario);
        return rutaList;
    }

}
