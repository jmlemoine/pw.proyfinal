package dao.interfaces;

import encapsulacion.Ruta;

import java.util.List;

public interface RutaDAO {
    ////////
    void insert(Ruta e);

    void update(Ruta e);

    void delete(Ruta e);

    List<Ruta> getAll();

    Ruta getById(long id);

    List<Ruta> getByUser(long user_id);

    Ruta generateShortLink(String ruta);

    Ruta getByRutaAcortada(String rutaAcortada);

    List<Ruta> getNulls();

    List<Ruta> getPagination(int pag, long id);

    int cantPag(long id);

    int cantPagNulls();

    List<Ruta>getNullsPagination(int pag);
    List<Ruta>getPagAll(int pag);

}
