
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Album;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.0
 */

public class CtrlAdminAlbumes implements Initializable {


    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableView<Album> tablaAlbumes;

    @FXML
    private TableColumn<Album, String> columnImagen;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button btnCrearAlbum;

    @FXML
    private TableColumn<Album, Integer> columnId;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button inicio;

    @FXML
    private TableColumn<Album, String> columnNombre;

    @FXML
    private Button btnArtista;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private TableColumn<Album, String> columnArtista;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Album, LocalDate> columnFechaLanz;

    @FXML
    private Button btnGenero;

    @FXML
    private Button btnActualizar;

    private FilteredList<Album> albumFilt;

    Album albumSeleccionado = new Album();

    {
        try {
            albumFilt = gestor.cargaAlbumes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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


    public boolean buscadorAlbum(Album album, String textoBuscado){
        return album.getNombreAlbum().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                album.getArtistaAlbum().getNombreArtistico().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                album.getFechaLanzamiento().toString().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Album> crearPredicate(String textoBuscado){
        return album -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorAlbum(album, textoBuscado);
        };
    }


    /**
     * Llama al gestor para eliminar el álbum seleccionado
     */
    public void eliminarAlbum() {
        try {
            gestor.eliminarAlbum(albumSeleccionado.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            albumFilt = gestor.cargaAlbumes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablaAlbumes.setItems(albumFilt);
    }

    /**
     * Este método carga la escena de registro de álbumes
     * @param event evento que se genera cuando se aprieta el botón de crear álbum
     * @throws IOException
     */
    public void escenaCrearAlbum(ActionEvent event) throws IOException {
        gestor.escenarioCrearAlbumes(event,window);
    }

    /**
     * Este método carga la escena de actualizar álbum
     * @param event evento que se genera cuando se aprieta el botón de actualizar álbum
     * @throws IOException
     */
    public void actualizarAlbum(ActionEvent event) throws IOException {
        gestor.escenarioActualizarAlbumes(event,window);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            albumFilt = gestor.cargaAlbumes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        columnId.setCellValueFactory(new PropertyValueFactory<Album, Integer>("id"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Album, String>("nombreAlbum"));
        columnFechaLanz.setCellValueFactory(new PropertyValueFactory<Album, LocalDate>("fechaLanzamiento"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<Album, String>("nombreArtista"));
        columnImagen.setCellValueFactory(new PropertyValueFactory<Album, String>("imagenAlbum"));

        tablaAlbumes.setItems(albumFilt);

        tablaAlbumes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaAlbumes.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                albumSeleccionado = tablaAlbumes.getItems().get(row);
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                albumFilt.setPredicate(crearPredicate(newValue))
        );
    }

}