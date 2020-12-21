package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

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

public class CtrlAgregarCanciones {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;


    @FXML
    private Button cerrarSesion;

    @FXML
    private DatePicker fechaLanzamiento;

    @FXML
    private ComboBox<String> fieldAlbum;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button btnArtista;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private TextField fieldPrecio;

    @FXML
    private ComboBox<String> fieldCompositor;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> fieldArtista;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnArchivoCancion;

    @FXML
    private ComboBox<String> fieldGenero;

    @FXML
    private Button btnGenero;

    private String pathArchivo;

    /**
     * Guarda la canción en la BD
     * @throws SQLException
     */
    public void guardarCancion() throws SQLException {
        String nombre = fiedlNombre.getText();
        String artista = String.valueOf(fieldArtista.getValue());
        String compositor = String.valueOf(fieldCompositor.getValue());
        LocalDate fechaLanz = fechaLanzamiento.getValue();
        String genero = String.valueOf(fieldGenero.getValue());
        String album = String.valueOf(fieldAlbum.getValue());
        int precio = Integer.parseInt(fieldPrecio.getText());
        String recurso = pathArchivo;
        if(nombre != null  && fechaLanz != null && genero != null && recurso != null) {
            gestor.guardarCancion(nombre,artista,compositor,fechaLanz,genero,album,precio,recurso);
        } else {
            gestor.creacionAlertas("Debe rellenar todos los campos menos el de artista o compositor, dependiendo el intérprete de la canción, y álbum.");
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
     * Este método devuelve al escenario inicial del admin
     * @param event evento que se genera cuando se aprieta el botón de Inicio
     * @throws IOException
     */
    public void inicio(ActionEvent event) throws IOException {
        gestor.escenarioInicioAdmin(event, window);
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

}
