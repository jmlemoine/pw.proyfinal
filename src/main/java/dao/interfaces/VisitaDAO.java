package dao.interfaces;

import encapsulacion.Visita;

import java.util.List;

public interface VisitaDAO {

    void insert(Visita e);

    void update(Visita e);

    void delete(Visita e);

    List<Visita> getAll();

    Visita getById(long id);

    List<Visita> getByRuta(long ruta_id);

}
