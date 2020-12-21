package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx;

import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Daniel
 * @version 1.1
 */

public class CtrlRegistroAdmin implements Initializable {
    Gestor gestor = new Gestor();
    private Connection cnx;

    private Scene escenaRegistro;
    private Stage window;
    private String pathImg;

    @FXML
    private TextField lblUsuario;

    @FXML
    private TextField lblNombre;

    @FXML
    private TextField lblCorreo;

    @FXML
    private TextField lblContrasenna;

    @FXML
    private Button registrarAdmin;

    @FXML
    private ImageView ivImagen;

    @FXML
    private TextField lblApellidos;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button inicio;

    public void setEscenaRegistro(Scene scene) {
         escenaRegistro = scene;
    }

    /**
     *
     * @param actionEvent abre la escena del registro del administrador
     */
    public void abrirEscenaRegistro(ActionEvent actionEvent) {
        Stage stageRegistro = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageRegistro.setScene(escenaRegistro);
    }

    /**
     *
     * @param event cuando se hace click en el botón de iniciar sesión, se activa el evento
     * @throws IOException
     */
    public void cambiarEscena(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../vistas/inicio.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga la imagen que se selecciona en el registro
     */
    public void cargarImagen() {
        btnBuscar.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Buscar Imagen");

            // Agregar filtros para facilitar la busqueda
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );

            // Obtener la imagen seleccionada
            File imgFile = fileChooser.showOpenDialog(window);

            // Mostar la imagen
            if (imgFile != null) {
                Image image = new Image("file:" + imgFile.getAbsolutePath());
                ivImagen.setImage(image);
                pathImg = String.valueOf(imgFile);
                //System.out.println("DIR "+pathImg);
            }
        });
    }

    /**
     * Este método registra al administrador
     * @throws SQLException
     */
    public void registrarDatosAdmin() throws SQLException {
        String nombre = lblNombre.getText();
        String apellidos = lblApellidos.getText();
        String contrasenna = lblContrasenna.getText();
        String correo = lblCorreo.getText();
        String nombreUsuario = lblUsuario.getText();
        if(gestor.verificarContrasenna(contrasenna) == false ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Contraseña con el formato incorrecto. Debe incluir mayúscula, minúscula, caracteres especiales y números.");
            alert.showAndWait();
        } else {
            if(pathImg != null && nombre != null && apellidos != null && correo != null && contrasenna != null && nombreUsuario != null) {
                gestor.agregarAdmin(pathImg,nombre,apellidos,correo,contrasenna,nombreUsuario);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Todos los campos deben estar rellenos");
                alert.showAndWait();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
