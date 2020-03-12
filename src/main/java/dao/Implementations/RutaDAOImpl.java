package dao.Implementations;

import dao.interfaces.RutaDAO;
import encapsulacion.Ruta;
import encapsulacion.Visita;
import modelo.EntityServices.EntityServices.VisitaService;
import modelo.EntityServices.utils.CRUD;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RutaDAOImpl extends CRUD<Ruta> implements RutaDAO {
    ////////
    public RutaDAOImpl(Class<Ruta> rutaClass) {
        super(rutaClass);

    }

    @Override
    public void insert(Ruta e) {
        crear(e);
    }

    @Override
    public void update(Ruta e) {
        editar(e);
    }

    @Override
    public void delete(Ruta e) {
        List<Visita> v = VisitaService.getInstancia().getByRuta(e.getId());
        for(Visita vd: v){
            VisitaService.getInstancia().delete(vd);
        }
        eliminar(e.getId());
    }

    @Override
    public List<Ruta> getAll() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findAllURL");
        return (List<Ruta>) query.getResultList();
    }

    @Override
    public Ruta getById(long id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findURLbyId");
        query.setParameter("id", id);
        return (Ruta) query.getResultList();
    }

    @Override
    public List<Ruta> getByUser(long user_id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findURLbyUserId");
        query.setParameter("userid", user_id);
        return (List<Ruta>) query.getResultList();
    }

    @Override
    public Ruta generateShortLink(String ruta) {
        return null;
    }

    @Override
    public Ruta getByRutaAcortada(String rutaAcortada) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findURLbyRutaAcortada");
        query.setParameter("rutaacortada", rutaAcortada);
        return (Ruta) query.getSingleResult();
    }

    @Override
    public List<Ruta> getNulls() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findNulls");
        return (List<Ruta>) query.getResultList();
    }

    @Override
    public List<Ruta> getPagination(int pag, long id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findURLbyUserId");
        query.setParameter("userid", id);
        query.setFirstResult((pag - 1) * 5);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public int cantPag(long id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findURLbyUserId");
        query.setParameter("userid", id);
        return query.getResultList().size();
    }

    @Override
    public int cantPagNulls() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findNulls");
        return query.getResultList().size();
    }

    @Override
    public List<Ruta> getNullsPagination(int pag) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findNulls");
        query.setFirstResult((pag - 1) * 5);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public List<Ruta> getPagAll(int pag) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ruta.findAllURL");
        query.setFirstResult((pag - 1) * 5);
        query.setMaxResults(5);
        return query.getResultList();
    }
}
