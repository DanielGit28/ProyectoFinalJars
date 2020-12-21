package cr.ac.ucenfotec.proyectofinal.bl.entidades;
/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */
public class Pais {
    private int idPais;
    private String codigoPais;
    private String nombrePais;


    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public Pais(){}

    public Pais(int idPais, String codigoPais, String nombrePais) {
        this.idPais = idPais;
        this.codigoPais = codigoPais;
        this.nombrePais = nombrePais;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "idPais=" + idPais +
                ", codigoPais='" + codigoPais + '\'' +
                ", nombrePais='" + nombrePais + '\'' +
                '}';
    }

    public String toString2() {
        return "nombrePais= '"+nombrePais +'\'';
    }
}