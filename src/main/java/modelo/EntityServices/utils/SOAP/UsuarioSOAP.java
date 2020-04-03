package modelo.EntityServices.utils.SOAP;

import encapsulacion.Ruta;
import encapsulacion.RutaSoap;
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
    public List<RutaSoap> getRuta(long usuario){
        List<RutaSoap> urlDao = new ArrayList<>();
        Usuario usuario1 = UsuarioService.getInstancia().getById(usuario);
        //Ruta ruta = new Ruta();
        Set<Ruta> rutas = usuario1.getRutas();
        RutaSoap rutaSoap = new RutaSoap();
        for (Ruta ruta: rutas){
            rutaSoap.setRedirect(rutaSoap.getRedirect());
            rutaSoap.setRuta(ruta.getRuta());
            urlDao.add(rutaSoap);
        }
        return urlDao;

    }

    /*@WebMethod
    public List<Ruta> getUrl(Long usuario) {
        RutaService rutaService = RutaService.getInstancia();
        List<Ruta> rutaList = rutaService.getByUser(usuario);
        return rutaList;
    }*/

}
