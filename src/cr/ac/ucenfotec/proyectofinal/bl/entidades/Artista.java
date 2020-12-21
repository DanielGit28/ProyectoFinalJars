package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.2
 */
public class Artista {
    private int id;
    private String nombreArtista;
    private String apellidoArtista;
    private String nombreArtistico;
    private LocalDate fechaNacimientoArtista;
    private LocalDate fechaFallecimientoArtista;
    private Pais paisNacimiento;
    private Genero generoMusicalArtista;
    private int edadArtista;
    private String descripcionArtista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getApellidoArtista() {
        return apellidoArtista;
    }

    public void setApellidoArtista(String apellidoArtista) {
        this.apellidoArtista = apellidoArtista;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public LocalDate getFechaNacimientoArtista() {
        return fechaNacimientoArtista;
    }

    public void setFechaNacimientoArtista(LocalDate fechaNacimientoArtista) {
        this.fechaNacimientoArtista = fechaNacimientoArtista;
    }

    public LocalDate getFechaFallecimientoArtista() {
        return fechaFallecimientoArtista;
    }

    public void setFechaFallecimientoArtista(LocalDate fechaFallecimientoArtista) {
        this.fechaFallecimientoArtista = fechaFallecimientoArtista;
    }

    public Pais getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(Pais paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public Genero getGeneroMusicalArtista() {
        return generoMusicalArtista;
    }

    public void setGeneroMusicalArtista(Genero generoMusicalArtista) {
        this.generoMusicalArtista = generoMusicalArtista;
    }

    public int getEdadArtista() {
        return edadArtista;
    }

    public void setEdadArtista(int edadArtista) {
        this.edadArtista = edadArtista;
    }

    public String getDescripcionArtista() {
        return descripcionArtista;
    }

    public void setDescripcionArtista(String descripcionArtista) {
        this.descripcionArtista = descripcionArtista;
    }

    public Artista(){}

    public Artista(int id, String nombreArtista, String apellidoArtista,
                   String nombreArtistico, LocalDate fechaNacimientoArtista,
                   LocalDate fechaFallecimientoArtista, Pais paisNacimiento,
                   Genero generoMusicalArtista, int edadArtista,
                   String descripcionArtista) {

        this.id = id;
        this.nombreArtista = nombreArtista;
        this.apellidoArtista = apellidoArtista;
        this.nombreArtistico = nombreArtistico;
        this.fechaNacimientoArtista = fechaNacimientoArtista;
        this.fechaFallecimientoArtista = fechaFallecimientoArtista;
        this.paisNacimiento = paisNacimiento;
        this.generoMusicalArtista = generoMusicalArtista;
        this.edadArtista = edadArtista;
        this.descripcionArtista = descripcionArtista;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id='" + id + '\'' +
                ", nombreArtista='" + nombreArtista + '\'' +
                ", apellidoArtista='" + apellidoArtista + '\'' +
                ", nombreArtistico='" + nombreArtistico + '\'' +
                ", fechaNacimientoArtista='" + fechaNacimientoArtista + '\'' +
                ", fechaFallecimientoArtista='" + fechaFallecimientoArtista + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", generoMusicalArtista=" + generoMusicalArtista +
                ", edadArtista=" + edadArtista +
                ", descripcionArtista='" + descripcionArtista + '\'' +
                '}';
    }

    //Para las tablas
    public String getNombrePais() {
        return paisNacimiento.getNombrePais();
    }
    public String getNombreGenero() {
        return generoMusicalArtista.getNombreGenero();
    }
}
