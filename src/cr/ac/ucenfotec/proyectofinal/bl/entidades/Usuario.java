package cr.ac.ucenfotec.proyectofinal.bl.entidades;
/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */
public abstract class Usuario {
    protected int id;
    protected String avatarUsuario;
    protected String nombre;
    protected String apellidosUsuario;
    protected String correoUsuario;
    protected String contrasennaUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUsuario() {
        return avatarUsuario;
    }

    public void setAvatarUsuario(String avatarUsuario) {
        this.avatarUsuario = avatarUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasennaUsuario() {
        return contrasennaUsuario;
    }

    public void setContrasennaUsuario(String contrasennaUsuario) {
        this.contrasennaUsuario = contrasennaUsuario;
    }

    public Usuario() {}

    public Usuario(int id,String avatarUsuario, String nombre, String apellidosUsuario,
                   String correoUsuario, String contrasennaUsuario) {
        this.id = id;
        this.avatarUsuario = avatarUsuario;
        this.nombre = nombre;
        this.apellidosUsuario = apellidosUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasennaUsuario = contrasennaUsuario;
    }

    /**
     *
     * @return devuelve los atributos del objeto en orden, separados por coma y en formato String
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", avatarUsuario='" + avatarUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", contrasennaUsuario='" + contrasennaUsuario + '\'' +
                '}';
    }


}
