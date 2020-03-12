package modelo.EntityServices.EntityServices;

import dao.Implementations.VisitaDAOImpl;
import dao.interfaces.VisitaDAO;
import encapsulacion.Visita;

import java.util.List;

public class VisitaService  implements VisitaDAO {
    ////////
    private VisitaDAOImpl visitaDAO;
    private static VisitaService instancia;

    public VisitaService(){
        visitaDAO = new VisitaDAOImpl(Visita.class);
    }

    public static VisitaService getInstancia(){
        if(instancia == null)
            instancia = new VisitaService();

        return instancia;
    }

    @Override
    public void insert(Visita e) {
        visitaDAO.insert(e);
    }

    @Override
    public void update(Visita e) {
        visitaDAO.update(e);
    }

    @Override
    public void delete(Visita e) {
        visitaDAO.delete(e);
    }

    @Override
    public List<Visita> getAll() {
        return visitaDAO.getAll();
    }

    @Override
    public Visita getById(long id) {
        return visitaDAO.getById(id);
    }

    @Override
    public List<Visita> getByRuta(long ruta_id) {
        return visitaDAO.getByRuta(ruta_id);
    }
}
