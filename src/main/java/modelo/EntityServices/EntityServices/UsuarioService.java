package modelo.EntityServices.EntityServices;

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


    @Override
    public void insert(Usuario e) {

    }

    @Override
    public void update(Usuario e) {

    }

    @Override
    public void delete(Usuario e) {

    }

    @Override
    public List<Usuario> getAll() {
        return null;
    }

    @Override
    public Usuario getByID(long id) {
        return null;
    }

    @Override
    public Usuario validateLogin(String user, String pass) {
        return null;
    }

    @Override
    public List<Usuario> getPagination(int pag) {
        return null;
    }
}
