package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import es.progcipfpbatoi.utils.AlertMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class VistaGestionProductos implements Initializable {

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Button botonAñadir;

    @FXML
    private Button botonDarBaja;

    @FXML
    private Button botonVerDetalle;

    @FXML
    private Button botonAtras;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    public VistaGestionProductos(ProductRepository productRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository) {
        this.productRepository = productRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productListView.setItems(getData());
        desactivarButtons();
        productListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                activarButtons();
            } else {
                desactivarButtons();
            }
        });
    }

    @FXML
    void darBaja(MouseEvent event){
        try {
            Product selectedProduct = productListView.getSelectionModel().getSelectedItem();

            selectedProduct.setDadoAlta(false);
            productRepository.save(selectedProduct);

            ObservableList<Product> productsAlta = productListView.getItems();
            productsAlta.remove(selectedProduct);
            AlertMessages.mostrarAlertError("Producto dado de baja");

            desactivarButtons();
        }catch (DatabaseErrorException ex) {
            // Alert
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void activarButtons(){
        botonVerDetalle.setDisable(false);
        botonDarBaja.setDisable(false);
    }
    private void desactivarButtons(){
        botonVerDetalle.setDisable(true);
        botonDarBaja.setDisable(true);
    }

    private ObservableList<Product> getData() {
        try {
            return FXCollections.observableArrayList(productRepository.findAllAvailable());
        }catch ( DatabaseErrorException ex) {
            ex.printStackTrace();
            AlertMessages.mostrarAlertError(ex.getMessage());
            return null;
        }
    }

    @FXML
    void atras(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPrincipalControler vistaPrincipalControler = new VistaPrincipalControler(inMemoryPendingOrderRepository, productRepository,inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaPrincipalControler, "/vista/vista_principal.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void añadir(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaNuevoProducto vistaNuevoProducto = new VistaNuevoProducto(productRepository,inMemoryArchiveHistoryOrderRepository,inMemoryPendingOrderRepository,this,"/vista/vista_gestion_productos.fxml");
            ChangeScene.change(stage, vistaNuevoProducto, "/vista/vista_nuevo_producto.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void verDetalle(MouseEvent event) {
        Product product = productListView.getSelectionModel().getSelectedItem();
        if (product != null) {

            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                VistaDetalleProducto vistaDetalleProducto = new VistaDetalleProducto(productRepository,  inMemoryPendingOrderRepository,inMemoryArchiveHistoryOrderRepository);
                vistaDetalleProducto.añadirPedido(product);
                ChangeScene.change(stage, vistaDetalleProducto, "/vista/vista_detalle_producto.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }




}
