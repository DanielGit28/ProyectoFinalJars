package cr.ac.ucenfotec.proyectofinal.bl.logica;


import cr.ac.ucenfotec.proyectofinal.bl.controladoresfx.controladores_usuario.*;
import cr.ac.ucenfotec.proyectofinal.bl.entidades.*;
import cr.ac.ucenfotec.proyectofinal.bl.dao.*;
//import cr.ac.ucenfotec.proyectofinal.controladoresfx.controladores_usuario.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Daniel
 * @version 1.2
 */

public class Gestor {
    PropertiesHandler propertiesHandler = new PropertiesHandler();
    Connection connection;

    public UsuarioFinal usuarioSesion;

    private PreparedStatement cmdInsertarPaises;
    private PreparedStatement queryPaises;
    private PreparedStatement queryAdmin;
    private PreparedStatement queryGeneros;
    private PreparedStatement queryArtistas;
    private PreparedStatement queryAlbumes;
    private PreparedStatement queryCanciones;
    private PreparedStatement queryCompositores;
    private PreparedStatement queryListas;

    private final String TEMPLATE_CMD_INSERTAR = "insert into pais (nombrePais, codigoPais) values (?,?)";
    private final String TEMPLATE_QRY_TODOSLOSPAISES = "select * from pais";
    private final String TEMPLATE_QRY_ADMIN = "select * from admin";
    private final String TEMPLATE_QRY_GENEROS = "select * from genero";
    private final String TEMPLATE_QRY_ARTISTAS = "select * from artista";
    private final String TEMPLATE_QRY_ALBUMES = "select * from album";
    private final String TEMPLATE_QRY_CANCIONES = "select * from cancion";
    private final String TEMPLATE_QRY_COMPOSITORES = "select * from compositor";
    private final String TEMPLATE_QRY_LISTAS = "select * from lista_reproduccion_usuario";

    private final String[] locales = Locale.getISOCountries();
    private ObservableList<String> listaPaises;
    private String[] listPais;

    AdminDAO adminDAO;
    AlbumDAO albumDAO;
    ArtistaDAO artistaDAO;
    CancionDAO cancionDAO;
    CompositorDAO compositorDAO;
    GeneroDAO generoDAO;
    ListaReproduccionDAO listaReproduccionDAO;
    UsuarioFinalDAO usuarioFinalDAO;
    MetodoPagoDAO metodoPagoDAO;

    //VARIABLES QUE PROXIMAMENTE SE ELIMINARAN
    Admin administrador = new Admin();
    ArrayList<UsuarioFinal> usuarios = new ArrayList<>();
    ArrayList<Artista> artistas = new ArrayList<>();
    ArrayList<Compositor> compositores = new ArrayList<>();
    ArrayList<Genero> generos = new ArrayList<>();
    ArrayList<Album> albumes = new ArrayList<>();
    ArrayList<Cancion> canciones = new ArrayList<>();
    ArrayList<ListaReproduccion> listas = new ArrayList<>();

