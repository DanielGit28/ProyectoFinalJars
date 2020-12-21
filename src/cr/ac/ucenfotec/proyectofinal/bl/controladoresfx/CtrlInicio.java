package cr.ac.ucenfotec.proyectofinal.bl.controladoresfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CtrlInicio implements Initializable {

    @FXML
    private TextArea tituloEscena;
    @FXML
    private TextField descripcionInicio;
    @FXML
    private Button botonRegistrarAdmin;
    @FXML
    private Button btnRegistrarUsuario;
    @FXML
    private Button btnSesion;

    Scene escenaInicio;

    /**
     * Cuando este metodo se llame, cambiara la escena
     * @param event cuando se presiona el boton de registroAdmin
     */
    public void cambiarEscena(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../vistas/registroAdmin.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }
    public void cambiarEscenaUsuario(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../vistas/registroUsuario.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Cuando este metodo se llame, cambiara la escena
     * @param event cuando se presiona el boton de inicioSesion
     */
    public void cambiarEscenaLogin(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../vistas/login.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }


    public void setEscenaRegistro(Scene scene) {
        escenaInicio = scene;
    }

    /**
     *
     * @param actionEvent evento que genera la escena dentro del escenario
     */
    public void abrirEscenaRegistro(ActionEvent actionEvent) {
        Stage stageInicio = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageInicio.setScene(escenaInicio);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
/*
    public void cambiarEscena(ActionEvent actionEvent, window, Scene scene) {
        botonRegistrarAdmin.setOnAction(event -> {
            window.setScene(scene);
        });
    }
    */
}
