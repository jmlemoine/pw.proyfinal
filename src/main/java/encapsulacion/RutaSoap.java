package encapsulacion;

public class RutaSoap {

    private String ruta;
    private String redirect;

    public RutaSoap() {

    }

    public RutaSoap(String ruta, String redirect) {
        this.ruta = ruta;
        this.redirect = redirect;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

}
