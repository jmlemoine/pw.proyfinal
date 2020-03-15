package dao.Implementations;

import dao.interfaces.UsuarioDAO;
import encapsulacion.Ruta;
import encapsulacion.Usuario;
import modelo.EntityServices.EntityServices.RutaService;
import modelo.EntityServices.utils.CRUD;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UsuarioDAOImpl extends CRUD<Usuario> implements UsuarioDAO {
    ////////
    public UsuarioDAOImpl(Class<Usuario> usuarioClass){
        super(usuarioClass);
    }

    @Override
    public void insert(Usuario e){
        crear(e);
    }

    @Override
    public void update(Usuario e){
        editar(e);
    }

    @Override
    public void delete(Usuario e){
        List<Ruta> r = RutaService.getInstancia().getByUser(e.getId());
        for (Ruta rd : r){
            RutaService.getInstancia().delete(rd);
        }
        eliminar(e.getId());
    }

    @Override
    public List<Usuario> getAll() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findAllUsuario");
        return (List<Usuario>) query.getResultList();
    }

    @Override
    public Usuario getById(long id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findUsuariobyId");
        query.setParameter("id", id);
        return (Usuario) query.getSingleResult();
    }

    @Override
    public Usuario validateLogin(String user, String pass) {
        Usuario usuario = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.validateLogIn");
        query.setParameter("username", user);
        query.setParameter("pass", pass);
        try {
            usuario = (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontro el usuario.");
        }
        return usuario;
    }

    @Override
    public List<Usuario> getPagination(int pag) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findAllUsuario");
        query.setFirstResult((pag - 1) * 5);
        query.setMaxResults(5);
        return query.getResultList();
    }


}
