package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryProductRepository;
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

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    public VistaPrincipalControler(InMemoryPendingOrderRepository inMemoryPendingOrderRepository,InMemoryProductRepository inMemoryProductRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository) {
        this.inMemoryPendingOrderRepository =inMemoryPendingOrderRepository;
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void vistaPedidosPendientes(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPedidosPendientesControler vistaPedidosPendientesControler = new VistaPedidosPendientesControler(inMemoryPendingOrderRepository, inMemoryProductRepository, inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaPedidosPendientesControler, "/vista/vista_pedidos_pendientes.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void vistaHistorialPedidos(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         VistaHistorialPedidosControler vistaHistorialPedidosControler = new VistaHistorialPedidosControler(inMemoryProductRepository,inMemoryArchiveHistoryOrderRepository, inMemoryPendingOrderRepository);
            ChangeScene.change(stage, vistaHistorialPedidosControler, "/vista/vista_historial_pedidos.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
