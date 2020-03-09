package dao.Implementations;

import dao.interfaces.UsuarioDAO;
import encapsulacion.Usuario;
import modelo.EntityServices.utils.CRUD;

import java.util.List;

public class UsuarioDAOImpl extends CRUD<Usuario> implements UsuarioDAO {
//KLK
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
