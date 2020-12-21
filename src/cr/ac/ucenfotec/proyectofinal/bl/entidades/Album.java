package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.2
 */
public class Album {
    private int id;
    private String nombreAlbum;
    private LocalDate fechaLanzamiento;
    private Artista artistaAlbum;
    private String imagenAlbum;
    private ArrayList<Cancion> cancionesAlbum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Artista getArtistaAlbum() {
        return artistaAlbum;
    }

    public void setArtistaAlbum(Artista artistaAlbum) {
        this.artistaAlbum = artistaAlbum;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }

    public void setImagenAlbum(String imagenAlbum) {
        this.imagenAlbum = imagenAlbum;
    }

    public ArrayList<Cancion> getCancionesAlbum() {
        return cancionesAlbum;
    }

    public void setCancionesAlbum(ArrayList<Cancion> cancionesAlbum) {
        this.cancionesAlbum = cancionesAlbum;
    }

    public Album(){}

    public Album(int id, String nombreAlbum, LocalDate fechaLanzamiento, Artista artistaAlbum,
                 String imagenAlbum, ArrayList<Cancion> cancionesAlbum) {
        this.id = id;
        this.nombreAlbum = nombreAlbum;
        this.fechaLanzamiento = fechaLanzamiento;
        this.artistaAlbum = artistaAlbum;
        this.imagenAlbum = imagenAlbum;
        this.cancionesAlbum = cancionesAlbum;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", nombreAlbum='" + nombreAlbum + '\'' +
                ", fechaLanzamiento='" + fechaLanzamiento + '\'' +
                ", artistaAlbum=" + artistaAlbum +
                ", imagenAlbum='" + imagenAlbum + '\'' +
                ", cancionesAlbum=" + cancionesAlbum +
                '}';
    }

    public String getNombreArtista() {
        return artistaAlbum.getNombreArtistico();
    }

}
