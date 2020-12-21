
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Album;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.0
 */

public class CtrlAgregarCancionesAlbum implements Initializable {

    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private TableColumn<Cancion, String> columnRecurso;

    @FXML
    private Button btnCanciones;

    @FXML
    private TableColumn<Cancion, Integer> columnId;

    @FXML
    private TableColumn<Cancion, Integer> columnCancionSimple;

    @FXML
    private Button inicio;

    @FXML
    private Button btnArtista;

    @FXML
    private TableView<Cancion> tablaCanciones;


    @FXML
    private Button btnCrearCanción;


    @FXML
    private TableColumn<Cancion, LocalDate> columnFechaLanz;

    @FXML
    private Button btnGenero;

    @FXML
    private TableColumn<Cancion, String> columnAlbum;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableColumn<Cancion, String> columnCompositor;

    @FXML
    private DatePicker fieldFechaLanz;


    @FXML
    private TableColumn<Cancion, String> columnNombre;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private TableColumn<Cancion, String> columnArtista;

    @FXML
    private TableColumn<Cancion, String> columnGenero;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnQuitarCancion;

    @FXML
    private Button btnAgregarCancion;

    @FXML
    private Button btnFinalizar;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private ComboBox<String> fieldAlbum;

    @FXML
    private DatePicker fechaLanzamiento;

    @FXML
    private ImageView ivImagen;

    @FXML
    private Button btnImagen;


    Cancion cancionSeleccionada = new Cancion();
    ArrayList<Cancion> cancionesSeleccionadas = new ArrayList<>();
    String pathImg;

    private FilteredList<Cancion> cancionFilt;


    {
        try {
            cancionFilt = gestor.cargaCanciones();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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

    public boolean buscadorCancion(Cancion cancion, String textoBuscado) {
        return cancion.getNombreCancion().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getAlbumCancion().getNombreAlbum().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getGeneroCancion().getNombreGenero().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getArtistaCancion().getNombreArtistico().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getFechaLanzamientoCancion().toString().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Cancion> crearPredicate(String textoBuscado) {
        return cancion -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorCancion(cancion, textoBuscado);
        };
    }

    /**
     * Esta función carga los álbumes de la BD en el ComboBox fieldAlbum
     * @throws SQLException
     */
    public void cargarAlbumes() throws SQLException {
        if (fieldAlbum.getItems().size() == 0) {
            gestor.cargarAlbumesComboBox(fieldAlbum);
        }
    }

    /**
     * Esta función quita la canción seleccionada, en la tabla, del ArrayList de canciones local del controlador
     */
    public void quitarCancionArray() {
        if(tablaCanciones.getSelectionModel().selectedItemProperty() == null) {
            gestor.creacionAlertas("Debe seleccionar una canción para poder quitarla del álbum");
        } else {
            if(cancionSeleccionada != null) {
                cancionesSeleccionadas.remove(cancionSeleccionada);
                gestor.alertasInformacion("Canción", "Canción removida");
            }
        }
    }

    /**
     * Esta función agrega la canción seleccionada, en la tabla al ArrayList de canciones local del controlador
     */
    public void agregarCancionArray() {
        if(tablaCanciones.getSelectionModel().selectedItemProperty() != null) {
            System.out.println(cancionSeleccionada.toString());
            if(cancionSeleccionada.getNombreCancion() != null) {
                if(cancionSeleccionada.getAlbumCancion().getNombreAlbum().equals("Default")) {
                    //VERIFICA QUE LA CANCIÓN NO ESTE REPETIDA EN EL ARRAYLIST LOCAL
                    for(Cancion cancion : cancionesSeleccionadas) {
                            if(cancion.getId() == cancionSeleccionada.getId()) {
                                gestor.creacionAlertas("Canción ya añadida");
                            } else {
                                cancionesSeleccionadas.add(cancionSeleccionada);
                                gestor.alertasInformacion("Canción", "Canción añadida");
                            }
                    }
                    if(cancionesSeleccionadas.size() == 0) {
                        cancionesSeleccionadas.add(cancionSeleccionada);
                        gestor.alertasInformacion("Canción", "Canción añadida");
                    }
                }else {
                    gestor.creacionAlertas("No se puede agregar esta canción porque ya pertence a otro álbum o ya ha sido agregada");
                }
            } else {
                gestor.creacionAlertas("Debe seleccionar una canción para poder añadirla al álbum");
            }
        }
    }

    /**
     * Guarda las canciones del álbum en la BD
     * @throws SQLException
     */
    public void finalizarCanciones() throws SQLException {
        Album album = gestor.getAlbum(fieldAlbum.getValue());
        if(album != null) {
            album.setCancionesAlbum(cancionesSeleccionadas);
            gestor.actualizarAlbum(album);
        } else {
            gestor.creacionAlertas("Debe seleccionar el álbum");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cancionFilt = gestor.cargaCanciones();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        columnId.setCellValueFactory(new PropertyValueFactory<Cancion, Integer>("id"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreCancion"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreArtista"));
        columnCompositor.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreCompositor"));
        columnFechaLanz.setCellValueFactory(new PropertyValueFactory<Cancion, LocalDate>("fechaLanzamientoCancion"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreGenero"));
        columnCancionSimple.setCellValueFactory(new PropertyValueFactory<Cancion, Integer>("cancionSimple"));
        columnAlbum.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreAlbum"));
        columnRecurso.setCellValueFactory(new PropertyValueFactory<Cancion, String>("recurso"));

        tablaCanciones.setItems(cancionFilt);

        tablaCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaCanciones.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                cancionSeleccionada = tablaCanciones.getItems().get(row);
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                cancionFilt.setPredicate(crearPredicate(newValue))
        );
    }


}
