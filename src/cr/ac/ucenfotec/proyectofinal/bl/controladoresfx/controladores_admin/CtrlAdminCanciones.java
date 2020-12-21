
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

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

public class CtrlAdminCanciones implements Initializable {

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
    private ComboBox<String> fieldCompositor;

    @FXML
    private Button btnCrearCanción;

    @FXML
    private ComboBox<String> fieldArtista;

    @FXML
    private TableColumn<Cancion, LocalDate> columnFechaLanz;

    @FXML
    private Button btnGenero;

    @FXML
    private TableColumn<Cancion, String> columnAlbum;

    @FXML
    private Button btnActualizar;

    @FXML
    private TextField fieldId;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableColumn<Cancion, String> columnCompositor;

    @FXML
    private DatePicker fieldFechaLanz;

    @FXML
    private TextField fieldNomActualizar;

    @FXML
    private Button btnCompositor;

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
    private Button btnEliminar;

    @FXML
    private ComboBox<String> fieldGenero;



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

    /**
     * Este método carga la escena de registro de canciones
     * @param event evento que se genera cuando se aprieta el botón de crear canciones
     * @throws IOException
     */
    public void escenaCrearCancion(ActionEvent event) throws IOException {
        gestor.escenarioGuardarCanciones(event,window);
    }

    public boolean buscadorCancion(Cancion cancion, String textoBuscado){
        return cancion.getNombreCancion().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getAlbumCancion().getNombreAlbum().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getGeneroCancion().getNombreGenero().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getArtistaCancion().getNombreArtistico().toLowerCase().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Cancion> crearPredicate(String textoBuscado){
        return cancion -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorCancion(cancion, textoBuscado);
        };
    }



    /**
     * Llama al gestor para actualizar la canción seleccionada
     */
    public void actualizarCancion() {
        try {
            gestor.actualizarCancion(Integer.parseInt(fieldId.getText()), fieldNomActualizar.getText(), fieldArtista.getValue(), fieldCompositor.getValue());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            cancionFilt = gestor.cargaCanciones();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tablaCanciones.setItems(cancionFilt);
    }

    /**
     * Llama al gestor para eliminar la canción seleccionada
     */
    public void eliminarCancion() {
        try {
            gestor.eliminarCancion(Integer.parseInt(fieldId.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            cancionFilt = gestor.cargaCanciones();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablaCanciones.setItems(cancionFilt);
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

                Cancion cancion = tablaCanciones.getItems().get(row);
                fieldId.setText(String.valueOf(cancion.getId()));
                fieldNomActualizar.setText(cancion.getNombreCancion());
                fieldArtista.setValue(cancion.getArtistaCancion().getNombreArtistico());
                fieldCompositor.setValue(cancion.getCompositorCancion().getNombre());
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                cancionFilt.setPredicate(crearPredicate(newValue))
        );
    }


}
