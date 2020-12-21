package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx;

import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class CtrlRegistroUsuario {
    Gestor gestor = new Gestor();
    private Connection cnx;

    private Scene escenaRegistro;
    private Stage window;
    private String pathImg;

    private ObservableList<String> listaPaises;

    @FXML
    private TextField fieldIdentificacion;

    @FXML
    private TextField fieldCorreo;

    @FXML
    private Button registrarUsuario;

    @FXML
    private ComboBox<String> paisNacimiento;

    @FXML
    private TextField fieldUsuario;

    @FXML
    private DatePicker fechaNacimiento = new DatePicker(LocalDate.now());

    @FXML
    private PasswordField fieldContrasenna;

    @FXML
    private Button inicio;

    @FXML
    private ImageView ivImagen;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private TextField fieldApellidos;

    @FXML
    private Button btnBuscar;


    public void setEscenaRegistro(Scene scene) {
        escenaRegistro = scene;
    }


    /**
     *
     * @param actionEvent abre la escena del registro del usuario
     */
    public void abrirEscenaRegistro(ActionEvent actionEvent) throws SQLException {
        Stage stageRegistro = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageRegistro.setScene(escenaRegistro);
    }


    /**
     * Esta función carga los paises de la BD en el ComboBox paisNacimiento
     * @throws SQLException
     */
    public void cargarPaises() throws SQLException {
        if(paisNacimiento.getItems().size() == 0) {
            gestor.cargarPaisesComboBox(paisNacimiento);
        }
    }

    /**
     *
     * @param event cuando se hace click en el botón de iniciar sesión, se activa el evento
     * @throws IOException
     */
    public void cambiarEscenaInicio(ActionEvent event) throws IOException {
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
     * Este método registra al usuario
     * @throws SQLException
     */
    public void registrarDatosUsuario() throws SQLException {
        String avatar = pathImg;
        String nombre = fiedlNombre.getText();
        String apellidos = fieldApellidos.getText();
        String contrasenna = fieldContrasenna.getText();
        String correo = fieldCorreo.getText();
        LocalDate fechaNac = fechaNacimiento.getValue();
        String pais = String.valueOf(paisNacimiento.getValue());
        //int idPais = gestor.idPais(pais);
        String identificacion = fieldIdentificacion.getText();
        String nombreUsuario = fieldUsuario.getText();

        if (avatar != null && nombre != null && apellidos != null && correo != null && contrasenna != null && fechaNac != null && pais != null && identificacion != null && nombreUsuario != null) {
            if (gestor.verificarContrasenna(contrasenna) == false) {
                gestor.creacionAlertas("Contraseña con el formato incorrecto. Debe incluir mayúscula, minúscula, caracteres especiales y números.");
            } else {
                gestor.agregarUsuario(avatar, nombre, apellidos, correo, contrasenna, fechaNac, pais, identificacion,nombreUsuario);
            }
        } else {
            gestor.creacionAlertas("Todos los campos deben estar rellenos");
        }

        }

}
