package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.Cancion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.ListaReproduccion;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author Daniel Zúñiga Rojas
 * @version 1.0
 */

public class CtrlCrearListaReproduccion implements Initializable {

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
    private TableColumn<Cancion, Integer> columnPrecio;


    @FXML
    private Button btnArtista;

    @FXML
    private TableView<Cancion> tablaCanciones;

    @FXML
    private TableColumn<Cancion, LocalDate> columnFechaLanz;

    @FXML
    private Button btnListaReproduccion;

    @FXML
    private TableColumn<Cancion, String> columnAlbum;

    @FXML
    private Button btnAlbum;

    @FXML
    private Button cerrarSesion;

    @FXML
    private TableColumn<Cancion, String> columnCompositor;

    @FXML
    private TableColumn<Cancion, String> columnNombre;

    @FXML
    private TextField fieldBusqueda;

    @FXML
    private TextField fieldNombreLista;

    @FXML
    private TableColumn<Cancion, String> columnArtista;

    @FXML
    private TableColumn<Cancion, String> columnGenero;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnReproducirCancion;

    @FXML
    private Button btnSubirCancion;

    @FXML
    private Button btnComprarCancion;

    @FXML
    private Button bntMetodosPago;

    @FXML
    private Button bntAgregarCancion;

    @FXML
    private Button btnCrearLista;

    UsuarioFinal usuario;
    Cancion cancionSeleccionada;

    ArrayList<Cancion> cancionesSeleccionadas = new ArrayList<>();

    private FilteredList<Cancion> cancionFilt;



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

        try {
            cancionFilt = gestor.cargaCancionesUsuario(usuario.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(cancionFilt);
        columnId.setCellValueFactory(new PropertyValueFactory<Cancion, Integer>("id"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreCancion"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreArtista"));
        columnCompositor.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreCompositor"));
        columnFechaLanz.setCellValueFactory(new PropertyValueFactory<Cancion, LocalDate>("fechaLanzamientoCancion"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreGenero"));
        columnAlbum.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombreAlbum"));

        tablaCanciones.setItems(cancionFilt);


    }


    public boolean buscadorCancion(Cancion cancion, String textoBuscado){
        return cancion.getNombreCancion().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getAlbumCancion().getNombreAlbum().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getGeneroCancion().getNombreGenero().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getArtistaCancion().getNombreArtistico().toLowerCase().contains(textoBuscado.toLowerCase()) ||
                cancion.getFechaLanzamientoCancion().toString().toLowerCase().contains(textoBuscado.toLowerCase());
    }

    private Predicate<Cancion> crearPredicate(String textoBuscado){
        return cancion -> {
            if (textoBuscado == null || textoBuscado.isEmpty()) return true;
            return buscadorCancion(cancion, textoBuscado);
        };
    }

    /**
     * Esta función quita la canción seleccionada, en la tabla, del ArrayList de canciones local del controlador
     */
    public void quitarCancionArray() {
        if(tablaCanciones.getSelectionModel().selectedItemProperty() == null) {
            gestor.creacionAlertas("Debe seleccionar una canción para poder quitarla de la lista");
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
                }
            } else {
                gestor.creacionAlertas("Debe seleccionar una canción para poder añadirla a la lista de reproducción");
            }
    }

    /**
     * Guarda la lista de reproducción en la BD
     * @throws SQLException
     */
    public void finalizarCanciones() throws SQLException {
        ListaReproduccion lista = new ListaReproduccion();
        if(fieldNombreLista.getText() != null && cancionesSeleccionadas.size() > 0) {
            lista.setNombreListaReproduccion(fieldNombreLista.getText());
            lista.setFechaCreacionListaReproduccion(LocalDate.now());
            lista.setCalificacionReproduccion(0);
            lista.setAutorLista(gestor.getUsuarioById(usuario.getId()));
            lista.setCancionesListaReproduccion(cancionesSeleccionadas);
            gestor.guardarListaReproduccion(lista);
        } else {
            gestor.creacionAlertas("Debe seleccionar mínimo una canción y rellenar el espacio de nombre de lista");
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("Initialize");
        //setUsuarioSesion(usuario);
        tablaCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TablePosition pos = tablaCanciones.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                Cancion cancion = tablaCanciones.getItems().get(row);
                cancionSeleccionada = cancion;
            }
        });

        fieldBusqueda.textProperty().addListener((observable, oldValue, newValue) ->
                cancionFilt.setPredicate(crearPredicate(newValue))
        );
    }


}
