package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VistaHistorialPedidosControler implements Initializable {


    @FXML
    private ListView<Order> listViewHistorialPedidos;

    @FXML
    private Button botonAtras;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    public VistaHistorialPedidosControler( InMemoryProductRepository inMemoryProductRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository) {
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewHistorialPedidos.setItems(getData());
    }

    private ObservableList<Order> getData(){
        return FXCollections.observableArrayList(inMemoryArchiveHistoryOrderRepository.findAll());
    }

    @FXML
    void atras(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPrincipalControler vistaPrincipalControler = new VistaPrincipalControler(inMemoryPendingOrderRepository,inMemoryProductRepository,inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaPrincipalControler, "/vista/vista_principal.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void verDetalle(MouseEvent event) {
        Order pedidoSeleccionado = listViewHistorialPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {

            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                VistaDetallePedido vistaDetallePedido = new VistaDetallePedido(this,"/vista/vista_historial_pedidos.fxml");
                vistaDetallePedido.añadirPedido(pedidoSeleccionado);
                ChangeScene.change(stage, vistaDetallePedido, "/vista/vista_detalle_pedido.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
