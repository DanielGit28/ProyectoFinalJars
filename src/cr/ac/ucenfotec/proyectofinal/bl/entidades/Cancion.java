package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.2
 */
public class Cancion {
    private int id;
    private String nombreCancion;
    private Artista artistaCancion;
    private Compositor compositorCancion;
    private LocalDate fechaLanzamientoCancion;
    private Genero generoCancion;
    private int cancionSimple;//1 si es un sencillo, 2 si es de un álbum
    private int cancionCompra;//1 si es normal, 2 si es compra
    private int precioCancion;
    private Album albumCancion;
    private String recurso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artista getArtistaCancion() {
        return artistaCancion;
    }

    public void setArtistaCancion(Artista artistaCancion) {
        this.artistaCancion = artistaCancion;
    }

    public Compositor getCompositorCancion() {
        return compositorCancion;
    }

    public void setCompositorCancion(Compositor compositorCancion) {
        this.compositorCancion = compositorCancion;
    }

    public LocalDate getFechaLanzamientoCancion() {
        return fechaLanzamientoCancion;
    }

    public void setFechaLanzamientoCancion(LocalDate fechaLanzamientoCancion) {
        this.fechaLanzamientoCancion = fechaLanzamientoCancion;
    }

    public Genero getGeneroCancion() {
        return generoCancion;
    }

    public void setGeneroCancion(Genero generoCancion) {
        this.generoCancion = generoCancion;
    }

    public int getCancionSimple() {
        return cancionSimple;
    }

    public void setCancionSimple(int cancionSimple) {
        this.cancionSimple = cancionSimple;
    }

    public int getCancionCompra() {
        return cancionCompra;
    }

    public void setCancionCompra(int cancionCompra) {
        this.cancionCompra = cancionCompra;
    }

    public Album getAlbumCancion() {
        return albumCancion;
    }

    public void setAlbumCancion(Album albumCancion) {
        this.albumCancion = albumCancion;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public int getPrecioCancion() {
        return precioCancion;
    }

    public void setPrecioCancion(int precioCancion) {
        this.precioCancion = precioCancion;
    }

    public Cancion() {}

    public Cancion(int id, String nombreCancion, Artista artistaCancion, Compositor compositorCancion,
                   LocalDate fechaLanzamientoCancion, Genero generoCancion,
                   int cancionSimple,int cancionCompra, int precioCancion,Album albumCancion, String recurso) {
        this.id = id;
        this.nombreCancion = nombreCancion;
        this.artistaCancion = artistaCancion;
        this.compositorCancion = compositorCancion;
        this.fechaLanzamientoCancion = fechaLanzamientoCancion;
        this.generoCancion = generoCancion;
        this.cancionSimple = cancionSimple;
        this.cancionCompra = cancionCompra;
        this.precioCancion = precioCancion;
        this.albumCancion = albumCancion;
        this.recurso = recurso;

    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", nombreCancion=" + nombreCancion +
                ", artistaCancion=" + artistaCancion +
                ", compositorCancion=" + compositorCancion +
                ", fechaLanzamientoCancion='" + fechaLanzamientoCancion + '\'' +
                ", generoCancion=" + generoCancion +
                ", cancionSimple=" + cancionSimple +
                ", cancionCompra=" + cancionCompra +
                ", precioCancion=" + precioCancion +
                ", albumCancion=" + albumCancion +
                ", recurso=" + recurso +
                '}';
    }

    //Para las tablas
    public String getNombreArtista() {
        return artistaCancion.getNombreArtistico();
    }
    public String getNombreCompositor() {
        return compositorCancion.getNombre();
    }
    public String getNombreGenero() {
        return generoCancion.getNombreGenero();
    }
    public String getNombreAlbum() {
        return albumCancion.getNombreAlbum();
    }

}
