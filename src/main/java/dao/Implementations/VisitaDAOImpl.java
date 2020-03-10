package dao.Implementations;

import dao.interfaces.VisitaDAO;
import encapsulacion.Visita;
import modelo.EntityServices.utils.CRUD;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VisitaDAOImpl extends CRUD<Visita> implements VisitaDAO {

    public VisitaDAOImpl(Class<Visita> visitaClass){
        super(visitaClass);

    }

    @Override
    public void insert(Visita e) {
        crear(e);
    }

    @Override
    public void update(Visita e) {
        editar(e);
    }

    @Override
    public void delete(Visita e) {
        eliminar(e.getId());
    }

    @Override
    public List<Visita> getAll() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Visita.findAllVisita");
        return (List<Visita>) query.getResultList();
    }

    @Override
    public Visita getById(long id) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Visita.findVisitaById");
            query.setParameter("id", id);
            return (Visita) query.getResultList();
        }
        catch (Exception e){
            return null;
        }
        finally {
            em.close();
        }

    }

    @Override
    public List<Visita> getByRuta(long ruta_id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Visita.findAllVisitaByRutaId");
        query.setParameter("id", ruta_id);
        return (List<Visita>) query.getResultList();
    }
}
