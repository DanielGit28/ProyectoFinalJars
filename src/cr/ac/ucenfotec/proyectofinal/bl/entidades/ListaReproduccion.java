package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.4
 */

public class ListaReproduccion {
    private int id;
    private ArrayList<Cancion> cancionesListaReproduccion;
    private LocalDate fechaCreacionListaReproduccion;
    private String nombreListaReproduccion;
    private int calificacionReproduccion;
    private UsuarioFinal autorLista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cancion> getCancionesListaReproduccion() {
        return cancionesListaReproduccion;
    }

    public void setCancionesListaReproduccion(ArrayList<Cancion> cancionesListaReproduccion) {
        this.cancionesListaReproduccion = cancionesListaReproduccion;
    }

    public LocalDate getFechaCreacionListaReproduccion() {
        return fechaCreacionListaReproduccion;
    }

    public void setFechaCreacionListaReproduccion(LocalDate fechaCreacionListaReproduccion) {
        this.fechaCreacionListaReproduccion = fechaCreacionListaReproduccion;
    }

    public String getNombreListaReproduccion() {
        return nombreListaReproduccion;
    }

    public void setNombreListaReproduccion(String nombreListaReproduccion) {
        this.nombreListaReproduccion = nombreListaReproduccion;
    }

    public int getCalificacionReproduccion() {
        return calificacionReproduccion;
    }

    public void setCalificacionReproduccion(int calificacionReproduccion) {
        this.calificacionReproduccion = calificacionReproduccion;
    }

    public UsuarioFinal getAutorLista() {
        return autorLista;
    }

    public void setAutorLista(UsuarioFinal autorLista) {
        this.autorLista = autorLista;
    }

    public ListaReproduccion(){}

    public ListaReproduccion(int id, ArrayList<Cancion> cancionesListaReproduccion,
                             LocalDate fechaCreacionListaReproduccion,
                             String nombreListaReproduccion,
                             int calificacionReproduccion, UsuarioFinal usuario) {
        this.id = id;
        this.cancionesListaReproduccion = cancionesListaReproduccion;
        this.fechaCreacionListaReproduccion = fechaCreacionListaReproduccion;
        this.nombreListaReproduccion = nombreListaReproduccion;
        this.calificacionReproduccion = calificacionReproduccion;
        this.autorLista = usuario;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "ListaReproduccion{" +
                "id=" + id +
                ", cancionesListaReproduccion=" + cancionesListaReproduccion +
                ", fechaCreacionListaReproduccion='" + fechaCreacionListaReproduccion + '\'' +
                ", nombreListaReproduccion='" + nombreListaReproduccion + '\'' +
                ", calificacionReproduccion='" + calificacionReproduccion + '\'' +
                ", usuarioFinal='" + autorLista + '\'' +
                '}';
    }

    public String getNombreUsuario() {
        return autorLista.getNombreUsuario();
    }

}
