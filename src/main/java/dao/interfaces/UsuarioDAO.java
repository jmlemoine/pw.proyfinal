package dao.interfaces;

import encapsulacion.Usuario;

import java.util.List;

public interface UsuarioDAO {
    ////////
    void insert(Usuario e);

    void update(Usuario e);

    void delete(Usuario e);

    List<Usuario> getAll();

    Usuario getById(long id);

    Usuario validateLogIn(String user, String pass);
    List<Usuario> getPagination(int pag);

}
