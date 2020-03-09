package encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({@NamedQuery(name = "Ruta.findAllURL", query = "select r from Ruta r order by r.id desc "),
        @NamedQuery(name = "Ruta.findURLbyUserId", query = "select r from Ruta r where r.usuario.id = :userid order by r.id desc "),
        @NamedQuery(name = "Ruta.findURLbyId", query = "select r from Ruta r where r.id = :id order by r.id desc "),
        @NamedQuery(name = "Ruta.findNulls", query = "select r from Ruta r where r.usuario is null order by r.id desc "),
        @NamedQuery(name = "Ruta.findURLbyRutaAcortada", query = "select r from Ruta r where r.ruta_acortada = :rutaacortada order by r.id desc ")})

public class Ruta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column
    private String ruta;
    @Column
    private String ruta_acortada;
    @ManyToOne
    private Usuario usuario;

    public Ruta() {
    }

    public Ruta(String ruta, String ruta_acortada, Usuario usuario) {
        this.ruta = ruta;
        this.ruta_acortada = ruta_acortada;
        this.usuario = usuario;
    }

    public Ruta(Long id, String ruta, String ruta_acortada, Usuario usuario) {
        this.id = id;
        this.ruta = ruta;
        this.ruta_acortada = ruta_acortada;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta_acortada() {
        return ruta_acortada;
    }

    public void setRuta_acortada(String ruta_acortada) {
        this.ruta_acortada = ruta_acortada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

