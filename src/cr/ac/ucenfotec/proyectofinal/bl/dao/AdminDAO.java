package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;

import java.sql.*;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class AdminDAO {
    Connection cnx;
    private PreparedStatement cmdInsertar;
    private PreparedStatement queryAdmin;

    private final String TEMPLATE_CMD_INSERTAR = "insert into admin(avatar,nombre,apellidos,correo,contrasenna,nombreUsuario)" +
            " values (?,?,?,?,?,?)";
    private final String TEMPLATE_QRY_BUSCARADMIN = "select * from admin";


    /**
     *
     * @param conexion realiza la conexión de la clase con la base
     */
    public AdminDAO(Connection conexion){
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryAdmin = cnx.prepareStatement(TEMPLATE_QRY_BUSCARADMIN);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
/*
    public Admin encontrarPorId(String cedula){
        return null;
    }

    public Admin obtenerAdmin() throws SQLException {
        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from admin");

        Admin leido = new Admin();
        leido.setAvatarUsuario(resultado.getString("avatar"));
        leido.setNombre(resultado.getString("nombre"));
        leido.setAvatarUsuario(resultado.getString("apellidos"));
        leido.setNombre(resultado.getString("correo"));
        leido.setAvatarUsuario(resultado.getString("contrasenna"));
        leido.setNombre(resultado.getString("nombreUsuario"));
            //leido.setPuntos(resultado.getInt("puntos"));

        return leido;
    }*/

    /**
     *
     * @param nuevo administrador que se va a guardar
     * @throws SQLException
     */
    public void guardarAdmin(Admin nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            String pathImg = nuevo.getAvatarUsuario().replace("\\", "\\\\");
            this.cmdInsertar.setString(1,pathImg);
            this.cmdInsertar.setString(2,nuevo.getNombre());
            this.cmdInsertar.setString(3, nuevo.getApellidosUsuario());
            this.cmdInsertar.setString(4, nuevo.getCorreoUsuario());
            this.cmdInsertar.setString(5, nuevo.getContrasennaUsuario());
            this.cmdInsertar.setString(6, nuevo.getNombreUsuarioAdmin());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el admin");
        }
    }

    /**
     * Actualiza los datos del administrador
     * @param avatar Path del avatar
     * @param nombre Nombre actualizado del admin
     * @param apellidos Apellidos actualizados del admin
     * @param nombreUsuario nombre de usuario actualizado del admin
     * @throws SQLException
     */
    public void actualizarDatosAdmin (String avatar, String nombre, String apellidos, String nombreUsuario) throws SQLException {
        Statement query = cnx.createStatement();
        String path = avatar.replace("\\", "\\\\");
        //System.out.println(path+" avatar en dao");
        query.executeUpdate("update admin set avatar = '"+path+"', nombre = '"+nombre+"', apellidos = '"+apellidos+"', nombreUsuario = '"+nombreUsuario+"'  where idAdmin = 1");
    }

}
