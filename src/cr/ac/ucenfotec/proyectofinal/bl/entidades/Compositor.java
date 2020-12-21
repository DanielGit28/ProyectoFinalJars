package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.2
 */
public class Compositor {
    private int id;
    private String nombre;
    private String apellidos;
    private Pais paisNacimientoCompositor;
    private LocalDate fechaNacimientoCompositor;
    private int edadCompositor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Pais getPaisNacimientoCompositor() {
        return paisNacimientoCompositor;
    }

    public void setPaisNacimientoCompositor(Pais paisNacimientoCompositor) {
        this.paisNacimientoCompositor = paisNacimientoCompositor;
    }

    public LocalDate getFechaNacimientoCompositor() {
        return fechaNacimientoCompositor;
    }

    public void setFechaNacimientoCompositor(LocalDate fechaNacimientoCompositor) {
        this.fechaNacimientoCompositor = fechaNacimientoCompositor;
    }

    public int  getEdadCompositor() {
        return edadCompositor;
    }

    public void setEdadCompositor(int   edadCompositor) {
        this.edadCompositor = edadCompositor;
    }

    public Compositor() {}

    public Compositor(int id, String nombre, String apellidos, Pais paisNacimientoCompositor,
                      LocalDate fechaNacimientoCompositor, int edadCompositor) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.paisNacimientoCompositor = paisNacimientoCompositor;
        this.fechaNacimientoCompositor = fechaNacimientoCompositor;
        this.edadCompositor = edadCompositor;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Compositores{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", paisNacimientoCompositor='" + paisNacimientoCompositor + '\'' +
                ", fechaNacimientoCompositor='" + fechaNacimientoCompositor + '\'' +
                ", edadCompositor='" + edadCompositor + '\'' +
                '}';
    }

    //Para las tablas
    public String getNombrePais() {
        return paisNacimientoCompositor.getNombrePais();
    }

}
