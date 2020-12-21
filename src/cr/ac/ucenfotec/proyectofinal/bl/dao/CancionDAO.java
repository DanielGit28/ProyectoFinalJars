package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.0
 */

public class CancionDAO {
    Connection cnx;
    private PreparedStatement cmdInsertar;
    private PreparedStatement queryCancion;

    private final String TEMPLATE_CMD_INSERTAR = "insert into cancion (nombreCancion,idArtistaCancion,idCompositorCancion,fechaLanzamiento,idGeneroCancion,cancionSimple,cancionCompra,precio,idAlbumCancion,recurso)" +
            " values (?,?,?,?,?,?,?,?,?,?)";
    private final String TEMPLATE_QRY_BUSCARCANCIONES = "select * from cancion";

    /**
     * @param conexion conexión de la clase con la base de datos
     */
    public CancionDAO(Connection conexion) {
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryCancion = cnx.prepareStatement(TEMPLATE_QRY_BUSCARCANCIONES);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Admin encontrarPorId(String cedula) {
        return null;
    }

    public List<Cancion> obtenerTodosLasCanciones() throws SQLException {
        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from tcliente");
        ArrayList<Cancion> listaCanciones = new ArrayList<>();
        while (resultado.next()) {
            Cancion leido = new Cancion();
            leido.setNombreCancion(resultado.getString("nombre"));
            //leido.setArtistaCancion(resultado.getString("artista"));
            //leido.setPuntos(resultado.getInt("puntos"));
            listaCanciones.add(leido);
        }
        return listaCanciones;
    }

    /**
     * Actualiza una canción de la BD
     * @param id id de la canción
     * @param nombre nombre de la canción
     * @param idArtista id del artista de la canción
     * @param idCompositor id del compositor de la canción
     * @throws SQLException
     */
    public void actualizarCancion(int id, String nombre, int idArtista, int idCompositor) throws SQLException {
        Statement query = cnx.createStatement();
        query.executeUpdate("update cancion  set nombreCancion = '"+nombre +"', idArtista = "+idArtista+", idCompositor = "+idCompositor);
    }

    /**
     * Elimina una canción de la BD
     * @param idCancion id de la canción
     * @throws SQLException
     */
    public void eliminarCancion(int idCancion) throws SQLException {
        Statement query = cnx.createStatement();
        query.execute("delete from cancion where idCancion = "+idCancion);
    }

    /**
     * Guarda una cancion en la BD
     * @param nuevo objeto cancion que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarCancion(Cancion nuevo) throws SQLException{
        Statement queryBibliotecaAlbum = cnx.createStatement();
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNombreCancion());
            if(nuevo.getArtistaCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from artista where nombreArtistico = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(2,resultado.getInt("idArtista"));
                }
            } else {
                this.cmdInsertar.setInt(2,nuevo.getArtistaCancion().getId());
            }
            if(nuevo.getCompositorCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from compositor where nombre = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(3,resultado.getInt("idCompositor"));
                }
            } else {
                this.cmdInsertar.setInt(3,nuevo.getCompositorCancion().getId());
            }
            this.cmdInsertar.setInt(3, nuevo.getCompositorCancion().getId());
            this.cmdInsertar.setDate(4, Date.valueOf(nuevo.getFechaLanzamientoCancion()));
            this.cmdInsertar.setInt(5, nuevo.getGeneroCancion().getId());
            this.cmdInsertar.setInt(6, nuevo.getCancionSimple());
            this.cmdInsertar.setInt(7, nuevo.getCancionCompra());
            this.cmdInsertar.setInt(8, nuevo.getPrecioCancion());
            if(nuevo.getAlbumCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from album where nombreAlbum = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(9,resultado.getInt("idAlbum"));
                }
            } else {
                this.cmdInsertar.setInt(9, nuevo.getAlbumCancion().getId());
            }
            this.cmdInsertar.setString(10, nuevo.getRecurso());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar la canción");
        }
/*
        if(nuevo.getAlbumCancion() != null) {
            Statement query = cnx.createStatement();
            ResultSet resultado = query.executeQuery("select * from cancion where nombreCancion = '"+nuevo.getNombreCancion()+"'");
            if(resultado.next()) {
                queryBibliotecaAlbum.execute("insert into biblioteca_album (idAlbumBiblioteca,idCancionAlbumBiblioteca) values ("+resultado.getInt("idAlbumCancion")+","+resultado.getInt("idCancion")+")") ;
            } else {
                System.out.println("No se pudieron insertar datos en la biblioteca de album");
            }
        }
 */
    }

    /**
     * Guarda la canción comprada en la biblioteca de canciones del usuario
     * @param usuario usuario que compró la canción
     * @param cancion canción comprada
     * @throws SQLException
     */
    public void guardarCancionBiblioteca(UsuarioFinal usuario, Cancion cancion) throws SQLException {
        Statement query = cnx.createStatement();
        Statement queryBibliotecaAlbum = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from cancion where idCancion = "+cancion.getId());

        if(resultado.next()) {
            queryBibliotecaAlbum.execute("insert into biblioteca_canciones_usuario (idUsuarioBiblioteca,idCancionBiblioteca) values ("+usuario.getId()+","+resultado.getInt("idCancion")+")") ;
        } else {
            System.out.println("No se pudieron insertar datos en la biblioteca de canciones usuario");
        }
    }

    /**
     * Guarda una cancion en la BD de un usuario en específicio
     * @param nuevo objeto cancion que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarCancionUsuario(int idUsuario, Cancion nuevo) throws SQLException{
        Statement queryBibliotecaAlbum = cnx.createStatement();
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNombreCancion());
            if(nuevo.getArtistaCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from artista where nombreArtistico = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(2,resultado.getInt("idArtista"));
                }
            } else {
                this.cmdInsertar.setInt(2,nuevo.getArtistaCancion().getId());
            }
            if(nuevo.getCompositorCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from compositor where nombre = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(3,resultado.getInt("idCompositor"));
                }
            } else {
                this.cmdInsertar.setInt(3,nuevo.getCompositorCancion().getId());
            }
            this.cmdInsertar.setInt(3, nuevo.getCompositorCancion().getId());
            this.cmdInsertar.setDate(4, Date.valueOf(nuevo.getFechaLanzamientoCancion()));
            this.cmdInsertar.setInt(5, nuevo.getGeneroCancion().getId());
            this.cmdInsertar.setInt(6, nuevo.getCancionSimple());
            this.cmdInsertar.setInt(7, 1);
            this.cmdInsertar.setInt(8, 0);
            if(nuevo.getAlbumCancion() == null) {
                Statement query = cnx.createStatement();
                ResultSet resultado = query.executeQuery("select * from album where nombreAlbum = 'Default'");
                if(resultado.next()) {
                    this.cmdInsertar.setInt(9,resultado.getInt("idAlbum"));
                }
            } else {
                this.cmdInsertar.setInt(9, nuevo.getAlbumCancion().getId());
            }
            this.cmdInsertar.setString(10, nuevo.getRecurso());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar la canción");
        }

        Statement query = cnx.createStatement();
        ResultSet resultado = query.executeQuery("select * from cancion where nombreCancion = '"+nuevo.getNombreCancion()+"'");
        if(resultado.next()) {
            queryBibliotecaAlbum.execute("insert into biblioteca_canciones_usuario (idUsuarioBiblioteca,idCancionBiblioteca) values ("+idUsuario+","+resultado.getInt("idCancion")+")") ;
        } else {
            System.out.println("No se pudieron insertar datos en la biblioteca de canciones usuario");
        }

    }

}
