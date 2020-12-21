package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.1
 */

public class UsuarioFinalDAO {
    Connection cnx;
    private PreparedStatement cmdInsertar;
    private PreparedStatement queryUsuarios;

    private final String TEMPLATE_CMD_INSERTAR = "insert into usuario_final (avatar,nombre,apellidos,correo,contrasenna,fechaNacimiento,idPais,identificacion,nombreUsuario)" +
            " values (?,?,?,?,?,?,?,?,?)";
    private final String TEMPLATE_QRY_TODOSLOSUSUARIOS = "select * from usuario_final";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public UsuarioFinalDAO(Connection conexion){
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryUsuarios = cnx.prepareStatement(TEMPLATE_QRY_TODOSLOSUSUARIOS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Admin encontrarPorId(String cedula){
        return null;
    }

    public List<Admin> obtenerTodosLosClientes() throws SQLException {
        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from tcliente");
        ArrayList<Admin> listaClientes = new ArrayList<>();
        while (resultado.next()){
            Admin leido = new Admin();
            leido.setAvatarUsuario(resultado.getString("avatar"));
            leido.setNombre(resultado.getString("nombre"));
            //leido.setPuntos(resultado.getInt("puntos"));
            listaClientes.add(leido);
        }
        return listaClientes;
    }

    /**
     *
     * @param nuevo objeto UsuarioFinal que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarUsuario(UsuarioFinal nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            String pathImg = nuevo.getAvatarUsuario().replace("\\", "\\\\");
            this.cmdInsertar.setString(1,pathImg);
            this.cmdInsertar.setString(2,nuevo.getNombre());
            this.cmdInsertar.setString(3, nuevo.getApellidosUsuario());
            this.cmdInsertar.setString(4, nuevo.getCorreoUsuario());
            this.cmdInsertar.setString(5, nuevo.getContrasennaUsuario());
            this.cmdInsertar.setDate(6, Date.valueOf(nuevo.getFechaNacimientoUsuario()));
            this.cmdInsertar.setInt(7, nuevo.getPaisProcedenciaUsuario().getIdPais());
            this.cmdInsertar.setString(8, nuevo.getIdentificacionUsuario());
            this.cmdInsertar.setString(9, nuevo.getNombreUsuario());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el cliente");
        }
    }

    /**
     * Actualiza un usuario en la BD
     * @param idUsuario id del usuario que se actualiza
     * @param avatar path de la imagen de perfil del usuario
     * @param nombre nombre del usuario
     * @param apellidos apellidos del usuario
     * @param fechaNac fecha de nacimiento del usuario
     * @param nombreUsuario nombre del usuario en la aplicacion
     * @param idPais país nacimiento del usuario
     * @throws SQLException
     */
    public void actualizarDatosUsuario (int idUsuario,String avatar, String nombre, String apellidos, LocalDate fechaNac, String nombreUsuario, int idPais) throws SQLException {
        Statement query = cnx.createStatement();
        String path = avatar.replace("\\", "\\\\");
        //System.out.println(path+" avatar en dao");
        System.out.println(nombre);
        System.out.println(idUsuario);
        query.executeUpdate("update usuario_final set avatar = '"+path+"', nombre = '"+nombre+"', apellidos = '"+apellidos+"', fechaNacimiento = '"+fechaNac+"', nombreUsuario = '"+nombreUsuario+"', idPais = "+idPais+"  where idusuariofinal = "+idUsuario);
    }

}