    public Gestor() {
        try {
            propertiesHandler.loadProperties();
            String driver = propertiesHandler.getDriver();
            Class.forName(driver).newInstance();
            String url = propertiesHandler.getCnxStr();
            connection = DriverManager.getConnection(url, propertiesHandler.getUser(), propertiesHandler.getPassword());

            this.adminDAO = new AdminDAO(this.connection);
            this.albumDAO = new AlbumDAO(this.connection);
            this.artistaDAO = new ArtistaDAO(this.connection);
            this.cancionDAO = new CancionDAO(this.connection);
            this.compositorDAO = new CompositorDAO(this.connection);
            this.generoDAO = new GeneroDAO(this.connection);
            this.listaReproduccionDAO = new ListaReproduccionDAO(this.connection);
            this.usuarioFinalDAO = new UsuarioFinalDAO(this.connection);
            this.metodoPagoDAO = new MetodoPagoDAO(this.connection);

            //--ESTA SECCION CARGA LOS PAISES EN LA BASE DE DATOS CUANDO SE INICIALICE EL CONSTRUCTOR DEL GESTOR--
            this.cmdInsertarPaises = connection.prepareStatement(TEMPLATE_CMD_INSERTAR);
            this.queryPaises = connection.prepareStatement(TEMPLATE_QRY_TODOSLOSPAISES);
            this.queryAdmin = connection.prepareStatement(TEMPLATE_QRY_ADMIN);
            this.queryGeneros = connection.prepareStatement(TEMPLATE_QRY_GENEROS);
            this.queryArtistas = connection.prepareStatement(TEMPLATE_QRY_ARTISTAS);
            this.queryAlbumes = connection.prepareStatement(TEMPLATE_QRY_ALBUMES);
            this.queryCanciones = connection.prepareStatement(TEMPLATE_QRY_CANCIONES);
            this.queryCompositores = connection.prepareStatement(TEMPLATE_QRY_COMPOSITORES);
            this.queryListas = connection.prepareStatement(TEMPLATE_QRY_LISTAS);

        } catch (Exception e) {
            System.out.println("Cant connect to db");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Carga los paises en la BD y si ya están agregados, manda un mensaje en consola
     * @throws SQLException
     */
    public void cargarPaises() throws SQLException {
        ResultSet resultadoPaises = queryPaises.executeQuery();
        if (resultadoPaises.next()) {
            System.out.println("Paises cargados");
        } else {
            for (String countryCode : locales) {
                Locale paises = new Locale("", countryCode);
                if (this.cmdInsertarPaises != null) {
                    this.cmdInsertarPaises.setString(1, paises.getDisplayCountry());
                    this.cmdInsertarPaises.setString(2, paises.getCountry());
                    this.cmdInsertarPaises.execute();
                } else {
                    System.out.println("No se pudieron insertar los paises");
                }
            }
        }
    }

    /**
     * Carga valores defaults en la base de datos, para así evitar problemas con las llaves foráneas
     * @throws SQLException
     */
    public void cargarDefaults() throws SQLException {
        Statement queryArtistas = connection.createStatement();
        ResultSet resultado = queryArtistas.executeQuery("select * from artista where nombreArtistico = 'Default'");

        Statement queryInsertarCancion = connection.createStatement();
        Statement queryInsertarGenero = connection.createStatement();
        Statement queryInsertarAlbum = connection.createStatement();
        Statement queryInsertarBibliotecaAlbum = connection.createStatement();

        Statement queryAlbum = connection.createStatement();
        Statement queryArtista = connection.createStatement();
        Statement queryCompositor = connection.createStatement();
        Statement queryGenero = connection.createStatement();
        Statement queryBiblioteca = connection.createStatement();
        Statement queryCancion = connection.createStatement();

        ResultSet resultadoAlbum = queryAlbum.executeQuery("select * from album where nombreAlbum = 'Default'");
        ResultSet resultadoArtista = queryArtista.executeQuery("select * from artista where nombreArtistico = 'Default'");
        ResultSet resultadoCompositor = queryCompositor.executeQuery("select * from compositor where nombre = 'Default'");
        ResultSet resultadoGenero = queryGenero.executeQuery("select * from genero where nombre = 'Default'");
        ResultSet resultadoCancion = queryCancion.executeQuery("select * from cancion where nombreCancion = 'Default'");

        if(resultadoGenero.next()) {
            System.out.println("Genero default ya agregado");
        } else {
            queryInsertarGenero.executeUpdate("insert into genero (nombre,descripcion) values ('Default','Genero default')");
        }
        if(resultado.next()) {
            System.out.println("Artista default ya creado");
        } else {
            resultadoGenero = queryGenero.executeQuery("select * from genero where nombre = 'Default'");
            if(resultadoGenero.next()) {
                queryArtistas.executeUpdate("insert into artista (nombre,apellido,nombreArtistico,fechaNacimiento,fechaFallecimiento,idPaisArtista,idGeneroArtista,edadArtista,descripcion) values ('Def','def','Default','2020-12-15','2020-12-16',51,"+resultadoGenero.getInt("idGenero")+",-1,'artista default')");
            }
        }

        if(resultadoCompositor.next()) {
            System.out.println("Compositor default ya creado");
        } else {
                queryArtistas.executeUpdate("insert into compositor (nombre,apellidos,idPaisCompositor,fechaNacimiento,edad) values ('Default','def',51,'2020-12-15',-1)");
        }

        if(resultadoCancion.next()) {
            System.out.println("Canción default ya agregada");
        } else {
            resultadoGenero = queryGenero.executeQuery("select * from genero where nombre = 'Default'");
            if(resultadoGenero.next()) {
                resultadoArtista = queryArtista.executeQuery("select * from artista where nombreArtistico = 'Default'");
                if(resultadoArtista.next()) {
                    queryInsertarCancion.executeUpdate("insert into cancion (nombreCancion,idArtistaCancion,idCompositorCancion,fechaLanzamiento,idGeneroCancion,cancionSimple,cancionCompra,precio,idAlbumCancion,recurso)" +
                            " values('Default',"+resultadoArtista.getInt("idArtista")+","+resultadoCompositor.getInt("idCompositor")+",'2020-12-16',"+resultadoGenero.getInt("idGenero")+",1,1,1,1,'def')" );
                }
                System.out.println("Cancion agregada");
            }
        }

        if(resultadoAlbum.next()) {
            System.out.println("Album default ya agregado");
        } else {
            resultadoArtista = queryArtista.executeQuery("select * from artista where nombreArtistico = 'Default'");
            if(resultadoArtista.next()) {
                queryInsertarAlbum.executeUpdate("insert into album (nombreAlbum,fechaLanzamiento,idArtistaAlbum,imagenAlbum) values ('Default','2020-12-16',"+resultadoArtista.getInt("idArtista")+",'def')");
            }
        }

        resultadoAlbum = queryAlbum.executeQuery("select * from album where nombreAlbum = 'Default'");
        if(resultadoAlbum.next()) {
            resultadoCancion = queryCancion.executeQuery("select * from cancion where nombreCancion = 'Default'");
            if(resultadoCancion.next()){
                queryInsertarBibliotecaAlbum.executeUpdate("insert into biblioteca_album (idAlbumBiblioteca,idCancionAlbumBiblioteca) values ("+resultadoAlbum.getInt("idAlbum")+","+resultadoCancion.getInt("idCancion")+")");
            }
        }
    }


    /**
     * Esta función carga, con los paises de la BD, cualquier ComboBox que reciba como parámetro
     * @param combo ComboBox que se desea cargar de paises
     * @throws SQLException
     */
    public void cargarPaisesComboBox(ComboBox<String> combo) throws SQLException {
        ResultSet resultadoPaises = queryPaises.executeQuery();
        while (resultadoPaises.next()) {
            combo.getItems().add(resultadoPaises.getString("nombrePais"));
        }
    }

    /**
     * Esta función carga, con los géneros de la BD, cualquier ComboBox que reciba como parámetro
     * @param combo ComboBox que se desea cargar de géneros musicales
     * @throws SQLException
     */
    public void cargarGenerosComboBox(ComboBox<String> combo) throws SQLException {
        ResultSet resultadoGeneros = queryGeneros.executeQuery();
        while (resultadoGeneros.next()) {
            combo.getItems().add(resultadoGeneros.getString("nombre"));
        }
    }

    /**
     * Esta función carga, con los artistas de la BD, cualquier ComboBox que reciba como parámetro
     * @param combo ComboBox que se desea cargar de artistas
     * @throws SQLException
     */
    public void cargarArtistasComboBox(ComboBox<String> combo) throws SQLException {
        ResultSet resultadoArtistas = queryArtistas.executeQuery();
        while (resultadoArtistas.next()) {
            combo.getItems().add(resultadoArtistas.getString("nombreArtistico"));
        }
    }

    /**
     * Esta función carga, con los compositores de la BD, cualquier ComboBox que reciba como parámetro
     * @param combo ComboBox que se desea cargar de compositores
     * @throws SQLException
     */
    public void cargarCompositoresComboBox(ComboBox<String> combo) throws SQLException {
        ResultSet resultadoCompositores = queryCompositores.executeQuery();
        while (resultadoCompositores.next()) {
            combo.getItems().add(resultadoCompositores.getString("nombre"));
        }
    }

    /**
     * Esta función carga, con los álbumes de la BD, cualquier ComboBox que reciba como parámetro
     * @param combo ComboBox que se desea cargar de álbumes
     * @throws SQLException
     */
    public void cargarAlbumesComboBox(ComboBox<String> combo) throws SQLException {
        ResultSet resultadoAlbumes = queryAlbumes.executeQuery();
        while (resultadoAlbumes.next()) {
            combo.getItems().add(resultadoAlbumes.getString("nombreAlbum"));
        }
    }

    /**
     * Esta función carga cualquier ComboBox que reciba como parámetro con tarjetas de un usuario en específico
     * @param combo ComboBox que se desea cargar de tarjetas de crédito
     * @throws SQLException
     */
    public void cargarTarjetasComboBox(ComboBox<String> combo, int idUsuario) throws SQLException {
        Statement queryTarjetas = connection.createStatement();
        ResultSet resultadoTarjetas = queryTarjetas.executeQuery("select * from metodo_pago where idClientePago = "+idUsuario);
        while (resultadoTarjetas.next()) {
            combo.getItems().add(resultadoTarjetas.getString("numeroTarjeta"));
        }
    }

    /**
     * Busca el pais según el nombre en la BD y devuelve el id del pais
     *
     * @param nombrePais nombre del país que se desea buscar
     * @return pais objeto pais
     * @throws SQLException
     */
    public int idPais(String nombrePais) throws SQLException {
        int resultado = 0;
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from pais where nombrePais = '" + nombrePais + "'");
        if (result.next()) {
            resultado = result.getInt("idPais");
        } else {
            System.out.println("No se encontró ningún país con ese nombre");
        }

        return resultado;
    }

    /**
     * Busca el pais según el nombre en la BD y devuelve un objeto Pais
     * @param nombrePais nombre del país que se desea buscar
     * @return pais objeto pais
     * @throws SQLException
     */
    public Pais pais(String nombrePais) throws SQLException {
        Pais pais = new Pais();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from pais where nombrePais = '" + nombrePais + "'");
        if (result.next()) {
            pais.setIdPais(result.getInt("idPais"));
            pais.setNombrePais(result.getString("nombrePais"));
            pais.setCodigoPais(result.getString("codigoPais"));
        } else {
            System.out.println("No se encontró ningún país con ese nombre");
        }

        return pais;
    }

    /**
     * Busca el pais según el id en la BD y devuelve un objeto Pais
     * @param idPais id del país que se desea buscar
     * @return pais objeto pais
     * @throws SQLException
     */
    public Pais getPaisById(int idPais) throws SQLException {
        Pais pais = new Pais();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from pais where idPais = " + idPais);
        if (result.next()) {
            pais.setIdPais(result.getInt("idPais"));
            pais.setNombrePais(result.getString("nombrePais"));
            pais.setCodigoPais(result.getString("codigoPais"));
        } else {
            System.out.println("No se encontró ningún país con ese nombre");
        }

        return pais;
    }

    /**
     * Devuelve los datos de una tarjeta específica de un usuario de la BD
     * @param idUsuario id del usuario con la tarjeta asociada
     * @param numeroTarjeta número de la tarjeta buscada para el usuario
     * @return
     * @throws SQLException
     */
    public MetodoPago getMetodoPagoByIdUsuario(int idUsuario, String numeroTarjeta) throws SQLException {
        MetodoPago tarjeta = new MetodoPago();
        Statement queryTarjetas = connection.createStatement();
        ResultSet resultadoTarjetas = queryTarjetas.executeQuery("select * from metodo_pago where idClientePago = "+idUsuario+" and numeroTarjeta = '"+numeroTarjeta+"'");
        if (resultadoTarjetas.next()) {
            tarjeta.setId(resultadoTarjetas.getInt("idMetodoPago"));
            tarjeta.setFechaVencimiento(resultadoTarjetas.getDate("fechaVencimiento").toLocalDate());
            tarjeta.setNumeroTarjeta(resultadoTarjetas.getString("numeroTarjeta"));
            tarjeta.setCodigoSeguridad(resultadoTarjetas.getInt("codigoSeguridad"));
            tarjeta.setUsuario(getUsuarioById(resultadoTarjetas.getInt("idClientePago")));
        } else {
            System.out.println("No se encontró ninguna tarjeta con ese número o con ese usuario asociado");
        }
        //System.out.println(tarjeta.toString());
        return tarjeta;
    }

    /**
     * Busca y devuelve un género musical de la base de datos
     * @param nomGenero recibe un String del nombre del género para buscar en la BD
     * @return genero Objeto Genero con los datos de la BD
     * @throws SQLException
     */
    public Genero getGenero(String nomGenero) throws SQLException {
        Genero genero = new Genero();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from genero where nombre = '" + nomGenero + "'");
        if (result.next()) {
            genero.setId(result.getInt("idGenero"));
            genero.setNombreGenero(result.getString("nombre"));
            genero.setDescripcionGenero(result.getString("descripcion"));
        } else {
            System.out.println("No se encontró ningún género con ese nombre");
        }

        return genero;
    }


    /**
     * Busca y devuelve un género musical de la base de datos
     * @param idGenero id del género para buscar en la BD
     * @return genero Objeto Genero con los datos de la BD
     * @throws SQLException
     */
    public Genero getGeneroById(int idGenero) throws SQLException {
        Genero genero = new Genero();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from genero where idGenero = " + idGenero);
        if (result.next()) {
            genero.setId(result.getInt("idGenero"));
            genero.setNombreGenero(result.getString("nombre"));
            genero.setDescripcionGenero(result.getString("descripcion"));
        } else {
            System.out.println("No se encontró ningún género con ese nombre");
        }

        return genero;
    }

    /**
     * Devuelve un artista encontrado en la BD
     * @param idArtista id del artista en la BD
     * @return Objeto Artista
     * @throws SQLException
     */
    public Artista getArtistaById(int idArtista) throws SQLException {
        Artista artista = new Artista();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from artista where idArtista = " + idArtista);
        if (result.next()) {
            artista.setId(result.getInt("idArtista"));
            artista.setNombreArtista(result.getString("nombre"));
            artista.setApellidoArtista(result.getString("apellido"));
            artista.setNombreArtistico(result.getString("nombreArtistico"));
            artista.setFechaNacimientoArtista(result.getDate("fechaNacimiento").toLocalDate());
            if(result.getDate("fechaFallecimiento") == null) {
                artista.setFechaFallecimientoArtista(LocalDate.now());
            } else {
                artista.setFechaFallecimientoArtista(result.getDate("fechaFallecimiento").toLocalDate());
            }
            artista.setPaisNacimiento(getPaisById(result.getInt("idPaisArtista")));
            artista.setGeneroMusicalArtista(getGeneroById(result.getInt("idGeneroArtista")));
            artista.setEdadArtista(result.getInt("edadArtista"));
            artista.setDescripcionArtista(result.getString("descripcion"));
        } else {
            System.out.println("No se encontró ningún artista con ese nombre");
        }
        return artista;
    }

    /**
     * Devuelve un artista de la BD
     * @param nomArtista nombre artístico del artista en la BD
     * @return Objeto Artista
     * @throws SQLException
     */
    public Artista getArtista(String nomArtista) throws SQLException {
        Artista artista = new Artista();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from artista where nombreArtistico = '" + nomArtista+"'");
        if (result.next()) {
            artista.setId(result.getInt("idArtista"));
            artista.setNombreArtista(result.getString("nombre"));
            artista.setApellidoArtista(result.getString("apellido"));
            artista.setNombreArtistico(result.getString("nombreArtistico"));
            artista.setFechaNacimientoArtista(result.getDate("fechaNacimiento").toLocalDate());
            if(result.getDate("fechaFallecimiento") == null) {
                artista.setFechaFallecimientoArtista(LocalDate.now());
            } else {
                artista.setFechaFallecimientoArtista(result.getDate("fechaFallecimiento").toLocalDate());
            }
            artista.setPaisNacimiento(getPaisById(result.getInt("idPaisArtista")));
            artista.setGeneroMusicalArtista(getGeneroById(result.getInt("idGeneroArtista")));
            artista.setEdadArtista(result.getInt("edadArtista"));
            artista.setDescripcionArtista(result.getString("descripcion"));
        } else {
            System.out.println("No se encontró ningún artista con ese nombre");
        }
        return artista;
    }

    /**
     * Devuelve un compositor de la BD
     * @param idCompositor id del compositor en la BD
     * @return Objeto Compositor
     * @throws SQLException
     */
    public Compositor getCompositorById(int idCompositor) throws SQLException {
        Compositor compositor = new Compositor();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from compositor where idCompositor = " + idCompositor);
        if (result.next()) {
            compositor.setId(result.getInt("idCompositor"));
            compositor.setNombre(result.getString("nombre"));
            compositor.setApellidos(result.getString("apellidos"));
            compositor.setPaisNacimientoCompositor(getPaisById(result.getInt("idPaisCompositor")));
            compositor.setFechaNacimientoCompositor(result.getDate("fechaNacimiento").toLocalDate());
            compositor.setEdadCompositor(result.getInt("edad"));
        } else {
            System.out.println("No se encontró ningún artista con ese nombre");
        }
        return compositor;
    }

    /**
     * Devuelve un compositor de la BD
     * @param nomCompositor id del compositor en la BD
     * @return Objeto Compositor
     * @throws SQLException
     */
    public Compositor getCompositor(String nomCompositor) throws SQLException {
        Compositor compositor = new Compositor();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from compositor where nombre = '" + nomCompositor+"'");
        if (result.next()) {
            compositor.setId(result.getInt("idCompositor"));
            compositor.setNombre(result.getString("nombre"));
            compositor.setApellidos(result.getString("apellidos"));
            compositor.setPaisNacimientoCompositor(getPaisById(result.getInt("idPaisCompositor")));
            compositor.setFechaNacimientoCompositor(result.getDate("fechaNacimiento").toLocalDate());
            compositor.setEdadCompositor(result.getInt("edad"));
        } else {
            System.out.println("No se encontró ningún artista con ese nombre");
        }
        return compositor;
    }

    /**
     * Devuelve una canción de la BD
     * @param idCancion id de la canción en la BD
     * @return Objeto Cancion
     * @throws SQLException
     */
    public Cancion getCancionById(int idCancion) throws SQLException {
        Cancion cancion = new Cancion();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from cancion where idCancion = " + idCancion);
        Compositor compositor = new Compositor();
        Artista artista = new Artista();
        Album album = new Album();
        if (result.next()) {
            cancion.setId(result.getInt("idCancion"));
            cancion.setNombreCancion(result.getString("nombreCancion"));
            if(result.getInt("idArtista") == 0) {
                cancion.setArtistaCancion(artista);
            } else {
                cancion.setArtistaCancion(getArtistaById(result.getInt("idArtista")));
            }
            if(result.getInt("idCompositor") == 0) {
                cancion.setCompositorCancion(compositor);
            }else {
                cancion.setCompositorCancion(getCompositorById(result.getInt("idCompositor")));
            }
            cancion.setFechaLanzamientoCancion(result.getDate("fechaLanzamiento").toLocalDate());
            cancion.setGeneroCancion(getGeneroById(result.getInt("idGeneroCancion")));
            cancion.setCancionSimple(result.getInt("cancionSimple"));
            if(result.getInt("idAlbum") == 0) {
                cancion.setAlbumCancion(album);
            } else {
                cancion.setAlbumCancion(getAlbumById(result.getInt("idAlbum")));
            }
            cancion.setRecurso(result.getString("recurso"));
            cancion.setCancionCompra(result.getInt("cancionCompra"));
        } else {
            System.out.println("No se encontró ninguna canción con ese nombre");
        }
        return cancion;
    }

    /**
     * Devuelve una canción de la BD
     * @param nomCancion id de la canción en la BD
     * @return Objeto Cancion
     * @throws SQLException
     */
    public Cancion getCancion(String nomCancion) throws SQLException {
        Cancion cancion = new Cancion();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from cancion where nombreCancion = '" + nomCancion+"'");
        Compositor compositor = new Compositor();
        Artista artista = new Artista();
        Album album = new Album();
        if (result.next()) {
            cancion.setId(result.getInt("idCancion"));
            cancion.setNombreCancion(result.getString("nombreCancion"));
            if(result.getInt("idArtista") == 0) {
                cancion.setArtistaCancion(artista);
            } else {
                cancion.setArtistaCancion(getArtistaById(result.getInt("idArtista")));
            }
            if(result.getInt("idCompositor") == 0) {
                cancion.setCompositorCancion(compositor);
            }else {
                cancion.setCompositorCancion(getCompositorById(result.getInt("idCompositor")));
            }
            cancion.setFechaLanzamientoCancion(result.getDate("fechaLanzamiento").toLocalDate());
            cancion.setGeneroCancion(getGeneroById(result.getInt("idGeneroCancion")));
            cancion.setCancionSimple(result.getInt("cancionSimple"));
            if(result.getInt("idAlbum") == 0) {
                cancion.setAlbumCancion(album);
            } else {
                cancion.setAlbumCancion(getAlbumById(result.getInt("idAlbum")));
            }
            cancion.setRecurso(result.getString("recurso"));
            cancion.setCancionCompra(result.getInt("cancionCompra"));
        } else {
            System.out.println("No se encontró ninguna canción con ese nombre");
        }
        return cancion;
    }

    /**
     * Devuelve una lista de reproduccion de un Usuario específico
     * Busca en la BD las canciones de la lista en la tabla biblioteca_lista_reproduccion
     * @param idUsuario id del Usuario de la lista
     * @return Objeto ListaReproduccion
     * @throws SQLException
     */
    public ListaReproduccion getListaReproduccion(int idUsuario, String nombreLista) throws SQLException {
        ListaReproduccion lista = new ListaReproduccion();
        ArrayList<Cancion> canciones = new ArrayList<>();

        Statement queryLista = connection.createStatement();
        ResultSet result = queryLista.executeQuery("select * from lista_reproduccion_usuario where idUsuarioLista = "+idUsuario+" and nombreList = '"+nombreLista+"'");

        Statement queryBiblioteca = connection.createStatement();
        if (result.next()) {
        ResultSet resultadoBiblioteca = queryBiblioteca.executeQuery("select * from biblioteca_lista_reproduccion where idListaReproduccionUsuario = "+result.getInt("idListaUsuario"));
            while(resultadoBiblioteca.next()) {
                canciones.add(getCancionById(resultadoBiblioteca.getInt("idCancionBibliotecaLista")));
            }
            lista.setId(result.getInt("idListaUsuario"));
            lista.setFechaCreacionListaReproduccion(result.getDate("fechaCreacion").toLocalDate());
            lista.setNombreListaReproduccion(result.getString("nombreLista"));
            lista.setCalificacionReproduccion(result.getInt("calificacion"));
        } else {
            System.out.println("No se encontró ninguna playlist asociada al usuario");
        }
        return lista;
    }

    /**
     * Devuelve todas las listas de reproduccion de un Usuario específico
     * Busca en la BD las canciones de la lista en la tabla biblioteca_lista_reproduccion
     * @param idUsuario id del Usuario de la lista
     * @return ArrayList del Objeto ListaReproduccion
     * @throws SQLException
     */
    public ArrayList<ListaReproduccion> getListasReproduccionesUsuario(int idUsuario) throws SQLException {

        ArrayList<Cancion> canciones = new ArrayList<>();
        ArrayList<ListaReproduccion> listas = new ArrayList<>();

        Statement queryLista = connection.createStatement();
        ResultSet result = queryLista.executeQuery("select * from lista_reproduccion_usuario where idUsuarioLista = "+idUsuario);

        Statement queryBiblioteca = connection.createStatement();
        while (result.next()) {
            ResultSet resultadoBiblioteca = queryBiblioteca.executeQuery("select * from biblioteca_lista_reproduccion where idListaReproduccionUsuario = "+result.getInt("idListaUsuario"));
            while(resultadoBiblioteca.next()) {
                canciones.add(getCancionById(resultadoBiblioteca.getInt("idCancionBibliotecaLista")));
            }
            ListaReproduccion lista = new ListaReproduccion();
            lista.setId(result.getInt("idListaUsuario"));
            lista.setFechaCreacionListaReproduccion(result.getDate("fechaCreacion").toLocalDate());
            lista.setNombreListaReproduccion(result.getString("nombreLista"));
            lista.setCalificacionReproduccion(result.getInt("calificacion"));
            listas.add(lista);
        }
        return listas;
    }

    /**
     * Devuelve un arrayList de canciones de la BD
     * @param idBibliotecaCanciones id de la biblioteca para almacenar canciones de un álbum
     * @return ArrayList del tipo de objeto Cancion
     * @throws SQLException
     */
    public ArrayList<Cancion> getArrayListCanciones(int idBibliotecaCanciones) throws SQLException {
        ArrayList<Cancion> canciones = new ArrayList<>();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from biblioteca_album where idBibliotecaAlbum = " + idBibliotecaCanciones);
        while (result.next()) {
            Cancion cancion = new Cancion();
            cancion = getCancionById(result.getInt("idCancionAlbumBiblioteca"));
            canciones.add(cancion);
        }
        return canciones;
    }

    /**
     * Devuelve un arrayList de canciones de la BD
     * @param idUsuario id del Usuario de la playList
     * @return ArrayList del tipo de objeto Cancion
     * @throws SQLException
     */
    /*
    public ArrayList<Cancion> getCancionesListaReproduccion(int idUsuario) throws SQLException {
        ArrayList<Cancion> canciones = new ArrayList<>();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from biblioteca_lista_reproduccion where idBibliotecaAlbum = " + idBibliotecaCanciones);
        while (result.next()) {
            Cancion cancion = new Cancion();
            cancion = getCancionById(result.getInt("idCancionAlbumBiblioteca"));
            canciones.add(cancion);
        }
        return canciones;
    }*/

    /**
     * Devuelve un álbum de la BD
     * @param idAlbum id del álbum en la BD
     * @return Objeto Album
     * @throws SQLException
     */
    public Album getAlbumById(int idAlbum) throws SQLException {
        Album album = new Album();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from album where idAlbum = " + idAlbum);
        if (result.next()) {
            album.setId(result.getInt("idAlbum"));
            album.setNombreAlbum(result.getString("nombreAlbum"));
            album.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            album.setArtistaAlbum(getArtistaById(result.getInt("idArtistaAlbum")));
            album.setImagenAlbum(result.getString("imagenAlbum"));
        } else {
            System.out.println("No se encontró ningún album con ese nombre");
        }
        return album;
    }

    /**
     * Devuelve un álbum de la BD
     * @param nomAlbum id del álbum en la BD
     * @return Objeto Album
     * @throws SQLException
     */
    public Album getAlbum(String nomAlbum) throws SQLException {
        Album album = new Album();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("select * from album where nombreAlbum = '" + nomAlbum+"'");
        if (result.next()) {
            album.setId(result.getInt("idAlbum"));
            album.setNombreAlbum(result.getString("nombreAlbum"));
            album.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            album.setArtistaAlbum(getArtistaById(result.getInt("idArtistaAlbum")));
            album.setImagenAlbum(result.getString("imagenAlbum"));
        } else {
            System.out.println("No se encontró ningún album con ese nombre");
        }
        return album;
    }

    /**
     * Devuelve un Observable list del tipo de objeto género para cargar una tabla
     * @return ObservableList del tipo de objeto Genero
     * @throws SQLException
     */
    public FilteredList<Genero> cargaGeneros() throws SQLException {
        ResultSet resultadoGeneros = queryGeneros.executeQuery();
        ObservableList<Genero> generos = FXCollections.observableArrayList();
        while(resultadoGeneros.next()) {
            Genero leido = new Genero();

            leido.setId(resultadoGeneros.getInt("idGenero"));
            leido.setNombreGenero(resultadoGeneros.getString("nombre"));
            leido.setDescripcionGenero(resultadoGeneros.getString("descripcion"));
            //System.out.println(leido.toString());
            generos.add(leido);
        }
        FilteredList<Genero> generosFiltrado = new FilteredList<>(FXCollections.observableList(generos));
        //System.out.println(generos);
        return generosFiltrado;
    }

    /**
     * Devuelve un Observable list del tipo de objeto Artista para cargar una tabla
     * @return ObservableList del tipo de objeto Artista
     * @throws SQLException
     */
    public FilteredList<Artista> cargaArtistas() throws SQLException {
        ResultSet resultadoArtistas = queryArtistas.executeQuery();
        ObservableList<Artista> artistas = FXCollections.observableArrayList();
        while(resultadoArtistas.next()) {
            Artista leido = new Artista();

            leido.setId(resultadoArtistas.getInt("idArtista"));
            leido.setNombreArtista(resultadoArtistas.getString("nombre"));
            leido.setApellidoArtista(resultadoArtistas.getString("apellido"));
            leido.setNombreArtistico(resultadoArtistas.getString("nombreArtistico"));
            leido.setFechaNacimientoArtista(resultadoArtistas.getDate("fechaNacimiento").toLocalDate());
            if(resultadoArtistas.getDate("fechaFallecimiento") == null) {
                leido.setFechaFallecimientoArtista(LocalDate.now());
            } else {
                leido.setFechaFallecimientoArtista(resultadoArtistas.getDate("fechaFallecimiento").toLocalDate());
            }
            leido.setPaisNacimiento(getPaisById(resultadoArtistas.getInt("idPaisArtista")));
            leido.setGeneroMusicalArtista(getGeneroById(resultadoArtistas.getInt("idGeneroArtista")));
            leido.setEdadArtista(resultadoArtistas.getInt("edadArtista"));
            leido.setDescripcionArtista(resultadoArtistas.getString("descripcion"));
            //System.out.println(leido.toString());
            artistas.add(leido);
        }
        FilteredList<Artista> generosFiltrado = new FilteredList<>(FXCollections.observableList(artistas));
        //System.out.println(generosFiltrado);
        return generosFiltrado;
    }


    /**
     * Devuelve un Observable list del tipo de objeto Artista para cargar una tabla
     * @return ObservableList del tipo de objeto Compositor
     * @throws SQLException
     */
    public FilteredList<Compositor> cargaCompositores() throws SQLException {
        ResultSet resultadoCompositor = queryCompositores.executeQuery();
        ObservableList<Compositor> compositores = FXCollections.observableArrayList();
        while(resultadoCompositor.next()) {
            Compositor leido = new Compositor();

            leido.setId(resultadoCompositor.getInt("idCompositor"));
            leido.setNombre(resultadoCompositor.getString("nombre"));
            leido.setApellidos(resultadoCompositor.getString("apellidos"));
            leido.setPaisNacimientoCompositor(getPaisById(resultadoCompositor.getInt("idPaisCompositor")));
            leido.setFechaNacimientoCompositor(resultadoCompositor.getDate("fechaNacimiento").toLocalDate());
            leido.setEdadCompositor(resultadoCompositor.getInt("edad"));
            //System.out.println(leido.toString());
            compositores.add(leido);
        }
        FilteredList<Compositor> compositoresFiltrado = new FilteredList<>(FXCollections.observableList(compositores));
        //System.out.println(generosFiltrado);
        return compositoresFiltrado;
    }

    /**
     * Devuelve un FilteredList para cargar una tabla del tipo Cancion
     * @return FilteredList del tipo de objeto Cancion
     * @throws SQLException
     */
    public FilteredList<Cancion> cargaCanciones() throws SQLException {
        ResultSet resultadoCancion = queryCanciones.executeQuery();
        ObservableList<Cancion> canciones = FXCollections.observableArrayList();
        Compositor compositor = new Compositor();
        Artista artista = new Artista();
        Album album = new Album();
        while(resultadoCancion.next()) {
            Cancion cancion = new Cancion();
            cancion.setId(resultadoCancion.getInt("idCancion"));
            cancion.setNombreCancion(resultadoCancion.getString("nombreCancion"));
            if(resultadoCancion.getInt("idArtistaCancion") == 0) {
                cancion.setArtistaCancion(artista);
            } else {
                cancion.setArtistaCancion(getArtistaById(resultadoCancion.getInt("idArtistaCancion")));
            }
            if(resultadoCancion.getInt("idCompositorCancion") == 0) {
                cancion.setCompositorCancion(compositor);
            }else {
                cancion.setCompositorCancion(getCompositorById(resultadoCancion.getInt("idCompositorCancion")));
            }
            if(String.valueOf(resultadoCancion.getInt("precio")) == null ||  resultadoCancion.getInt("precio") == 0) {
                cancion.setPrecioCancion(0);
            }  else {
                cancion.setPrecioCancion(resultadoCancion.getInt("precio"));
            }
            cancion.setFechaLanzamientoCancion(resultadoCancion.getDate("fechaLanzamiento").toLocalDate());
            cancion.setGeneroCancion(getGeneroById(resultadoCancion.getInt("idGeneroCancion")));
            cancion.setCancionSimple(resultadoCancion.getInt("cancionSimple"));
            if(resultadoCancion.getInt("idAlbumCancion") == 0) {
                cancion.setAlbumCancion(album);
            } else {
                cancion.setAlbumCancion(getAlbumById(resultadoCancion.getInt("idAlbumCancion")));
            }
            cancion.setRecurso(resultadoCancion.getString("recurso"));
            cancion.setCancionCompra(resultadoCancion.getInt("cancionCompra"));
            canciones.add(cancion);
        }
        FilteredList<Cancion> cancionesFilt = new FilteredList<>(FXCollections.observableList(canciones));
        //System.out.println(generosFiltrado);
        return cancionesFilt;
    }

    /**
     * Devuelve un FilteredList para cargar una tabla del tipo Cancion para un usuario específico
     * @param idUsuario id del usuario que se van a cargar las canciones
     * @return FilteredList del tipo de objeto Cancion
     * @throws SQLException
     */
    public FilteredList<Cancion> cargaCancionesUsuario(int idUsuario) throws SQLException {
        Statement queryCancUsr = connection.createStatement();
        ResultSet resCancionesUsr = queryCancUsr.executeQuery("select * from biblioteca_canciones_usuario where idUsuarioBiblioteca = "+idUsuario);

        ObservableList<Cancion> canciones = FXCollections.observableArrayList();
        Compositor compositor = new Compositor();
        Artista artista = new Artista();
        Album album = new Album();
        while(resCancionesUsr.next()) {
            System.out.println("Pasa la primera condicion");
            Statement queryCancion = connection.createStatement();
            ResultSet resultadoCancion = queryCancion.executeQuery("select * from cancion where idCancion = "+resCancionesUsr.getInt("idCancionBiblioteca"));
            if(resultadoCancion.next()) {
                Cancion cancion = new Cancion();
                cancion.setId(resultadoCancion.getInt("idCancion"));
                cancion.setNombreCancion(resultadoCancion.getString("nombreCancion"));
                if(resultadoCancion.getInt("idArtistaCancion") == 0) {
                    cancion.setArtistaCancion(artista);
                } else {
                    cancion.setArtistaCancion(getArtistaById(resultadoCancion.getInt("idArtistaCancion")));
                }
                if(resultadoCancion.getInt("idCompositorCancion") == 0) {
                    cancion.setCompositorCancion(compositor);
                }else {
                    cancion.setCompositorCancion(getCompositorById(resultadoCancion.getInt("idCompositorCancion")));
                }
                if(String.valueOf(resultadoCancion.getInt("precio")) == null ||  resultadoCancion.getInt("precio") == 0) {
                    cancion.setPrecioCancion(0);
                }  else {
                    cancion.setPrecioCancion(resultadoCancion.getInt("precio"));
                }
                cancion.setFechaLanzamientoCancion(resultadoCancion.getDate("fechaLanzamiento").toLocalDate());
                cancion.setGeneroCancion(getGeneroById(resultadoCancion.getInt("idGeneroCancion")));
                cancion.setCancionSimple(resultadoCancion.getInt("cancionSimple"));
                if(resultadoCancion.getInt("idAlbumCancion") == 0) {
                    cancion.setAlbumCancion(album);
                } else {
                    cancion.setAlbumCancion(getAlbumById(resultadoCancion.getInt("idAlbumCancion")));
                }
                cancion.setRecurso(resultadoCancion.getString("recurso"));
                cancion.setCancionCompra(resultadoCancion.getInt("cancionCompra"));
                canciones.add(cancion);
            } else {
                System.out.println("Ninguna canción para cargar del usuario");
            }

        }
        FilteredList<Cancion> cancionesFilt = new FilteredList<>(FXCollections.observableList(canciones));
        //System.out.println(generosFiltrado);
        return cancionesFilt;
    }


    /**
     * Devuelve un FilteredList para cargar una tabla del tipo ListaReproduccion
     * @return FilteredList del tipo de objeto ListaReproduccion
     * @throws SQLException
     */
    public FilteredList<ListaReproduccion> cargaListasReproduccion() throws SQLException {
        ResultSet resultadoListas = queryListas.executeQuery();

        ObservableList<ListaReproduccion> listas = FXCollections.observableArrayList();
        ArrayList<Cancion> canciones = new ArrayList<>();

        Compositor compositor = new Compositor();
        Artista artista = new Artista();
        Album album = new Album();

        //AQUÍ SE CARGAN LAS LISTAS
        while(resultadoListas.next()) {
            //System.out.println("Entra en lista de reproduccion");
            ListaReproduccion lista = new ListaReproduccion();
            lista.setId(resultadoListas.getInt("idListaUsuario"));
            lista.setNombreListaReproduccion(resultadoListas.getString("nombreLista"));
            lista.setFechaCreacionListaReproduccion(resultadoListas.getDate("fechaCreacion").toLocalDate());
            lista.setCalificacionReproduccion(resultadoListas.getInt("calificacion"));
            lista.setAutorLista(getUsuarioById(resultadoListas.getInt("idUsuarioLista")));

            Statement queryCancBiblioteca = connection.createStatement();
            ResultSet resultadoCancBiblioteca = queryCancBiblioteca.executeQuery("select * from biblioteca_lista_reproduccion where idListaReproduccionUsuario = "+resultadoListas.getInt("idListaUsuario") );
            //AQUI SE CARGAN LAS CANCIONES EN LA BIBLIOTECA
            while(resultadoCancBiblioteca.next()) {
                //System.out.println("Entra en biblioteca");
                Statement queryCanciones = connection.createStatement();
                ResultSet resultadoCancion = queryCanciones.executeQuery("select * from cancion where idCancion = "+resultadoCancBiblioteca.getInt("idCancionBibliotecaLista") );
                //AQUI SE BUSCAN LAS CANCIONES GUARDADAS EN LA BIBLIOTECA
                while(resultadoCancion.next()) {
                    //System.out.println("Entra en canciones");
                    Cancion cancion = new Cancion();
                    cancion.setId(resultadoCancion.getInt("idCancion"));
                    cancion.setNombreCancion(resultadoCancion.getString("nombreCancion"));
                    if(resultadoCancion.getInt("idArtistaCancion") == 0) {
                        cancion.setArtistaCancion(artista);
                    } else {
                        cancion.setArtistaCancion(getArtistaById(resultadoCancion.getInt("idArtistaCancion")));
                    }
                    if(resultadoCancion.getInt("idCompositorCancion") == 0) {
                        cancion.setCompositorCancion(compositor);
                    }else {
                        cancion.setCompositorCancion(getCompositorById(resultadoCancion.getInt("idCompositorCancion")));
                    }
                    if(String.valueOf(resultadoCancion.getInt("precio")) == null ||  resultadoCancion.getInt("precio") == 0) {
                        cancion.setPrecioCancion(0);
                    }  else {
                        cancion.setPrecioCancion(resultadoCancion.getInt("precio"));
                    }
                    cancion.setFechaLanzamientoCancion(resultadoCancion.getDate("fechaLanzamiento").toLocalDate());
                    cancion.setGeneroCancion(getGeneroById(resultadoCancion.getInt("idGeneroCancion")));
                    cancion.setCancionSimple(resultadoCancion.getInt("cancionSimple"));
                    if(resultadoCancion.getInt("idAlbumCancion") == 0) {
                        cancion.setAlbumCancion(album);
                    } else {
                        cancion.setAlbumCancion(getAlbumById(resultadoCancion.getInt("idAlbumCancion")));
                    }
                    cancion.setRecurso(resultadoCancion.getString("recurso"));
                    cancion.setCancionCompra(resultadoCancion.getInt("cancionCompra"));
                    canciones.add(cancion);
                }

            }
            lista.setCancionesListaReproduccion(canciones);
            listas.add(lista);
        }
        FilteredList<ListaReproduccion> listasFilt = new FilteredList<>(FXCollections.observableList(listas));
        //System.out.println(generosFiltrado);
        return listasFilt;
    }

    /**
     * Devuelve un FilteredList para cargar una tabla del tipo Album
     * @return FilteredList del tipo de objeto Album
     * @throws SQLException
     */
    public FilteredList<Album> cargaAlbumes() throws SQLException {
        ResultSet resultadoAlbum = queryAlbumes.executeQuery();
        ObservableList<Album> albumes = FXCollections.observableArrayList();
        while(resultadoAlbum.next()) {
            Album leido = new Album();

            leido.setId(resultadoAlbum.getInt("idAlbum"));
            leido.setNombreAlbum(resultadoAlbum.getString("nombreAlbum"));
            leido.setFechaLanzamiento(resultadoAlbum.getDate("fechaLanzamiento").toLocalDate());
            leido.setArtistaAlbum(getArtistaById(resultadoAlbum.getInt("idArtistaAlbum")));
            leido.setImagenAlbum(resultadoAlbum.getString("imagenAlbum"));

            albumes.add(leido);
        }
        FilteredList<Album> albumesFiltrado = new FilteredList<>(FXCollections.observableList(albumes));
        //System.out.println(generosFiltrado);
        return albumesFiltrado;
    }

    /**
     * Actualiza el género recibido en la BD
     * @param genero Objeto género que se actualizara
     * @throws SQLException
     */
    public void actualizarGenero(Genero genero) throws SQLException {
        generoDAO.actualizarGenero(genero);
        //System.out.println(genero.toString());
        alertasInformacion("Género", "Género actualizado exitosamente");
    }

    /**
     * Actualiza solo ciertos atributos del artista en la BD
     * @param id idArtista
     * @param nombre nombre Artista
     * @param apellidos apellidos Artista
     * @param nomArt nombre artístico del Artista
     * @param fechaFall fecha fallecimiento del Artista
     * @param nomPais nombre del país de nacimiento
     * @param descripcion descripción del artista
     * @throws SQLException
     */
    public void actualizarArtista(int id, String nombre, String apellidos, String nomArt, LocalDate fechaFall, String nomPais, String descripcion) throws SQLException {
        artistaDAO.actualizarArtista(id, nombre, apellidos, nomArt, fechaFall, idPais(nomPais),descripcion);
        //System.out.println(genero.toString());
        alertasInformacion("Artista", "Artista actualizado exitosamente");
    }

    /**
     * Actualiza un Compositor en la BD
     * @param id id del compositor
     * @param nombre nombre del compositor
     * @param apellidos apellidos del compositor
     * @param nomPais país nacimiento del compositor
     * @throws SQLException
     */
    public void actualizarCompositor(int id, String nombre, String apellidos, String nomPais) throws SQLException {
        compositorDAO.actualizarCompositor(id, nombre, apellidos, idPais(nomPais));
        //System.out.println(genero.toString());
        alertasInformacion("Compositor", "Compositor actualizado exitosamente");
    }

    /**
     * Actualiza una Cancion en la BD
     * @param id id de la canción
     * @param nombre nombre de la canción
     * @param artista artista de la canción
     * @param compositor compositor de la canción
     * @throws SQLException
     */
    public void actualizarCancion(int id, String nombre, String artista, String compositor) throws SQLException {

        cancionDAO.actualizarCancion(id,nombre,getArtista(artista).getId(),getCompositor(compositor).getId());
        //System.out.println(genero.toString());
        alertasInformacion("Canción", "Canción actualizada exitosamente");
    }

    /**
     * Elimina el genero recibido en la BD
     * @param genero Objeto Genero que se eliminara
     * @throws SQLException
     */
    public void eliminarGenero(Genero genero) throws SQLException {
        generoDAO.eliminarGenero(genero);
        alertasInformacion("Género","Género eliminado exitosamente");
    }

    /**
     * Elimina el artista recibido en la BD
     * @param id id del Artista que se eliminara
     * @throws SQLException
     */
    public void eliminarArtista(int id) throws SQLException {
        artistaDAO.eliminarArtista(id);
        alertasInformacion("Artista","Artista eliminado exitosamente");
    }

    /**
     * Elimina un Compositor en la BD
     * @param id id del compositor necesario para la eliminación
     * @throws SQLException
     */
    public void eliminarCompositor(int id) throws SQLException {
        compositorDAO.eliminarCompositor(id);
        alertasInformacion("Compositor","Compositor eliminado exitosamente");
    }

    /**
     * Elimina una Cancion de la BD
     * @param id id de la canción
     * @throws SQLException
     */
    public void eliminarCancion(int id) throws SQLException {
        cancionDAO.eliminarCancion(id);
        alertasInformacion("Canción","Canción eliminada exitosamente");
    }

    /**
     * Elimina un Album de la BD
     * @param id id del Album
     * @throws SQLException
     */
    public void eliminarAlbum(int id) throws SQLException {
        albumDAO.eliminarAlbum(id);
        alertasInformacion("Álbum","Álbum eliminado exitosamente");
    }

    /**
     * @param contrasenna Recibe como parametro el string de la contraseña a evaluar
     * @return boolean Devuelve un verdadero o falso según el resultado de la evaluacion del string de la contraseña
     */
    public boolean verificarContrasenna(String contrasenna) {

        int len = contrasenna.length();
        boolean verificacion = false;

        //String formato = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$";
        //String formato = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])([A-Za-z\d$@$!%*?&]|[^ ]){8,15}$/";
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";
        String pattern2 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}$";

        verificacion = contrasenna.matches(pattern2);
        return verificacion;
    }

    /**
     * @param query query que evalúa si existe la información
     * @return true or false según valide si hay información o no
     * @throws SQLException
     */
    public boolean siExiste(String query) throws SQLException {
        boolean verificacion = false;
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);
        verificacion = rs.next();
        return verificacion;
    }

