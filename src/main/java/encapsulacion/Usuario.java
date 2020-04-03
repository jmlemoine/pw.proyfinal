package encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = "Usuario.findAllUsuario", query = "select u from Usuario u"),
        @NamedQuery(name = "Usuario.findUsuariobyId", query = "select u from Usuario u where u.id = :id"),
        @NamedQuery(name = "Usuario.validateLogIn", query = "select u from Usuario u where u.username = :username and u.password = :pass")})

public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Usuario_ID")
    private long id;
    @Column
    private String username;
    @Column
    private String nombre;
    @Column
    private String password;
    @Column
    private Boolean administrator;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "u")
    private Set<Ruta> rutas;

    public Usuario() {
    }

    public Usuario(long id, String username, String nombre, String password, Boolean administrator) {
        this.setId(id);
        this.setUsername(username);
        this.setNombre(nombre);
        this.setPassword(password);
        this.setAdministrator(administrator);
    }

    public Usuario(String username, String nombre, String password, Boolean administrator) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrator = administrator;
    }

    public Set<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(Set<Ruta> rutas) {
        if (this.rutas == null)
            this.rutas = new HashSet<>();

        this.rutas = rutas;
    }

    public void setRutas(Ruta rutas) {
        if (this.rutas == null)
            this.rutas = new HashSet<>();

        this.rutas.add(rutas);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }
}
