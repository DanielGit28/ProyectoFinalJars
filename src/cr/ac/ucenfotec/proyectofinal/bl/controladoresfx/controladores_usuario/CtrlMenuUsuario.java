package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioHolder;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.1
 */

public class CtrlMenuUsuario implements Initializable {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    String pathImg;


    @FXML
    private Button cerrarSesion;

    @FXML
    private Button btnListaReproduccion;

    @FXML
    private ComboBox<String> lblPais;

    @FXML
    private Button btnCanciones;

    @FXML
    private TextField lblNombre;

    @FXML
    private Button bntMetodosPago;

    @FXML
    private TextField lblNombreUsuario;

    @FXML
    private Button btnSubirCancion;

    @FXML
    private Button btnAlbum;

    @FXML
    private ImageView ctnAvatar;

    @FXML
    private TextField lblCorreo;

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Button btnInicio;

    @FXML
    private TextField lblIdentificacion;

    @FXML
    private Button btnComprarCancion;

    @FXML
    private DatePicker lblFechaNac;

    @FXML
    private TextField lblApellidos;

    @FXML
    private Button btnActualizar;


    UsuarioFinal usuario;

    /**
     *
     * @param actionEvent abre la escena del registro del administrador
     */
    /*
    public void abrirEscenaRegistro(ActionEvent actionEvent) throws SQLException {
        Stage stageRegistro = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageRegistro.setScene(escenaRegistro);
    }*/

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
                //System.out.println(pathImg);
                //System.out.println("DIR "+pathImg);
            }
        });
    }

    /**
     * Este método carga el escenario de cuenta de usuario como inicio
     * @param event evento que se genera cuando se aprieta el botón de inicio
     * @throws IOException
     */
    public void escenaInicio(ActionEvent event) throws IOException {
        gestor.escenarioInicioUsuario(event, window, usuario);
        CloseAction(event, btnInicio);
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
     *      * Carga los labels de la cuenta del admin con los datos de la base de datos
     * @throws SQLException
     */
    public void cargarFields(){
        if(usuario == null || usuario.getNombre() == null) {
            System.out.println("Usuario vacío");
        } else {
            String avatar = usuario.getAvatarUsuario();
            pathImg = avatar;
            //System.out.println(avatar + ", avatar");
            File file = new File(avatar);
            Image image = new Image(file.toURI().toString());
            //Image image = new Image("file:" + avatar);
            ctnAvatar.setImage(image);
            lblNombre.setText(usuario.getNombre());
            lblApellidos.setText(usuario.getApellidosUsuario());
            lblCorreo.setText(usuario.getCorreoUsuario());
            lblNombreUsuario.setText(usuario.getNombreUsuario());
            lblFechaNac.setValue(usuario.getFechaNacimientoUsuario());
            lblIdentificacion.setText(usuario.getIdentificacionUsuario());
            lblPais.setValue(usuario.getPaisProcedenciaUsuario().getNombrePais());
        }

    }

    /**
     * Esta función carga los paises de la BD en el ComboBox paisNacimiento
     * @throws SQLException
     */
    public void cargarPaises() throws SQLException {
        if(lblPais.getItems().size() == 0) {
            gestor.cargarPaisesComboBox(lblPais);
        }
    }

    /**
     * Actualiza los datos del usuario según los campos de texto
     * @throws SQLException
     */
    public void actualizarUsuario() throws SQLException {
        String nombre = lblNombre.getText();
        String apellidos = lblApellidos.getText();
        LocalDate fechaNac = lblFechaNac.getValue();
        String pais = lblPais.getValue();
        String nombreUsuario = lblNombreUsuario.getText();
        //System.out.println(pathImg);
        gestor.actualizarUsuario(usuario.getId(), pathImg,nombre,apellidos,fechaNac,nombreUsuario,pais);
        //gestor.alertasInformacion("Actualización", "Usuario actualizado exitosamente");
    }

    /**
     * Establece el usuario que el controlador va a utilizar para manejar la aplicación
     * @param usuarioSes usuario que inicio sesión
     */
    public void setUsuarioSesion(UsuarioFinal usuarioSes) {
        this.usuario = usuarioSes;
        //System.out.println(usuario.toString());
        cargarFields();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(UsuarioHolder.getInstance().currentUsuario().getNombre()+" instancia");
        //cargarFields();
    }
}