    //------Bloque de agregar objetos a los ArrayList que los almacenan-------

    /**
     * @param avatar        directorio del archivo del avatar
     * @param nombre        String del nombre del admin
     * @param apellidos     String de los apellidos del admin
     * @param contrasenna   String de la contraseña del admin
     * @param correo        String del correo del admin
     * @param nombreUsuario String del usuario del admin
     * @throws SQLException
     */
    public void agregarAdmin(String avatar, String nombre, String apellidos, String correo, String contrasenna, String nombreUsuario) throws SQLException {
        Admin admin = new Admin(1, avatar, nombre, apellidos, correo, contrasenna, nombreUsuario);
        if (siExiste("select 1 from admin") == false) {
            try {
                adminDAO.guardarAdmin(admin);
                alertasInformacion("Registro","Administrador registrado con éxito");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            creacionAlertas("Ya existe un administrador registrado");
        }
    }

    /**
     * @param correo      String del correo para verificar sesión
     * @param contrasenna String de contraseña para verificar sesión
     * @return true or false según valide si la información corresponde al administrador
     * @throws SQLException
     */
    public boolean verificarSesionAdmin(String correo, String contrasenna) throws SQLException {
        boolean validacion = false;
        Statement query = connection.createStatement();
        ResultSet resultadoAdmin = query.executeQuery("select * from admin where correo = '" + correo + "'");
        //ResultSet resultadoUsuario = query.executeQuery("select * from usuario_final");
        if (correo != null && contrasenna != null) {
            //System.out.println(correo + ", "+contrasenna);
            if (resultadoAdmin.next()) {
                if (resultadoAdmin.getString("correo").equals(correo) && resultadoAdmin.getString("contrasenna").equals(contrasenna)) {
                    validacion = true;
                }
            }
        }

        return validacion;
    }

    /**
     * Guarda un usuario en la base de datos
     * @param avatar directorio de la imagen del avatar
     * @param nombre nombre del usuario
     * @param apellidos apellidos del usuario
     * @param correo correo del usuario
     * @param contrasenna contraseña del usuario
     * @param fechaNac fecha de nacimiento del usuario
     * @param nombrePais país de nacimiento del usuario
     * @param id identificación del usuario
     * @param nombreUsuario nombre de usuario de la app
     * @throws SQLException
     */
    public void agregarUsuario(String avatar, String nombre, String apellidos, String correo, String contrasenna, LocalDate fechaNac, String nombrePais, String id, String nombreUsuario) throws SQLException {
        int otp = 0;
        ArrayList<ListaReproduccion> listasRep = new ArrayList<>();
        ArrayList<Cancion> canciones = new ArrayList<>();
        Pais pais = pais(nombrePais);

        UsuarioFinal usuario = new UsuarioFinal(1, avatar, nombre, apellidos, correo, contrasenna, fechaNac, pais, id, nombreUsuario, otp, listasRep, canciones);
        String query = "select * from usuario_final where identificacion = '" + id + "'";
        if (siExiste(query) == false) {
            try {
                usuarioFinalDAO.guardarUsuario(usuario);
                alertasInformacion("Registro", "Usuario registrado con éxito");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            creacionAlertas("Ya existe un usuario con esa identificación registrado");
        }
    }


    /**
     * @param correo      String del correo de usuario para verificar sesión
     * @param contrasenna String de la contraseña del usuario para verificar sesión
     * @return true or false según valide si la información corresponde a un  usuario registrado
     * @throws SQLException
     */
    public Boolean verificarSesionUsuario(String correo, String contrasenna) throws SQLException {
        boolean validacion = false;
        Statement query = connection.createStatement();
        ResultSet resultado = query.executeQuery("select * from usuario_final where correo = '" + correo + "'");
        if (correo != null && contrasenna != null) {
            if (resultado.next()) {
                if (resultado.getString("correo").equals(correo) && resultado.getString("contrasenna").equals(contrasenna)) {
                    validacion = true;
                }
            }
        }

        return validacion;
    }

    /**
     * Esta función crea una alerta de erorr JavaFx
     *
     * @param x String que describe la alerta
     */
    public void creacionAlertas(String x) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(x);
        alert.showAndWait();
    }

    /**
     * Esta función crea alertas de información JavaFx
     *
     * @param titulo String del titulo de la alerta
     * @param info   String que describe la información de la alerta
     */
    public void alertasInformacion(String titulo, String info) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * Calcula la edad según una fecha localDate
     * @param fecha recibe un localDate para calcular la fecha
     * @return
     */
    public int calcEdad(LocalDate fecha) {
        Calendar c1 = Calendar.getInstance();
        int dia = c1.get(Calendar.DATE);
        int mes = c1.get(Calendar.MONTH);
        int annio = c1.get(Calendar.YEAR);
        int edad = 0;

        if (fecha.getMonthValue() < mes) { // si el mes de nacimiento ya pasó, ya cumplió años
            edad = annio - fecha.getYear();
        } else if (fecha.getDayOfMonth() < dia && fecha.getMonthValue() == mes) { // si el dia de nacimiento ya pasó, ya cumplió años
            edad = annio - fecha.getYear();
        } else if (fecha.getDayOfMonth() > dia && fecha.getMonthValue() == mes) { //si el mes de nacimiento es igual al actual y el dia de nacimiento es mayor al dia actual, aún no ha cumplido años este año
            edad = (annio - fecha.getYear()) - 1;
        } else if (fecha.getMonthValue() > mes) { //si el mes de nacimiento es mayor al actual, aún no ha cumplido años este año
            edad = (annio - fecha.getYear()) - 1;
        }

        return edad;
    }


    /**
     * Actualiza los datos del administrador
     * @param avatar        Path del avatar
     * @param nombre        Nombre actualizado del admin
     * @param apellidos     Apellidos actualizados del admin
     * @param nombreUsuario nombre de usuario actualizado del admin
     * @throws SQLException
     */
    public void actualizarAdmin(String avatar, String nombre, String apellidos, String nombreUsuario) throws SQLException {
        adminDAO.actualizarDatosAdmin(avatar, nombre, apellidos, nombreUsuario);
        alertasInformacion("Admin", "Admin actualizado exitosamente");
    }

    /**
     * Actualiza un usuario en la BD
     * @param avatar path de la imagen de perfil del usuario
     * @param nombre nombre del usuario
     * @param apellidos apellidos del usuario
     * @param fechaNac fecha de nacimiento del usuario
     * @param nombreUsuario nombre del usuario en la aplicacion
     * @param pais país nacimiento del usuario
     * @throws SQLException
     */
    public void actualizarUsuario(int idUsuario,String avatar, String nombre, String apellidos,LocalDate fechaNac, String nombreUsuario, String pais) throws SQLException {
        int idPais = idPais(pais);
        usuarioFinalDAO.actualizarDatosUsuario(idUsuario,avatar, nombre, apellidos, fechaNac,nombreUsuario, idPais);
        alertasInformacion("Usuario", "Usuario actualizado exitosamente");
    }

    /**
     * Devuelve el administrador de la base de datos
     * @return nuevo Objeto Admin de la BD
     * @throws SQLException
     */
    public Admin getAdmin() throws SQLException {
        ResultSet resultado = queryAdmin.executeQuery();
        Admin nuevo = new Admin();
        if (resultado.next()) {
            nuevo.setAvatarUsuario(resultado.getString("avatar"));
            nuevo.setNombre(resultado.getString("nombre"));
            nuevo.setApellidosUsuario(resultado.getString("apellidos"));
            nuevo.setCorreoUsuario(resultado.getString("correo"));
            nuevo.setContrasennaUsuario(resultado.getString("contrasenna"));
            nuevo.setNombreUsuarioAdmin(resultado.getString("nombreUsuario"));
        }

        return nuevo;
    }

    /**
     * Devuelve un usuario de la BD con el correo y la contraseña correctos
     * @param correo correo del usuario
     * @param contrasenna contraseña del usuario
     * @return Objeto UsuarioFinal
     * @throws SQLException
     */
    public UsuarioFinal getUsuario(String correo, String contrasenna) throws SQLException {
        Statement queryUsuario = connection.createStatement();
        ResultSet resultado = queryUsuario.executeQuery("select * from usuario_final where correo = '"+correo+"' and contrasenna = '"+contrasenna+"'");
        UsuarioFinal nuevo = new UsuarioFinal();
        ArrayList<Cancion> canciones = new ArrayList<>();
        ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();
        if (resultado.next()) {
            nuevo.setId(resultado.getInt("idusuariofinal"));
            nuevo.setAvatarUsuario(resultado.getString("avatar"));
            nuevo.setNombre(resultado.getString("nombre"));
            nuevo.setApellidosUsuario(resultado.getString("apellidos"));
            nuevo.setCorreoUsuario(resultado.getString("correo"));
            nuevo.setContrasennaUsuario(resultado.getString("contrasenna"));
            nuevo.setFechaNacimientoUsuario(resultado.getDate("fechaNacimiento").toLocalDate());
            nuevo.setPaisProcedenciaUsuario(getPaisById(resultado.getInt("idPais")));
            nuevo.setIdentificacionUsuario(resultado.getString("identificacion"));
            nuevo.setNombreUsuario(resultado.getString("nombreUsuario"));
            if(resultado.getInt("otp") == 0 || String.valueOf(resultado.getInt("otp")) == null ) {
                nuevo.setOtp(0);
            } else {
                nuevo.setOtp(resultado.getInt("otp"));
            }

        }

        return nuevo;
    }


    public UsuarioFinal getUsuarioById(int idUsuario) throws SQLException {
        Statement queryUsuario = connection.createStatement();
        ResultSet resultado = queryUsuario.executeQuery("select * from usuario_final where idusuariofinal = "+idUsuario);
        UsuarioFinal nuevo = new UsuarioFinal();
        ArrayList<Cancion> canciones = new ArrayList<>();
        ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();
        if (resultado.next()) {
            nuevo.setId(resultado.getInt("idusuariofinal"));
            nuevo.setAvatarUsuario(resultado.getString("avatar"));
            nuevo.setNombre(resultado.getString("nombre"));
            nuevo.setApellidosUsuario(resultado.getString("apellidos"));
            nuevo.setCorreoUsuario(resultado.getString("correo"));
            nuevo.setContrasennaUsuario(resultado.getString("contrasenna"));
            nuevo.setFechaNacimientoUsuario(resultado.getDate("fechaNacimiento").toLocalDate());
            nuevo.setPaisProcedenciaUsuario(getPaisById(resultado.getInt("idPais")));
            nuevo.setIdentificacionUsuario(resultado.getString("identificacion"));
            nuevo.setNombreUsuario(resultado.getString("nombreUsuario"));
            if(resultado.getInt("otp") == 0 || String.valueOf(resultado.getInt("otp")) == null ) {
                nuevo.setOtp(0);
            } else {
                nuevo.setOtp(resultado.getInt("otp"));
            }

        }

        return nuevo;
    }

    /**
     * Guarda los datos del compositor en la base de datos
     * @param nombre nombre del compositor
     * @param apellidos apellidos del compositor
     * @param paisNacimiento pais de nacimiento del compositor
     * @param fechaNacimiento fecha de nacimiento del compositor
     * @throws SQLException
     */
    public void guardarCompositor(String nombre, String apellidos, String paisNacimiento, LocalDate fechaNacimiento) throws SQLException {
            Pais paisNac = pais(paisNacimiento);
            int edadCompositor = calcEdad(fechaNacimiento);
            Compositor compositor = new Compositor(1,nombre,apellidos,paisNac,fechaNacimiento,edadCompositor);
            if(siExiste("select * from compositor where nombre = '"+nombre+"' and apellidos = '"+apellidos+"'") == false) {
                compositorDAO.guardarCompositor(compositor);
                alertasInformacion("Compositor", "Compositor creado exitosamente");
            } else {
                creacionAlertas("Compositor ya existente");
            }

    }

    /**
     * Guarda un artista en la base de datos
     * @param nombre nombre del artista
     * @param apellidos apellidos del artista
     * @param nombreArtistico nombre artístico
     * @param fechaNacimiento fecha nacimiento del artista
     * @param fechaFallecimiento fecha fallecimiento del artista, si aplica
     * @param paisNacimiento país nacimiento del artista
     * @param generoMusical género musical del artista
     * @param descripcion descripción del artista
     * @throws SQLException
     */
    public void guardarArtista(String nombre, String apellidos, String nombreArtistico,LocalDate fechaNacimiento, LocalDate fechaFallecimiento, String paisNacimiento, String generoMusical, String descripcion) throws SQLException {
        Pais paisNac = pais(paisNacimiento);
        int edadArtista = calcEdad(fechaNacimiento);
        Genero generoArt = getGenero(generoMusical);
        Artista artista = new Artista(1,nombre,apellidos,nombreArtistico,fechaNacimiento,fechaFallecimiento,paisNac,generoArt,edadArtista,descripcion);
        if(siExiste("select * from artista where nombreArtistico = '"+nombreArtistico+"'") == false) {
            artistaDAO.guardarArtista(artista);
            alertasInformacion("Artista", "Artista creado exitosamente");
        } else {
            creacionAlertas("Artista ya existente");
        }

    }

    /**
     * Guarda una canción en la BD
     * @param nombre nombre de la canción
     * @param artista artista de la canción si es el caso
     * @param compositor compositor de la canción si es el caso
     * @param fechaLanz fecha de lanzamiento de la canción
     * @param genero género de la canción
     * @param album álbum de la canción
     * @param precio precio de la canción
     * @param recurso dirección del archivo de audio
     * @throws SQLException
     */
    public void guardarCancion(String nombre,String artista, String compositor,LocalDate fechaLanz, String genero,String album, int precio,String recurso) throws SQLException {
        Artista artis = getArtista(artista);
        Compositor compos = getCompositor(compositor);
        Genero gen = getGenero(genero);
        Album albumCancion = getAlbum(album);
        int cancionSimple = 0;
        int cancionCompra = 1;

        if(albumCancion.getNombreAlbum().equals("Default")) {
            cancionSimple = 2;
        } else {
            cancionSimple = 1;
        }

        Cancion cancion = new Cancion(1,nombre,artis,compos,fechaLanz,gen,cancionSimple,cancionCompra,precio,albumCancion, recurso);
        if(siExiste("select * from cancion where nombreCancion = '"+nombre+"'") == false) {
            cancionDAO.guardarCancion(cancion);
            alertasInformacion("Canción", "Canción agregada exitosamente");
        } else {
            creacionAlertas("Canción ya existente");
        }

    }

    /**
     * Guarda la canción comprada del usuario, en su propia biblioteca de canciones
     * @param usuario usuario que compró la canción
     * @param cancion Objeto Cancion comprado
     */
    public void guardarCancionUsuarioBiblioteca(UsuarioFinal usuario,Cancion cancion) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet resultado = query.executeQuery("select * from biblioteca_canciones_usuario where idUsuarioBiblioteca = "+usuario.getId()+" and idCancionBiblioteca = "+cancion.getId());
        if(resultado.next()) {
            creacionAlertas("La canción ya ha sido comprada");
        } else {
            try {
                cancionDAO.guardarCancionBiblioteca(usuario,cancion);
                alertasInformacion("Canción","Canción comprada exitosamente");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    /**
     * Guarda una canción en la BD
     * @param idUsuario id del usuario en que se va a guardar la cancion
     * @param nombre nombre de la canción
     * @param artista artista de la canción si es el caso
     * @param compositor compositor de la canción si es el caso
     * @param fechaLanz fecha de lanzamiento de la canción
     * @param genero género de la canción
     * @param album álbum de la canción
     * @param recurso dirección del archivo de audio
     * @throws SQLException
     */
    public void guardarCancionUsuario(int idUsuario,String nombre,String artista, String compositor,LocalDate fechaLanz, String genero,String album,String recurso) throws SQLException {
        Artista artis = getArtista(artista);
        Compositor compos = getCompositor(compositor);
        Genero gen = getGenero(genero);
        Album albumCancion = getAlbum(album);
        int cancionSimple = 0;
        int cancionCompra = 1;

        if(albumCancion.getNombreAlbum().equals("Default")) {
            cancionSimple = 2;
        } else {
            cancionSimple = 1;
        }

        Cancion cancion = new Cancion(1,nombre,artis,compos,fechaLanz,gen,cancionSimple,cancionCompra,0,albumCancion, recurso);
        if(siExiste("select * from cancion where nombreCancion = '"+nombre+"'") == false) {
            cancionDAO.guardarCancionUsuario(idUsuario,cancion);
            alertasInformacion("Canción", "Canción agregada exitosamente");
        } else {
            creacionAlertas("Canción ya existente");
        }

    }

    /**
     * Guarda un género musical en la base de datos
     * @param nombre nombre del género
     * @param descripcion descripción del género
     * @throws SQLException
     */
    public void guardarGenero(String nombre, String descripcion) throws SQLException {
        Genero genero = new Genero(1,nombre,descripcion);
        if(siExiste("select * from genero where nombre = '"+nombre+"'") == false) {
            generoDAO.guardarGenero(genero);
            alertasInformacion("Género", "Género creado exitosamente");
        } else {
            creacionAlertas("Género existente");
        }
    }

    /**
     * Guarda un álbum en la base de datos
     * @param album Objeto Album que se va a guardar
     * @throws SQLException
     */
    public void guardarAlbum(Album album) throws SQLException {
        if(siExiste("select * from album where nombreAlbum = '"+album.getNombreAlbum()+"'") == false) {
            albumDAO.guardarAlbum(album);
            alertasInformacion("Álbum", "Álbum creado exitosamente");
        } else {
            creacionAlertas("Álbum existente");
        }
    }


    /**
     * Guarda las canciones de un álbum en la base de datos
     * @param album Objeto Album que contiene el ArrayList de canciones que se van a guardar
     * @throws SQLException
     */
    public void actualizarAlbum(Album album) throws SQLException {
        if(siExiste("select * from album where nombreAlbum = '"+album.getNombreAlbum()+"'") == false) {
            creacionAlertas("Álbum no encontrado");
        } else {
            albumDAO.agregarCancionesBiblioteca(album);
            alertasInformacion("Canciones", "Canciones agregadas exitosamente");
        }

    }

    /**
     * Registra un método de pago de la BD
     * @param idUsuario id del usuario de la tarjeta que se guarda
     * @param numeroTarjeta numero de la tarjeta que se guarda
     * @param fechaVencimiento fecha de vencimiento de la tarjeta
     * @param ccv codigo de seguridad de la tarjeta
     * @throws SQLException
     */
    public void registrarMetodoPago(int idUsuario, String numeroTarjeta, LocalDate fechaVencimiento, String ccv) throws SQLException {
        MetodoPago metodo = new MetodoPago();
        metodo.setUsuario(getUsuarioById(idUsuario));
        metodo.setNumeroTarjeta(numeroTarjeta);
        metodo.setFechaVencimiento(fechaVencimiento);
        metodo.setCodigoSeguridad(Integer.valueOf(ccv));
        //System.out.println(metodo.toString());
        metodoPagoDAO.guardarMetodoPago(metodo);
        alertasInformacion("Método de pago", "Método de pago (tarjeta) guardado exitosamente");
    }

    /**
     * Elimina un método de pago de la BD
     * @param idUsuario id del usuario de la tarjeta
     * @param numeroTarjeta numero de la tarjeta que se eliminara
     * @throws SQLException
     */
    public void eliminarTarjeta(int idUsuario, String numeroTarjeta) throws SQLException {
        metodoPagoDAO.eliminarMetodoPago(getMetodoPagoByIdUsuario(idUsuario,numeroTarjeta));
        alertasInformacion("Método de pago", "Método de pago (tarjeta) eliminado exitosamente");
    }

    /**
     * Guarda una lista de reproducción de un usuario específico en la base de datos
     * @param lista lista de reproduccion que se va a guardar en la BD
     * @throws SQLException
     */
    public void guardarListaReproduccion(ListaReproduccion lista) throws SQLException {
        listaReproduccionDAO.guardarListaReproduccion(lista);
        alertasInformacion("Lista de reproducción", "Lista de reproducción añadida exitosamente");
    }

    //--CAMBIO ESCENA LOGIN--

    /**
     * Cambia la escena del login a usuario o administrador, dependiendo los datos ingresados
     * @param event evento que se genera cuandose aprieta el boton de login
     * @param window escenario
     * @param login FXML que se va a cargar
     * @param usuario usuario de la sesion
     * @throws IOException
     */
    public void manejoEscenasLogin(ActionEvent event, Stage window, Parent login,UsuarioFinal usuario, String path) throws IOException {
        //System.out.println(usuario.toString()+ " x");
        usuarioSesion = usuario;

        URL url = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlMenuUsuario controller = loader.getController();
        controller.setUsuarioSesion(usuario);

    }



    //--CARGA DE ESCENAS ADMIN--
    /**
     * Este método carga el escenario de canciones
     * @param event evento que se genera cuando se aprieta el botón de canciones
     * @throws IOException
     */
    public void escenarioCanciones(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminCanciones.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de guardar canciones
     * @param event evento que se genera cuando se aprieta el botón de registrar canciones
     * @throws IOException
     */
    public void escenarioGuardarCanciones(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/agregarCanciones.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }
    
    /**
     * Este método carga el escenario de compositores
     * @param event evento que se genera cuando se aprieta el botón de compositores
     * @throws IOException
     */
    public void escenarioCompositores(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminCompositores.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de agregar compositores
     * @param event evento que se genera cuando se aprieta el botón de crear compositor
     * @throws IOException
     */
    public void escenarioCrearCompositores(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearCompositores.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }
    

    /**
     * Este método carga el escenario de inicio de admin
     * @param event evento que se genera cuando se aprieta el botón de inicio
     * @throws IOException
     */
    public void escenarioInicioAdmin(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdmin.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de artistas
     * @param event evento que se genera cuando se aprieta el botón de artistas
     * @throws IOException
     */
    public void escenarioArtistas(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminArtistas.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de agregar artistas
     * @param event evento que se genera cuando se aprieta el botón de crear artista
     * @throws IOException
     */
    public void escenarioCrearArtistas(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearArtistas.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de géneros
     * @param event evento que se genera cuando se aprieta el botón de géneros
     * @throws IOException
     */
    public void escenarioGeneros(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminGeneros.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de crear géneros
     * @param event evento que se genera cuando se aprieta el botón de registrar géneros
     * @throws IOException
     */
    public void escenarioCrearGeneros(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearGeneros.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de álbumes
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void escenarioAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminAlbumes.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de crear álbumes
     * @param event evento que se genera cuando se aprieta el botón de crear álbumes
     * @throws IOException
     */
    public void escenarioCrearAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearAlbumes.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de actualizar álbumes
     * @param event evento que se genera cuando se aprieta el botón de actualizar álbum
     * @throws IOException
     */
    public void escenarioActualizarAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/agregarCancionesAlbum.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }
    
    //--FIN CARGA DE ESCENAS ADMIN--

    //--CARGA DE ESCENAS USUARIO--
    /**
     * Este método carga el escenario de inicio de admin
     * @param event evento que se genera cuando se aprieta el botón de inicio
     * @throws IOException
     */
    public void escenarioInicioUsuario(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/menuUsuario.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlMenuUsuario controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/menuUsuario.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de canciones del usuario
     * @param event evento que se genera cuando se aprieta el botón de canciones
     * @throws IOException
     */
    public void escenarioCancionesUsuario(ActionEvent event, Stage window,UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/cancionesUsuario.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlCanciones controller = loader.getController();
        controller.setUsuarioSesion(usuario);

        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/cancionesUsuario.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de guardar canciones del usuario
     * @param event evento que se genera cuando se aprieta el botón de registrar canciones
     * @throws IOException
     */
    public void escenarioSubirCancionesUsuario(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/subirCancion.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlSubirCancion controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/subirCancion.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de comprar canción del usuario
     * @param event evento que se genera cuando se aprieta el botón de comprar canción
     * @throws IOException
     */
    public void escenarioComprarCancionUsuario(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/comprarCancion.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlComprarCancion controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/comprarCancion.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de listas de reproducción del Usuario
     * @param event evento que se genera cuando se aprieta el botón de listas de reproduccion
     * @throws IOException
     */
    public void escenarioListasReproduccionUsuario(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/listasReproduccionUsuario.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlListasReproduccion controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/listasReproduccionUsuario.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }


    /**
     * Este método carga el escenario de álbumes para el usuario
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void escenarioAlbumesUsuario(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/albumesUsuario.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlMenuUsuario controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/albumesUsuario.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de métodos de pago del usuario
     * @param event evento que se genera cuando se aprieta el botón de métodos de pago
     * @throws IOException
     */
    public void escenarioMetodosPago(ActionEvent event, Stage window, UsuarioFinal usuario) throws IOException {
        URL url = getClass().getResource("../../vistas/vistas_usuario/metodosPago.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(url);
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
        CtrlMetodosPago controller = loader.getController();
        controller.setUsuarioSesion(usuario);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_usuario/metodosPago.fxml"));
        Scene vistaLogin = new Scene(login);
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();

         */
    }

    /**
     * Este método carga el escenario de agregar artistas
     * @param event evento que se genera cuando se aprieta el botón de crear artista
     * @throws IOException
     */
    public void aescenarioCrearArtistas(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearArtistas.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de géneros
     * @param event evento que se genera cuando se aprieta el botón de géneros
     * @throws IOException
     */
    public void aescenarioGeneros(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminGeneros.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de crear géneros
     * @param event evento que se genera cuando se aprieta el botón de registrar géneros
     * @throws IOException
     */
    public void aescenarioCrearGeneros(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearGeneros.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de álbumes
     * @param event evento que se genera cuando se aprieta el botón de álbumes
     * @throws IOException
     */
    public void aescenarioAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/menuAdminAlbumes.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de crear álbumes
     * @param event evento que se genera cuando se aprieta el botón de crear álbumes
     * @throws IOException
     */
    public void aescenarioCrearAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/crearAlbumes.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    /**
     * Este método carga el escenario de actualizar álbumes
     * @param event evento que se genera cuando se aprieta el botón de actualizar álbum
     * @throws IOException
     */
    public void aescenarioActualizarAlbumes(ActionEvent event, Stage window) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../../vistas/vistas_admin/agregarCancionesAlbum.fxml"));
        Scene vistaLogin = new Scene(login);

        //Esta linea agarra la informacion del escenario (stage o window)
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(vistaLogin);
        window.show();
    }

    //--FIN CARGA DE ESCENAS USUARIO--
}



