
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Artista;
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

public class CtrlAdminArtistas implements Initializable {

    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private TableColumn<Artista, Integer> columnEdad;

    @FXML
    private Button btnCanciones;

    @FXML
    private TableColumn<Artista, Integer> columnId;

    @FXML
    private TableColumn<Artista, String> columnApellido;

    @FXML
    private Button inicio;

    @FXML
    private Button btnArtista;

    @FXML
    private Button btnCrearArtista;

    @FXML
    private TableView<Artista> tablaArtistas;

    @FXML
    private Button btnGenero;

    @FXML
    private TableColumn<Artista, String> columnGenero;

    @FXML
    private Button btnActualizar;

    @FXML
    private TextField fieldId;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableColumn<Artista, String> columnDescripcion;

    @FXML
    private TextField fieldNomActualizar;

    @FXML
    private TableColumn<Artista, String> columnNomArtistico;



    @FXML
    private TableColumn<Artista, String> columnNombre;

    @FXML
    private TableColumn<Artista, String> columnPais;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private Button btnAlbum;

    @FXML
    private TableColumn<Artista, LocalDate> columnFechaFallecimiento;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Artista, LocalDate> columnFechaNac;

    @FXML
    private TextField fieldDescripcion;

    @FXML
    private DatePicker fieldFallecimiento;

    @FXML
    private ComboBox<String> fieldPais;

    @FXML
    private TextField fieldApellido;

    @FXML
    private TextField fieldNomArtistico;


    private FilteredList<Artista> artistasFilt;


    {
        try {
            artistasFilt = gestor.cargaArtistas();
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
     * Este método carga la escena de registro de artistas
     * @param event evento que se genera cuando se aprieta el botón de crear artista
     * @throws IOException
     */
    public void escenaCrearArtista(ActionEvent event) throws IOException {
        gestor.escenarioCrearArtistas(event,window);
    }

    public boolean buscadorArtista(Artista artista, String textoBuscado){
        return artista.getNombreArtistico().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                artista.getNombreArtista().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                artista.getApellidoArtista().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                artista.getGeneroMusicalArtista().getNombreGenero().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                artista.getPaisNacimiento().getNombrePais().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                artista.getFechaNacimientoArtista().toString().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Artista> crearPredicate(String textoBuscado){
        return artista -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorArtista(artista, textoBuscado);
        };
    }



    /**
     * Llama al gestor para actualizar el artista seleccionado
     */
    public void actualizarArtista() {
        try {
            gestor.actualizarArtista(Integer.parseInt(fieldId.getText()), fieldNomActualizar.getText(), fieldApellido.getText(), fieldNomArtistico.getText(), fieldFallecimiento.getValue(), fieldPais.getValue(), fieldDescripcion.getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
                artistasFilt = gestor.cargaArtistas();
        } catch (SQLException throwables) {
                throwables.printStackTrace();
        }

        tablaArtistas.setItems(artistasFilt);
    }

    /**
     * Llama al gestor para eliminar el artista seleccionado
     */
    public void eliminarArtista() {
        try {
            gestor.eliminarArtista(Integer.parseInt(fieldId.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
                artistasFilt = gestor.cargaArtistas();
        } catch (SQLException throwables) {
                throwables.printStackTrace();
        }
        tablaArtistas.setItems(artistasFilt);
    }

    /**
     * Esta función carga los paises de la BD en el ComboBox paisNacimiento
     * @throws SQLException
     */
    public void cargarPaises() throws SQLException {
        if(fieldPais.getItems().size() == 0) {
            gestor.cargarPaisesComboBox(fieldPais);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            artistasFilt = gestor.cargaArtistas();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        columnId.setCellValueFactory(new PropertyValueFactory<Artista, Integer>("id"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Artista, String>("nombreArtista"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<Artista, String>("apellidoArtista"));
        columnNomArtistico.setCellValueFactory(new PropertyValueFactory<Artista, String>("nombreArtistico"));
        columnFechaNac.setCellValueFactory(new PropertyValueFactory<Artista, LocalDate>("fechaNacimientoArtista"));
        columnFechaFallecimiento.setCellValueFactory(new PropertyValueFactory<Artista, LocalDate>("fechaFallecimientoArtista"));

        //FALTA AVERIGUAR COMO OBTENER SOLO UN ATRIBUTO DEL OBJETO PAIS QUE VIENE EN EL OBSERVABLE LIST
        columnPais.setCellValueFactory(new PropertyValueFactory<Artista, String>("nombrePais"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<Artista, String>("nombreGenero"));

        columnEdad.setCellValueFactory(new PropertyValueFactory<Artista, Integer>("edadArtista"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<Artista, String>("descripcionArtista"));

        tablaArtistas.setItems(artistasFilt);

        tablaArtistas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaArtistas.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                Artista artista = tablaArtistas.getItems().get(row);
                fieldId.setText(String.valueOf(artista.getId()));
                fieldNomActualizar.setText(artista.getNombreArtista());
                fieldApellido.setText(artista.getApellidoArtista());
                fieldNomArtistico.setText(artista.getNombreArtistico());
                fieldFallecimiento.setValue(artista.getFechaFallecimientoArtista());
                fieldPais.setValue(artista.getPaisNacimiento().getNombrePais());
                fieldDescripcion.setText(artista.getDescripcionArtista());
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                artistasFilt.setPredicate(crearPredicate(newValue))
        );
    }


}
