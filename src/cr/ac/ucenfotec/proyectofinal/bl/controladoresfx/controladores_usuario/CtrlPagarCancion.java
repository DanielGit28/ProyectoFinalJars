package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.MetodoPago;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class CtrlPagarCancion implements Initializable {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private TextField fieldNombreCancion;

    @FXML
    private TextField fieldPrecioCancion;

    @FXML
    private Button btnComprarCancion;

    @FXML
    private DatePicker fieldFechaVenc;

    @FXML
    private TextField fieldNumeroTarjeta;

    @FXML
    private ComboBox<String> fieldTarjeta;


    UsuarioFinal usuario;
    MetodoPago metodoPagoUtilizado;
    Cancion cancionSeleccionada;



    /**
     * Cierra la venta previa cuando se inicializa el evento de la ventana nueva
     * @param event evento que se inicializa para abrir otra ventana
     * @param btn boton que genera el evento
     */
    @FXML
    public void CloseAction(ActionEvent event, Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }


    /**
     * Establece el usuario que el controlador va a utilizar para comprar la cancion
     * @param usuarioSes usuario que inicio sesión
     * @param cancionSelected cancion que se va a comprar
     */
    public void setUsuarioSesion(UsuarioFinal usuarioSes, Cancion cancionSelected) {
        this.usuario = usuarioSes;
        this.cancionSeleccionada = cancionSelected;

        fieldNombreCancion.setText(cancionSeleccionada.getNombreCancion());
        fieldPrecioCancion.setText(String.valueOf(cancionSeleccionada.getPrecioCancion()));
        //System.out.println(usuario.toString());

    }


    /**
     * Esta función carga los paises de la BD en el ComboBox paisNacimiento
     * @throws SQLException
     */
    public void cargarTarjetas() throws SQLException {
        if(fieldTarjeta.getItems().size() == 0) {
            gestor.cargarTarjetasComboBox(fieldTarjeta, usuario.getId());
            if(fieldTarjeta.getSelectionModel().getSelectedItem() != null) {
                cargarDatosTarjeta(usuario.getId(), fieldTarjeta.getValue());
            }
        }
    }

    public void cargarDatosTarjeta(int idUsuario, String numeroTarjeta) throws SQLException {
        MetodoPago tarjeta = gestor.getMetodoPagoByIdUsuario(idUsuario, numeroTarjeta);
        //metodoPagoUtilizado = tarjeta;
        if (tarjeta == null) {
            System.out.println("No hay tarjeta");
        } else {
            fieldNumeroTarjeta.setText(String.valueOf(tarjeta.getNumeroTarjeta()));
            fieldFechaVenc.setValue(tarjeta.getFechaVencimiento());
        }

    }

    /**
     * Compra la canción seleccionada y la guarda en la biblioteca del usuario respectivo
     * @param event evento del botón
     */
    public void comprarCancion(ActionEvent event) throws SQLException {
        if(fieldTarjeta.getValue() == null) {
            gestor.creacionAlertas("Debe seleccionar una tarjeta para pagar primero");
        } else {
            gestor.guardarCancionUsuarioBiblioteca(usuario,cancionSeleccionada);
            CloseAction(event, btnComprarCancion);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldTarjeta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    cargarDatosTarjeta(usuario.getId(), fieldTarjeta.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }
}
