package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Artista;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Genero;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.1
 */

public class ArtistaDAO {
    Connection cnx;
    private PreparedStatement cmdInsertar;
    private PreparedStatement queryArtista;

    private final String TEMPLATE_CMD_INSERTAR = "insert into artista (nombre,apellido,nombreArtistico,fechaNacimiento,fechaFallecimiento,idPaisArtista,idGeneroArtista,edadArtista,descripcion)" +
            " values (?,?,?,?,?,?,?,?,?)";
    private final String TEMPLATE_QRY_BUSCARARTISTAS = "select * from artista";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public ArtistaDAO(Connection conexion) throws SQLException {
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryArtista = cnx.prepareStatement(TEMPLATE_QRY_BUSCARARTISTAS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Admin encontrarPorId(String cedula){
        return null;
    }

    public List<Artista> obtenerTodosLosArtistas() throws SQLException {
        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from tcliente");
        ArrayList<Artista> listaArtistas = new ArrayList<>();
        while (resultado.next()){
            Artista leido = new Artista();
            leido.setNombreArtista(resultado.getString("nombre"));
            leido.setApellidoArtista(resultado.getString("apellido"));
            leido.setNombreArtistico(resultado.getString("nombreArtistico"));
            leido.setFechaNacimientoArtista(resultado.getDate("fechaNacimiento").toLocalDate());
            leido.setFechaFallecimientoArtista(resultado.getDate("fechaFallecimiento").toLocalDate());
            //leido.setPaisNacimiento(resultado.getString("paisNacimiento"));
            //leido.setGeneroMusicalArtista(resultado.getString("generoMusical"));
            leido.setEdadArtista(resultado.getInt("edadArtista"));
            leido.setDescripcionArtista(resultado.getString("descripcion"));

            //leido.setPuntos(resultado.getInt("puntos"));
            listaArtistas.add(leido);
        }
        return listaArtistas;
    }

    /**
     * Actualiza solo ciertos atributos del artista en la BD
     * @param id idArtista
     * @param nombre nombre Artista
     * @param apellidos apellidos Artista
     * @param nomArt nombre artístico del Artista
     * @param fechaFall fecha fallecimiento del Artista
     * @param idPais id del país de nacimiento
     * @param descripcion descripción del artista
     * @throws SQLException
     */
    public void actualizarArtista(int id, String nombre, String apellidos, String nomArt, LocalDate fechaFall, int idPais, String descripcion) throws SQLException {
        Statement query = cnx.createStatement();
        query.executeUpdate("update artista  set nombre = '"+nombre +"', apellido = '"+apellidos+"', nombreArtistico = '"+nomArt +"', fechaFallecimiento = "+Date.valueOf(fechaFall)+"', idPaisArtista = "+idPais +"', descripcion = '"+descripcion+"'  where idArtista = " + id);
    }

    /**
     * Elimina el artista recibido en la BD
     * @param idArtista id del Artista que se eliminara
     * @throws SQLException
     */
    public void eliminarArtista(int idArtista) throws SQLException {
        Statement query = cnx.createStatement();
        query.execute("delete from artista where idArtista = "+idArtista);
    }


    /**
     *
     * @param nuevo objeto artista que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarArtista(Artista nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            Date date = null;
            this.cmdInsertar.setString(1,nuevo.getNombreArtista());
            this.cmdInsertar.setString(2,nuevo.getApellidoArtista());
            this.cmdInsertar.setString(3, nuevo.getNombreArtistico());
            this.cmdInsertar.setDate(4, Date.valueOf(nuevo.getFechaNacimientoArtista()));
            if(nuevo.getFechaFallecimientoArtista() == null) {
                this.cmdInsertar.setDate(5, date);
            } else {
                this.cmdInsertar.setDate(5, Date.valueOf(nuevo.getFechaFallecimientoArtista()));
            }
            this.cmdInsertar.setInt(6, nuevo.getPaisNacimiento().getIdPais());
            this.cmdInsertar.setInt(7, nuevo.getGeneroMusicalArtista().getId());
            this.cmdInsertar.setInt(8, nuevo.getEdadArtista());
            this.cmdInsertar.setString(9, nuevo.getDescripcionArtista());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el artista");
        }
    }
}
