package cr.ac.ucenfotec.proyectofinal.bl.dao;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Genero;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.MetodoPago;

import java.sql.*;

public class MetodoPagoDAO {
    Connection cnx;

    private PreparedStatement cmdInsertar;
    private PreparedStatement queryMetodoPago;

    private final String TEMPLATE_CMD_INSERTAR = "insert into metodo_pago (numeroTarjeta,fechaVencimiento,codigoSeguridad,idClientePago)" +
            " values (?,?,?,?)";
    private final String TEMPLATE_QRY_METODOSPAGO = "select * from metodo_pago";

    /**
     *
     * @param conexion conexión de la clase con la base de datos
     */
    public MetodoPagoDAO(Connection conexion){
        this.cnx = conexion;
        try {
            this.cmdInsertar = cnx.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryMetodoPago = cnx.prepareStatement(TEMPLATE_QRY_METODOSPAGO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Elimina un método de pago de la Base de Datos
     * @param metodoPago Objeto MetodoPago que se eliminara
     * @throws SQLException
     */
    public void eliminarMetodoPago(MetodoPago metodoPago) throws SQLException {
        Statement query = cnx.createStatement();
        query.execute("delete from metodo_pago where numeroTarjeta = '"+metodoPago.getNumeroTarjeta()+"'");
    }

    /**
     * Guarda un método de pago en la base de datos
     * @param nuevo objeto MetodoPago que se va a guardar en la base de datos
     * @throws SQLException
     */
    public void guardarMetodoPago(MetodoPago nuevo) throws SQLException{
        //System.out.println(nuevo.getUsuario().toString());
        if(this.cmdInsertar != null) {
            this.cmdInsertar.setString(1,nuevo.getNumeroTarjeta());
            this.cmdInsertar.setDate(2, Date.valueOf(nuevo.getFechaVencimiento()));
            this.cmdInsertar.setInt(3,nuevo.getCodigoSeguridad());
            this.cmdInsertar.setInt(4,nuevo.getUsuario().getId());
            this.cmdInsertar.execute();
        } else {
            System.out.println("No se pudo guardar el método de pago (tarjeta)");
        }
    }

}
