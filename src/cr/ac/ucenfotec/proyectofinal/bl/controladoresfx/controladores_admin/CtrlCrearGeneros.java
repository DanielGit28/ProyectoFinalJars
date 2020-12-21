package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_admin;

import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.0
 */

public class CtrlCrearGeneros {
    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;


    @FXML
    private Button cerrarSesion;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCompositor;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnArtista;

    @FXML
    private Button btnGenero;

    @FXML
    private TextField fiedlNombre;

    @FXML
    private TextArea areaDescripcion;


    /**
     * Guarda el género en la BD
     * @throws SQLException
     */
    public void guardarGenero() throws SQLException {
        String nombre = fiedlNombre.getText();
        String descripcion = areaDescripcion.getText();
        if(nombre != null && descripcion != null ) {
            gestor.guardarGenero(nombre, descripcion);
            //gestor.alertasInformacion("Género","Género registrado con éxito");
        } else {
            gestor.creacionAlertas("Debe rellenar todos los campos");
        }

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



}
