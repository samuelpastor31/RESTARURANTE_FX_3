package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VistaPrincipalControler implements Initializable {

    @FXML
    private Button PedidosPendientesButton;

    @FXML
    private Button HistorialPedidosButton;

    @FXML
    private Button GestionDePedidosButton;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    public VistaPrincipalControler(InMemoryPendingOrderRepository inMemoryPendingOrderRepository, ProductRepository productRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository) {
        this.inMemoryPendingOrderRepository =inMemoryPendingOrderRepository;
        this.productRepository = productRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void vistaPedidosPendientes(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPedidosPendientesControler vistaPedidosPendientesControler = new VistaPedidosPendientesControler(inMemoryPendingOrderRepository, productRepository, inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaPedidosPendientesControler, "/vista/vista_pedidos_pendientes.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void vistaGestionProductos(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaGestionProductos vistaGestionProductos = new VistaGestionProductos(productRepository, inMemoryArchiveHistoryOrderRepository, inMemoryPendingOrderRepository);
            ChangeScene.change(stage, vistaGestionProductos, "/vista/vista_gestion_productos.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void vistaHistorialPedidos(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         VistaHistorialPedidosControler vistaHistorialPedidosControler = new VistaHistorialPedidosControler(productRepository,inMemoryArchiveHistoryOrderRepository, inMemoryPendingOrderRepository);
            ChangeScene.change(stage, vistaHistorialPedidosControler, "/vista/vista_historial_pedidos.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
