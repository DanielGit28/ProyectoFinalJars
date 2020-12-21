
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Album;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Artista;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class CtrlCrearAlbumes {

    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;


    @FXML
    private Button cerrarSesion;

    @FXML
    private DatePicker fechaLanzamiento;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button btnImagen;

    @FXML
    private Button btnArtista;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private Button btnAlbum;

    @FXML
    private ComboBox<String> fieldArtista;

    @FXML
    private Button btnGuardarAlbum;

    @FXML
    private Button btnInicio;

    @FXML
    private ImageView ivImagen;

    @FXML
    private Button btnGenero;

    String pathImg;


    /**
     * Este método devuelve al escenario inicial del admin
     *
     * @param event evento que se genera cuando se aprieta el botón de Inicio
     * @throws IOException
     */
    public void inicio(ActionEvent event) throws IOException {
        gestor.escenarioInicioAdmin(event, window);
    }

    /**
     * Este método carga el escenario de canciones
     *
     * @param event evento que se genera cuando se aprieta el botón de canciones
     * @throws IOException
     */
    public void canciones(ActionEvent event) throws IOException {
        gestor.escenarioCanciones(event, window);
    }

    /**
     * Este método carga el escenario de compositores
     *
     * @param event evento que se genera cuando se aprieta el botón de compositores
     * @throws IOException
     */
    public void compositores(ActionEvent event) throws IOException {
        gestor.escenarioCompositores(event, window);
    }

    /**
     * Este método carga el escenario de artistas
     *
     * @param event evento que se genera cuando se aprieta el botón de artistas
     * @throws IOException
     */
    public void artistas(ActionEvent event) throws IOException {
        gestor.escenarioArtistas(event, window);
    }

    /**
     * Este método carga el escenario de géneros
     *
     * @param event evento que se genera cuando se aprieta el botón de géneros
     * @throws IOException
     */
    public void generos(ActionEvent event) throws IOException {
        gestor.escenarioGeneros(event, window);
    }

    /**
     * Este método carga el escenario de álbumes
     *
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void albumes(ActionEvent event) throws IOException {
        gestor.escenarioAlbumes(event, window);
    }

    /**
     * Este método devuelve al escenario inicial
     *
     * @param event evento que se genera cuando se aprieta el botón de cerrarSesion
     * @throws IOException
     */
    public void cerrarSesion(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/login.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }


    /**
     * Este método carga la imagen que se selecciona en el registro
     */
    public void cargarImagen() {
        btnImagen.setOnAction(event -> {
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
     * Esta función carga los paises de la BD en el ComboBox fieldArtista
     *
     * @throws SQLException
     */
    public void cargarArtistas() throws SQLException {
        if (fieldArtista.getItems().size() == 0) {
            gestor.cargarArtistasComboBox(fieldArtista);
        }
    }


    /**
     * Guarda un álbum en la BD
     * @throws SQLException
     */
    public void guardarAlbum() throws SQLException {
        Album album = new Album();
        String nombre = fiedlNombre.getText();
        LocalDate fechaLanz = fechaLanzamiento.getValue();
        Artista artista = gestor.getArtista(fieldArtista.getValue());
        if(nombre != null && fechaLanz != null && artista != null && pathImg != null) {
            album.setNombreAlbum(nombre);
            album.setFechaLanzamiento(fechaLanz);
            album.setArtistaAlbum(artista);
            album.setImagenAlbum(pathImg);
            gestor.guardarAlbum(album);
        } else {
            gestor.creacionAlertas("Debe seleccionar todos los campos de registro");
        }

    }


}
