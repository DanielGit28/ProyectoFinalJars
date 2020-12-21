package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Admin;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class CtrlMenuAdmin implements Initializable {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    private String pathImg;

    @FXML
    private Button cerrarSesion;

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Button btnAlbum;

    @FXML
    private ImageView ctnAvatar;

    @FXML
    private TextField lblNombre;

    @FXML
    private TextField lblCorreo;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button btnInicio;

    @FXML
    private TextField lblNombreUsuario;

    @FXML
    private Button btnArtista;

    @FXML
    private Button btnGenero;

    @FXML
    private TextField lblApellidos;

    @FXML
    private Button btnActualizar;


    /**
     *
     * @param actionEvent abre la escena del registro del administrador
     */
    public void abrirEscenaRegistro(ActionEvent actionEvent) throws SQLException {
        Stage stageRegistro = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageRegistro.setScene(escenaRegistro);
    }

    /**
     * Carga la imagen del avatar
     */
    public void cargarImagen() {
        btnSeleccionar.setOnAction(event -> {
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
                ctnAvatar.setImage(image);
                pathImg = String.valueOf(imgFile);
                System.out.println(pathImg);
                //System.out.println("DIR "+pathImg);
            }
        });
    }

    /**
     * Este método carga el escenario de canciones
     * @param event evento que se genera cuando se aprieta el botón de canciones
     * @throws IOException
     */
    public void canciones(ActionEvent event) throws IOException {
        gestor.escenarioCanciones(event, window);
    }

    /**
     * Este método carga el escenario de compositores
     * @param event evento que se genera cuando se aprieta el botón de compositores
     * @throws IOException
     */
    public void compositores(ActionEvent event) throws IOException {
        gestor.escenarioCompositores(event, window);
    }

    /**
     * Este método carga el escenario de artistas
     * @param event evento que se genera cuando se aprieta el botón de artistas
     * @throws IOException
     */
    public void artistas(ActionEvent event) throws IOException {
        gestor.escenarioArtistas(event, window);
    }

    /**
     * Este método carga el escenario de géneros
     * @param event evento que se genera cuando se aprieta el botón de géneros
     * @throws IOException
     */
    public void generos(ActionEvent event) throws IOException {
        gestor.escenarioGeneros(event,window);
    }

    /**
     * Este método carga el escenario de álbumes
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void albumes(ActionEvent event) throws IOException {
        gestor.escenarioAlbumes(event, window);
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
     *      * Carga los labels de la cuenta del admin con los datos de la base de datos
     * @throws SQLException
     */
    public void cargarFields() throws SQLException {
        Admin admin = gestor.getAdmin();
        String avatar = admin.getAvatarUsuario();
        //System.out.println(avatar + ", avatar");
        File file = new File(avatar);
        Image image = new Image(file.toURI().toString());
        //Image image = new Image("file:" + avatar);
        ctnAvatar.setImage(image);
        lblNombre.setText(admin.getNombre());
        lblApellidos.setText(admin.getApellidosUsuario());
        lblCorreo.setText(admin.getCorreoUsuario());
        lblNombreUsuario.setText(admin.getNombreUsuarioAdmin());
    }


    /**
     * Actualiza los datos del admin según los campos de texto
     * @throws SQLException
     */
    public void actualizarAdmin() throws SQLException {
        String nombre = lblNombre.getText();
        String apellidos = lblApellidos.getText();
        String nombreUsuario = lblNombreUsuario.getText();

        gestor.actualizarAdmin(pathImg,nombre,apellidos,nombreUsuario);
        gestor.alertasInformacion("Actualización", "Administrador actualizado exitosamente");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarFields();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
