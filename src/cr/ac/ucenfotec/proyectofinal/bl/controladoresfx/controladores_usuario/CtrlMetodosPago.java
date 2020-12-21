package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.MetodoPago;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class CtrlMetodosPago implements Initializable {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    private String pathImg;

    @FXML
    private Button cerrarSesion;

    @FXML
    private Button btnListaReproduccion;

    @FXML
    private TextField fieldNumeroTarjetaRegistrar;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button bntMetodosPago;

    @FXML
    private PasswordField fieldCodigoSeguridad;

    @FXML
    private Button btnRegistrarTarjeta;

    @FXML
    private Button btnSubirCancion;

    @FXML
    private DatePicker fieldFechaVenc;

    @FXML
    private Button btnAlbum;

    @FXML
    private DatePicker fieldFechaVencRegistrar;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnComprarCancion;

    @FXML
    private Button btnEliminarTarjeta;

    @FXML
    private TextField fieldNumeroTarjeta;

    @FXML
    private ComboBox<String> fieldTarjeta;


    UsuarioFinal usuario;



    /**
     * Este método carga el escenario de cuenta de usuario como inicio
     * @param event evento que se genera cuando se aprieta el botón de inicio
     * @throws IOException
     */
    public void escenaInicio(ActionEvent event) throws IOException {
        gestor.escenarioInicioUsuario(event, window, usuario);
        CloseAction(event,btnInicio);
    }

    /**
     * Este método carga el escenario de canciones
     * @param event evento que se genera cuando se aprieta el botón de canciones
     * @throws IOException
     */
    public void escenaCanciones(ActionEvent event) throws IOException {
        gestor.escenarioCancionesUsuario(event, window, usuario);
        CloseAction(event, btnCanciones);
    }

    /**
     * Este método carga el escenario de compositores
     * @param event evento que se genera cuando se aprieta el botón de compositores
     * @throws IOException
     */
    public void escenaSubirCanciones(ActionEvent event) throws IOException {
        gestor.escenarioSubirCancionesUsuario(event, window, usuario);
        CloseAction(event, btnSubirCancion);
    }

    /**
     * Este método carga el escenario de artistas
     * @param event evento que se genera cuando se aprieta el botón de artistas
     * @throws IOException
     */
    public void escenaComprarCancion(ActionEvent event) throws IOException {
        gestor.escenarioComprarCancionUsuario(event, window, usuario);
        CloseAction(event, btnComprarCancion);
    }

    /**
     * Este método carga el escenario de géneros
     * @param event evento que se genera cuando se aprieta el botón de géneros
     * @throws IOException
     */
    public void escenaListasReproduccion(ActionEvent event) throws IOException {
        gestor.escenarioListasReproduccionUsuario(event,window,usuario);
        CloseAction(event, btnListaReproduccion);
    }

    /**
     * Este método carga el escenario de álbumes
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void escenaAlbumes(ActionEvent event) throws IOException {
        gestor.escenarioAlbumesUsuario(event, window, usuario);
        CloseAction(event, btnAlbum);
    }

    /**
     * Este método carga el escenario de métodos de pago del usuario
     * @param event evento que se genera cuando se aprieta el botón de métodos de pago
     * @throws IOException
     */
    public void escenaMetodosPago(ActionEvent event) throws IOException {
        gestor.escenarioMetodosPago(event, window, usuario);
        CloseAction(event, bntMetodosPago);
    }

    /**
     * Este método devuelve al escenario inicial
     * @param event evento que se genera cuando se aprieta el botón de cerrarSesion
     * @throws IOException
     */
    public void cerrarSesion(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/login.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

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
     * Establece el usuario que el controlador va a utilizar para manejar la aplicación
     * @param usuarioSes usuario que inicio sesión
     */
    public void setUsuarioSesion(UsuarioFinal usuarioSes) {
        this.usuario = usuarioSes;
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

    /**
     * Registra el método de pago en la BD
     * @throws SQLException
     */
    public void registrarTarjeta() throws SQLException {
        String tarjeta = fieldNumeroTarjetaRegistrar.getText();
        String codigo = fieldCodigoSeguridad.getText();

        if(fieldNumeroTarjetaRegistrar != null && fieldFechaVencRegistrar != null && fieldCodigoSeguridad != null) {
            gestor.registrarMetodoPago(usuario.getId(), tarjeta, fieldFechaVencRegistrar.getValue(), codigo);
        }
    }

    public void cargarDatosTarjeta(int idUsuario, String numeroTarjeta) throws SQLException {
        MetodoPago tarjeta = gestor.getMetodoPagoByIdUsuario(idUsuario, numeroTarjeta);
        if (tarjeta == null) {
            System.out.println("No hay tarjeta");
        } else {
            fieldNumeroTarjeta.setText(String.valueOf(tarjeta.getNumeroTarjeta()));
            fieldFechaVenc.setValue(tarjeta.getFechaVencimiento());
        }

    }

    /**
     * Elimina la tarjeta seleccionada
     */
    public void eliminarTarjeta() throws SQLException {
        if(fieldTarjeta.getValue() != null) {
            gestor.eliminarTarjeta(usuario.getId(), fieldTarjeta.getValue());
        } else {
            gestor.creacionAlertas("Debe seleccionar una tarjeta para eliminar");
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
