package encapsulacion;


import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({@NamedQuery(name = "Visita.findAllVisita", query = "select v from Visita v"),
        @NamedQuery(name = "Visita.findVisitaById", query = "select v from Visita v where v.id = :id"),
        @NamedQuery(name = "Visita.findAllVisitaByRutaId", query = "select v from Visita v join Ruta r on r.id = :id where  v.ruta.id = :id")})
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column
    private String navegador;
    @Column
    private String ip;
    @Column
    private Date fecha;
    @ManyToOne(fetch=FetchType.EAGER)
    private Ruta ruta;

    public Visita(){

    }

    public Visita(String navegador, String ip, Date fecha, Ruta ruta) {
        this.navegador = navegador;
        this.ip = ip;
        this.fecha = fecha;
        this.ruta = ruta;
    }
    public Visita(Long id, String navegador, String ip, Date fecha, Ruta ruta) {
        this.id = id;
        this.navegador = navegador;
        this.ip = ip;
        this.fecha = fecha;
        this.ruta = ruta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNavegador() {
        return navegador;
    }

    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}
