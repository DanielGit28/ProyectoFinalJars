
package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Compositor;
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

public class CtrlAdminCompositores implements Initializable {


    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableColumn<Compositor, Integer> columnEdad;

    @FXML
    private Button btnCanciones;

    @FXML
    private TextField fieldNomActualizar;

    @FXML
    private TableColumn<Compositor, Integer> columnId;

    @FXML
    private TextField fieldApellidos;

    @FXML
    private ComboBox<String> fieldPais;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button inicio;

    @FXML
    private TableColumn<Compositor, String> columnNombre;

    @FXML
    private Button btnArtista;

    @FXML
    private TableColumn<Compositor, String> columnApellidos;

    @FXML
    private TableColumn<Compositor, String> columnPais;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private Button btnCrearGenero;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Compositor> tablaCompositores;

    @FXML
    private Button btnGenero;

    @FXML
    private TableColumn<Compositor, LocalDate> columnFechaNac;

    @FXML
    private Button btnActualizar;

    @FXML
    private TextField fieldId;

    private FilteredList<Compositor> compositoresFilt;

    {
        try {
            compositoresFilt = gestor.cargaCompositores();
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


    public boolean buscadorCompositor(Compositor compositor, String textoBuscado){
        return compositor.getNombre().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                compositor.getApellidos().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                compositor.getPaisNacimientoCompositor().getNombrePais().toLowerCase().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Compositor> crearPredicate(String textoBuscado){
        return compositor -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorCompositor(compositor, textoBuscado);
        };
    }



    /**
     * Llama al gestor para actualizar el Compositor seleccionado
     */
    public void actualizarCompositor() {
        try {
            gestor.actualizarCompositor(Integer.parseInt(fieldId.getText()), fieldNomActualizar.getText(), fieldApellidos.getText(), fieldPais.getValue());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            compositoresFilt = gestor.cargaCompositores();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablaCompositores.setItems(compositoresFilt);
    }

    /**
     * Llama al gestor para eliminar el Compositor seleccionado
     */
    public void eliminarCompositor() {
        try {
            gestor.eliminarCompositor(Integer.parseInt(fieldId.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            compositoresFilt = gestor.cargaCompositores();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablaCompositores.setItems(compositoresFilt);
    }

    /**
     * Este método carga la escena de registro de compositores
     * @param event evento que se genera cuando se aprieta el botón de crear compositor
     * @throws IOException
     */
    public void escenaCrearCompositor(ActionEvent event) throws IOException {
        gestor.escenarioCrearCompositores(event,window);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            compositoresFilt = gestor.cargaCompositores();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        columnId.setCellValueFactory(new PropertyValueFactory<Compositor, Integer>("id"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Compositor, String>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<Compositor, String>("apellidos"));
        columnPais.setCellValueFactory(new PropertyValueFactory<Compositor, String>("nombrePais"));
        columnFechaNac.setCellValueFactory(new PropertyValueFactory<Compositor, LocalDate>("fechaNacimientoCompositor"));
        columnEdad.setCellValueFactory(new PropertyValueFactory<Compositor, Integer>("edadCompositor"));


        tablaCompositores.setItems(compositoresFilt);

        tablaCompositores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaCompositores.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                Compositor compositor = tablaCompositores.getItems().get(row);
                fieldId.setText(String.valueOf(compositor.getId()));
                fieldNomActualizar.setText(compositor.getNombre());
                fieldApellidos.setText(compositor.getApellidos());
                fieldPais.setValue(compositor.getPaisNacimientoCompositor().getNombrePais());
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                compositoresFilt.setPredicate(crearPredicate(newValue))
        );
    }

}