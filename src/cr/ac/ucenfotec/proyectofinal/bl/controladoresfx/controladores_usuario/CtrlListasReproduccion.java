package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.ListaReproduccion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
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

public class CtrlListasReproduccion implements Initializable {

    Gestor gestor = new Gestor();

    Scene escenaRegistro;
    Stage window;

    @FXML
    private Button cerrarSesion;

    @FXML
    private Button btnListaReproduccion;

    @FXML
    private Button btnCanciones;

    @FXML
    private Button bntMetodosPago;

    @FXML
    private TableView<ListaReproduccion> tablaListas;

    @FXML
    private TableColumn<ListaReproduccion, String> colNombre;

    @FXML
    private Button btnSubirCancion;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button btnReproducirCancion;

    @FXML
    private Button btnCrearLista;

    @FXML
    private Button btnAgregarCancion;

    @FXML
    private TableColumn<ListaReproduccion, Integer> colCalificacion;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnComprarCancion;

    @FXML
    private TableColumn<ListaReproduccion, LocalDate> colFechaCreacion;

    @FXML
    private TableColumn<ListaReproduccion, String> colAutorLista;


    UsuarioFinal usuario;
    ListaReproduccion listaSeleccionada;
    private FilteredList<ListaReproduccion> listasFilt;



/*
    {
        try {
            cancionFilt = gestor.cargaCancionesUsuario(usuario.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

 */




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
        //System.out.println("Sesión usuario");


    }


    public boolean buscadorLista(ListaReproduccion lista, String textoBuscado){
        return lista.getNombreListaReproduccion().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                String.valueOf(lista.getCalificacionReproduccion()).toLowerCase().contains(textoBuscado.toLowerCase()) ||
                lista.getAutorLista().getNombreUsuario().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                lista.getFechaCreacionListaReproduccion().toString().toLowerCase().contains(textoBuscado.toLowerCase());
    }

    private Predicate<ListaReproduccion> crearPredicate(String textoBuscado){
        return listaReproduccion -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorLista(listaReproduccion, textoBuscado);
        };
    }


    public void reproducirLista(){
        gestor.alertasInformacion("Reproducción", "Lista de reproduccion reproduciendose (simulación)");
    }


    /**
     * Abre la ventana de crear una lista de reproduccion
     * @throws IOException
     */
    public void crearLista(ActionEvent event) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/crearListaReproduccion.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlCrearListaReproduccion controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        CloseAction(event,btnCrearLista);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("Initialize");
        //setUsuarioSesion(usuario);

        try {
            listasFilt = gestor.cargaListasReproduccion();
            System.out.println(listasFilt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(listasFilt != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, String>("nombreListaReproduccion"));
            colCalificacion.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, Integer>("calificacionReproduccion"));
            colFechaCreacion.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, LocalDate>("fechaCreacionListaReproduccion"));
            colAutorLista.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, String>("nombreUsuario"));

            tablaListas.setItems(listasFilt);
        }
        //System.out.println(listasFilt);

        tablaListas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaListas.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                ListaReproduccion lista = tablaListas.getItems().get(row);
                listaSeleccionada = lista;
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                listasFilt.setPredicate(crearPredicate(newValue))
        );
    }


}
