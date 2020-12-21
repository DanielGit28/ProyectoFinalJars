package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.0
 */

public class CtrlSubirCancion {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;



    @FXML
    private Button cerrarSesion;

    @FXML
    private DatePicker fechaLanzamiento;

    @FXML
    private Button btnListaReproduccion;

    @FXML
    private ComboBox<String> fieldAlbum;

    @FXML
    private Button btnCanciones;

    @FXML
    private TextField fieldPrecio;

    @FXML
    private Button bntMetodosPago;

    @FXML
    private Button btnSubirCancion;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private ComboBox<String> fieldCompositor;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> fieldArtista;

    @FXML
    private Button btnArchivoCancion;

    @FXML
    private Button btnInicio;

    @FXML
    private ComboBox<String> fieldGenero;

    @FXML
    private Button btnComprarCancion;

    private String pathArchivo;
    UsuarioFinal usuario;


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
     * Establece el usuario que el controlador va a utilizar para manejar la aplicación
     * @param usuarioSes usuario que inicio sesión
     */
    public void setUsuarioSesion(UsuarioFinal usuarioSes) {
        this.usuario = usuarioSes;
    }


    /**
     * Esta función carga los paises de la BD en el ComboBox fieldArtista
     * @throws SQLException
     */
    public void cargarArtistas() throws SQLException {
        if(fieldArtista.getItems().size() == 0) {
            gestor.cargarArtistasComboBox(fieldArtista);
        }
    }

    /**
     * Esta función carga los compositores de la BD en el ComboBox fieldCompositor
     * @throws SQLException
     */
    public void cargarCompositores() throws SQLException {
        if(fieldCompositor.getItems().size() == 0) {
            gestor.cargarCompositoresComboBox(fieldCompositor);
        }
    }

    /**
     * Esta funcion carga los generos musicales de la BD en el comboBox genero
     * @throws SQLException
     */
    public void cargarGeneros() throws SQLException {
        if(fieldGenero.getItems().size() == 0) {
            gestor.cargarGenerosComboBox(fieldGenero);
        }
    }

    /**
     * Esta funcion carga los álbumes de la BD en el comboBox fieldAlbum
     * @throws SQLException
     */
    public void cargarAlbumes() throws SQLException {
        if(fieldAlbum.getItems().size() == 0) {
            gestor.cargarAlbumesComboBox(fieldAlbum);
        }
    }




    /**
     * Guarda la canción en la BD para el usuario
     * @throws SQLException
     */
    public void guardarCancion() throws SQLException {
        String nombre = fiedlNombre.getText();
        String artista = String.valueOf(fieldArtista.getValue());
        String compositor = String.valueOf(fieldCompositor.getValue());
        LocalDate fechaLanz = fechaLanzamiento.getValue();
        String genero = String.valueOf(fieldGenero.getValue());
        String album = String.valueOf(fieldAlbum.getValue());
        String recurso = pathArchivo;
        if(nombre != null  && fechaLanz != null && genero != null && recurso != null && compositor != null && artista != null) {
            gestor.guardarCancionUsuario(usuario.getId(), nombre,artista,compositor,fechaLanz,genero,album,recurso);
        } else {
            gestor.creacionAlertas("Debe rellenar todos los campos menos el de artista o compositor, dependiendo el intérprete de la canción, escoger default, y álbum.");
        }

    }

    public void cargarArchivo() {
        btnArchivoCancion.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Buscar archivo de audio");

            // Agregar filtros para facilitar la busqueda
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                    new FileChooser.ExtensionFilter("MP4", "*.mp4")
            );

            // Obtener la imagen seleccionada
            File archivoMP3 = fileChooser.showOpenDialog(window);

            // Mostar la imagen
            if (archivoMP3 != null) {
                pathArchivo = String.valueOf(archivoMP3);
                //System.out.println("DIR "+pathImg);
            }
        });
    }

}

