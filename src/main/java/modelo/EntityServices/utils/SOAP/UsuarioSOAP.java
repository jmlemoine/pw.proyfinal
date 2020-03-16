package modelo.EntityServices.utils.SOAP;

import encapsulacion.Ruta;
import modelo.EntityServices.EntityServices.RutaService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class UsuarioSOAP {

    @WebMethod
    public List<Ruta> getUrl(Long usuario) {
        RutaService rutaService = RutaService.getInstancia();
        List<Ruta> rutaList = rutaService.getByUser(usuario);
        return rutaList;
    }

}
