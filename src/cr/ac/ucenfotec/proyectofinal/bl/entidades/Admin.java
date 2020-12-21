package cr.ac.ucenfotec.proyectofinal.bl.entidades;
/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */
public class Admin extends Usuario{
    private String nombreUsuarioAdmin;

    public String getNombreUsuarioAdmin() {
        return nombreUsuarioAdmin;
    }

    public void setNombreUsuarioAdmin(String nombreUsuarioAdmin) {
        this.nombreUsuarioAdmin = nombreUsuarioAdmin;
    }
    public Admin() {}

    public Admin(int id, String avatarUsuario, String nombre, String apellidosUsuario,
                 String correoUsuario, String contrasennaUsuario, String nombreUsuarioAdmin) {
        super(id, avatarUsuario, nombre, apellidosUsuario, correoUsuario, contrasennaUsuario);
        this.nombreUsuarioAdmin = nombreUsuarioAdmin;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Admin{" +
                "nombreUsuarioAdmin='" + nombreUsuarioAdmin + '\'' +
                ", id='" + id + '\'' +
                ", avatarUsuario='" + avatarUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", contrasennaUsuario='" + contrasennaUsuario + '\'' +
                '}';
    }
}
