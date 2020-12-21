package cr.ac.ucenfotec.proyectofinal.bl.entidades;
/**
 * @author Daniel Zúñiga Rojas
 * @version 1.2
**/
public class Genero {
    private int id;
    private String nombreGenero;
    private String descripcionGenero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public String getDescripcionGenero() {
        return descripcionGenero;
    }

    public void setDescripcionGenero(String descripcionGenero) {
        this.descripcionGenero = descripcionGenero;
    }

    public Genero() {}

    public Genero(int id, String nombreGenero, String descripcionGenero) {
        this.id = id;
        this.nombreGenero = nombreGenero;
        this.descripcionGenero = descripcionGenero;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Generos{" +
                "id='" + id + '\'' +
                ", nombreGenero='" + nombreGenero + '\'' +
                ", descripcionGenero='" + descripcionGenero + '\'' +
                '}';
    }


}
