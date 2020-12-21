package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Album;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.1
 */

public class AlbumDAO {
    Connection cnx;
    private PreparedStatement cmdInsertar;
    private PreparedStatement queryAlbum;

    private final String TEMPLATE_CMD_INSERTAR = "insert into album (nombreAlbum,fechaLanzamiento,idArtistaAlbum,imagenAlbum)" +
            " values (?,?,?,?)";
    private final String TEMPLATE_QRY_TODOSLOSALBUMES = "select * from album";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public AlbumDAO(Connection conexion) throws SQLException {
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryAlbum = cnx.prepareStatement(TEMPLATE_QRY_TODOSLOSALBUMES);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Admin encontrarPorId(String cedula){
        return null;
    }

    public ArrayList<Album> obtenerAlbumes() throws SQLException {

        ResultSet resultado = queryAlbum.executeQuery();
        ArrayList<Album> listaAlbumes = new ArrayList<>();
        while (resultado.next()){
            Album leido = new Album();
            //leido.setAvatarUsuario(resultado.getString("avatar"));
            //leido.setNombre(resultado.getString("nombre"));
            //leido.setPuntos(resultado.getInt("puntos"));
            listaAlbumes.add(leido);
        }
        return listaAlbumes;
    }

    /**
     * Elimina un Álbum en la BD
     * @param idAlbum id del álbum que se va a eliminar
     * @throws SQLException
     */
    public void eliminarAlbum(int idAlbum) throws SQLException {
        Statement query = cnx.createStatement();
        query.execute("delete from album where idCompositor = "+idAlbum);
    }

    /**
     * Guarda las canciones del álbum en una tabla Biblioteca en la BD
     * @param nuevo Album con el ArrayList de canciones para guardar
     * @throws SQLException
     */
    public void agregarCancionesBiblioteca(Album nuevo) throws SQLException {
        Statement queryAlbumes = cnx.createStatement();
        Statement queryBiblioteca = cnx.createStatement();
        ResultSet resultadoAlbumes = queryAlbumes.executeQuery("select * from album where nombreAlbum = '"+nuevo.getNombreAlbum()+"'");
        ResultSet resultadoBiblioteca;

        //ESTE FOR AÑADE CADA CANCION DEL ARRAYLIST DE CANCIONES DEL ALBUM, A LA TABLA BIBLIOTECA_ALBUM
        for(Cancion cancion: nuevo.getCancionesAlbum()) {
            resultadoBiblioteca = queryBiblioteca.executeQuery("select * from biblioteca_album where idCancionAlbumBiblioteca = "+cancion.getId());
            if(resultadoAlbumes.next()) {
                Statement query = cnx.createStatement();
                if(resultadoBiblioteca.next()) {
                    System.out.println("No se puede agregar la canción a la biblioteca porque ya está repetida");
                } else {
                    query.execute("insert into biblioteca_album (idAlbumBiblioteca, idCancionAlbumBiblioteca) values ("+resultadoAlbumes.getInt("idBibliotecaAlbum")+","+cancion.getId()+")");
                }
            } else {
                System.out.println("No se pueden agregar canciones a biblioteca");
            }
        }
    }

    /**
     *
     * @param nuevo objeto album que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarAlbum(Album nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNombreAlbum());
            this.cmdInsertar.setDate(2, Date.valueOf(nuevo.getFechaLanzamiento()));
            this.cmdInsertar.setInt(3, nuevo.getArtistaAlbum().getId());
            this.cmdInsertar.setString(4, nuevo.getImagenAlbum());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el album");
        }

    }


}
