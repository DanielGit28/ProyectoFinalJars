package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.ListaReproduccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @version 1.0
 */

public class ListaReproduccionDAO {
    Connection cnx;

    private PreparedStatement cmdInsertar;
    private PreparedStatement queryListaReproduccion;

    private final String TEMPLATE_CMD_INSERTAR = "insert into lista_reproduccion_usuario (nombreLista,fechaCreacion,calificacion,idUsuarioLista)" +
            " values (?,?,?,?)";
    private final String TEMPLATE_QRY_LISTASREPRODUCCION = "select * from lista_reproduccion_usuario";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public ListaReproduccionDAO(Connection conexion){
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryListaReproduccion = cnx.prepareStatement(TEMPLATE_QRY_LISTASREPRODUCCION);
        }
        catch (SQLException throwables) {
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
     *
     * @param nuevo objeto ListaReproduccion que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarListaReproduccion(ListaReproduccion nuevo) throws SQLException{
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNombreListaReproduccion());
            this.cmdInsertar.setDate(2, Date.valueOf(nuevo.getFechaCreacionListaReproduccion()));
            this.cmdInsertar.setInt(3,nuevo.getCalificacionReproduccion());
            this.cmdInsertar.setInt(4,nuevo.getAutorLista().getId());
            this.cmdInsertar.execute();

            Statement queryLista = cnx.createStatement();
            ResultSet resultadoLista = queryLista.executeQuery("select * from lista_reproduccion_usuario where idUsuarioLista = "+nuevo.getAutorLista().getId()+" and nombreLista = '"+nuevo.getNombreListaReproduccion()+"'");
            if(resultadoLista.next()) {
                for (Cancion cancion: nuevo.getCancionesListaReproduccion()) {
                    Statement queryBiblioteca = cnx.createStatement();
                    queryBiblioteca.execute("insert into biblioteca_lista_reproduccion (idListaReproduccionUsuario,idCancionBibliotecaLista) values("+resultadoLista.getInt("idListaUsuario")+", "+cancion.getId()+")");
                }
            } else {
                System.out.println("No se encontró la lista recién creada");
            }

        } else {
            System.out.println("No se pudo guardar la lista de reproducción");
        }
    }
}
