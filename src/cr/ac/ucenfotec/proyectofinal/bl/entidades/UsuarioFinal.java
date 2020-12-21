package cr.ac.ucenfotec.proyectofinal.bl.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.3
 */
public class UsuarioFinal extends Usuario{
    private LocalDate fechaNacimientoUsuario;
    private Pais paisProcedenciaUsuario;
    private String identificacionUsuario;
    private String nombreUsuario;
    private int otp;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ArrayList<Cancion> cancionesUsuario;
    //private int edadUsuario;

    public LocalDate getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(LocalDate fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public Pais getPaisProcedenciaUsuario() {
        return paisProcedenciaUsuario;
    }

    public void setPaisProcedenciaUsuario(Pais paisProcedenciaUsuario) {
        this.paisProcedenciaUsuario = paisProcedenciaUsuario;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public void setListasReproduccion(ListaReproduccion lista) {
        this.listasReproduccion.add(lista);
    }
    public ArrayList<Cancion> getCancionesUsuario() {
        return cancionesUsuario;
    }

    public void setCancionesUsuario(Cancion cancionUsuario) {
        this.cancionesUsuario.add(cancionUsuario);
    }


    public UsuarioFinal() {}

    public UsuarioFinal(int id, String avatarUsuario, String nombre, String apellidosUsuario,
                        String correoUsuario, String contrasennaUsuario,
                        LocalDate fechaNacimientoUsuario, Pais paisProcedenciaUsuario,
                        String identificacionUsuario, String nombreUsuario, int otp,
                        ArrayList<ListaReproduccion> listasReproduccion, ArrayList<Cancion> cancionesUsuario) {
        super(id,avatarUsuario, nombre, apellidosUsuario, correoUsuario, contrasennaUsuario);
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.paisProcedenciaUsuario = paisProcedenciaUsuario;
        this.identificacionUsuario = identificacionUsuario;
        this.nombreUsuario = nombreUsuario;
        this.otp = otp;
        this.listasReproduccion = listasReproduccion;
        this.cancionesUsuario = cancionesUsuario;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "UsuarioFinal{" +
                "fechaNacimientoUsuario='" + fechaNacimientoUsuario + '\'' +
                ", paisProcedenciaUsuario='" + paisProcedenciaUsuario + '\'' +
                ", identificacionUsuario='" + identificacionUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", otp=" + otp +
                ", listasReproduccion=" + listasReproduccion +
                ", cancionesUsuario=" + cancionesUsuario +
                ", id='" + id + '\'' +
                ", avatarUsuario='" + avatarUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", contrasennaUsuario='" + contrasennaUsuario + '\'' +
                '}';
    }
}
