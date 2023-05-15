package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaDetalleProducto implements Initializable {

    @FXML
    private Label x;
    @FXML
    private Label tipo;
    @FXML
    private Label nombre;
    @FXML
    private Label precio;
    @FXML
    private Label descuento;
    @FXML
    private Label iva;
    @FXML
    private Label pvp;
    @FXML
    private Button botonDarBaja;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonAtras;

    private Initializable padreControler;

    private String vistaPadre;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    private ArrayList<Product> products;

    public VistaDetalleProducto(Initializable padreControler, String vistaPadre, ProductRepository productRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository) {
        this.padreControler = padreControler;
        this.vistaPadre = vistaPadre;
        this.productRepository = productRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
        this.products = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        x.setText(String.valueOf(products.get(0).getCod()));
        tipo.setText(String.valueOf(products.get(0).getClass().getSimpleName()));
        nombre.setText(products.get(0).getName());
        precio.setText(String.format("%.2f€",(products.get(0).getPrizeWithoutDiscount())));
        descuento.setText(String.format("%.0f%% de descuento",(products.get(0).getPercentageDiscount())));
        iva.setText(String.format("%.0f%% de iva", (products.get(0).getVat())));
        pvp.setText(pvp.getText()+" "+(String.format("%.0f€",products.get(0).getPrize())));
    }

    @FXML
    void atras(MouseEvent event){
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaGestionProductos vistaGestionProductos = new VistaGestionProductos(productRepository,inMemoryArchiveHistoryOrderRepository,inMemoryPendingOrderRepository);
            ChangeScene.change(stage, vistaGestionProductos, "/vista/vista_gestion_productos.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void añadirPedido(Product product){
            products.add(product);
    }

    @FXML
    void darBaja(MouseEvent event) {
        try {
            Product selectedProduct = products.get(0);
            if (selectedProduct != null) {
                try {
                    selectedProduct.setDadoAlta(false);
                    productRepository.save(selectedProduct); // cambiar estado a "dado de baja" en memoria
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mostrarAlerta("Producto dado de baja");
                atras(event);
            }
        } catch (DatabaseErrorException ex) {
           mostrarAlertaError(ex.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }



}
