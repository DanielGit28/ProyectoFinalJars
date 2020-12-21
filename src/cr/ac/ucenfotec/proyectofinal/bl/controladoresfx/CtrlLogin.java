package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx;

import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioFinal;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.UsuarioHolder;
import cr.ac.ucenfotec.proyectofinal.bl.logica.Gestor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CtrlLogin implements Initializable {
    Gestor gestor = new Gestor();
    Connection cnx;

    Scene escenaLogin;
    Stage window;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private TextField fiedlContrasenna;

    @FXML
    private TextField fiedlCorreo;

    @FXML
    private Button inicio;


    public void setEscenaRegistro(Scene scene) {
        escenaLogin = scene;
    }

    /**
     *
     * @param actionEvent abre la escena del login
     */
    public void abrirEscenaRegistro(ActionEvent actionEvent) {
        Stage stageRegistro = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageRegistro.setScene(escenaLogin);
    }

    /**
     *
     * @param event cuando se hace click en el botón de iniciar sesión, se activa el evento
     * @throws IOException
     */
    public void cambiarEscena(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../vistas/inicio.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     *
     * @param event accion en el boton de iniciar sesión
     * @throws IOException
     * @throws SQLException
     */
    public void cambiarEscenaLogin(ActionEvent event) throws IOException, SQLException {
        String correo = fiedlCorreo.getText();
        String contrasenna = fiedlContrasenna.getText();
        Parent login = null;
        boolean sesionAdmin = gestor.verificarSesionAdmin(correo,contrasenna);
        boolean sesionUsuario = gestor.verificarSesionUsuario(correo,contrasenna);

        UsuarioFinal usuarioSesion = gestor.getUsuario(correo, contrasenna);
        //System.out.println(usuarioSesion.toString());
        gestor.cargarDefaults();
        gestor.cargarPaises();
        String path = null;
        if(sesionAdmin == true) {
            login = FXMLLoader.load(getClass().getResource("../vistas/vistas_admin/menuAdmin.fxml"));
            path = "../vistas/vistas_admin/menuAdmin.fxml";
            Scene vistaLogin = new Scene(login);
            window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(vistaLogin);
            window.show();
        } else if(sesionUsuario == true) {
            login = FXMLLoader.load(getClass().getResource("../vistas/vistas_usuario/menuUsuario.fxml"));
            path = "../../vistas/vistas_usuario/menuUsuario.fxml";
            gestor.manejoEscenasLogin(event, window,login,usuarioSesion, path);
            CloseAction(event);
            //UsuarioHolder holder = UsuarioHolder.getInstance();

            UsuarioHolder.getInstance().currentUsuario().setNombre(usuarioSesion.getNombre());
            //System.out.println(UsuarioHolder.INSTANCE.getUsuario());
        } else {
            gestor.creacionAlertas("Datos incorrectos. Correo y/o contraseña incorrectos");
        }

    }

    @FXML
    public void CloseAction(ActionEvent event) {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }


/*
    @FXML
    private void login(javafx.event.ActionEvent event) throws IOException {
        if (pwf1.getText().equals("dolphin")) {
            Parent blah = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } else {
            label1.setText("Password is incorrect. Please Try Again");
        }
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
