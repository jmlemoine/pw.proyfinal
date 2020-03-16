package modelo.EntityServices.EntityServices;

import dao.Implementations.UsuarioDAOImpl;
import dao.interfaces.UsuarioDAO;
import encapsulacion.Usuario;

import java.util.List;

public class UsuarioService implements UsuarioDAO {
    ////////
    private static UsuarioService instancia;

    public static UsuarioService getInstancia() {
        if(instancia == null){
            instancia = new UsuarioService();
        }
        return instancia;
    }

    private UsuarioDAOImpl usuarioDAO;

    public UsuarioService(){
        usuarioDAO = new UsuarioDAOImpl(Usuario.class);
    }

    @Override
    public void insert(Usuario e) {
        usuarioDAO.insert(e);
    }

    @Override
    public void update(Usuario e) {
        usuarioDAO.update(e);
    }

    @Override
    public void delete(Usuario e) {
        usuarioDAO.delete(e);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioDAO.getAll();
    }

    @Override
    public Usuario getById(long id) {
        return usuarioDAO.getById(id);
    }

    @Override
    public Usuario validateLogIn(String user, String pass) {
        return usuarioDAO.validateLogin(user, pass);
    }

    @Override
    public List<Usuario> getPagination(int pag) {
        return usuarioDAO.getPagination(pag);
    }
}
