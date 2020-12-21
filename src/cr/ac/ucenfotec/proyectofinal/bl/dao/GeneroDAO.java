package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.0
 */

public class GeneroDAO {
    Connection cnx;

    private PreparedStatement cmdInsertar;
    private PreparedStatement queryArtista;

    private final String TEMPLATE_CMD_INSERTAR = "insert into genero (nombre,descripcion)" +
            " values (?,?)";
    private final String TEMPLATE_QRY_GENEROS = "select * from genero";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public GeneroDAO(Connection conexion){
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryArtista = cnx.prepareStatement(TEMPLATE_QRY_GENEROS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Admin encontrarPorId(String cedula){
        return null;
    }

    public List<Genero> obtenerTodosLosGeneros() throws SQLException {
        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from tcliente");
        ArrayList<Genero> listaGeneros = new ArrayList<>();
        while (resultado.next()){
            Genero leido = new Genero();
            leido.setNombreGenero(resultado.getString("nombre"));
            leido.setDescripcionGenero(resultado.getString("descripcion"));
            //leido.setPuntos(resultado.getInt("puntos"));
            listaGeneros.add(leido);
        }
        return listaGeneros;
    }

    /**
     * Actualiza el género recibido en la BD
     * @param genero Objeto género que se actualizara
     * @throws SQLException
     */
    public void actualizarGenero(Genero genero) throws SQLException {
        Statement query = cnx.createStatement();
        query.executeUpdate("update genero  set nombre = '"+genero.getNombreGenero() +"', descripcion = '"+ genero.getDescripcionGenero()+"'  where idGenero = " + genero.getId());
    }

    /**
     * Elimina el género recibido como parámetro
     * @param genero Objeto género que se eliminara
     * @throws SQLException
     */
    public void eliminarGenero(Genero genero) throws SQLException {
        Statement query = cnx.createStatement();
        query.execute("delete from genero where idGenero = "+genero.getId());
    }

    /**
     * Guarda un género en la base de datos
     * @param nuevo objeto Genero que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarGenero(Genero nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNombreGenero());
            this.cmdInsertar.setString(2,nuevo.getDescripcionGenero());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el género");
        }
    }
}
