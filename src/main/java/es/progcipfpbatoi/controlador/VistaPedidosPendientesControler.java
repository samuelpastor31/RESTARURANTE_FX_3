package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;


public class VistaPedidosPendientesControler implements Initializable {

    @FXML
    private ListView<Order> listViewPedidos;

    @FXML
    private Button botonAtras;

    @FXML
    private Button botonNuevoPedido;

    @FXML
    private Button botonPrepararPedido;

    @FXML
    private Button botonVerDetalle;

    @FXML
    private Button botonCancelarPedido;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;



    public VistaPedidosPendientesControler(InMemoryPendingOrderRepository inMemoryPendingOrderRepository, InMemoryProductRepository inMemoryProductRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository) {
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryArchiveHistoryOrderRepository= inMemoryArchiveHistoryOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewPedidos.setItems(getData());
        botonPrepararPedido.setDisable(true);
        if (listViewPedidos.getItems().size()>0){
            botonPrepararPedido.setDisable(false);
        }


    }

    private ObservableList<Order> getData(){
        return FXCollections.observableArrayList(inMemoryPendingOrderRepository.findAll());
    }

    @FXML
    void prepararPedido(){
        listViewPedidos.getItems().get(0).setServed();
        inMemoryArchiveHistoryOrderRepository.add(listViewPedidos.getItems().get(0));
        inMemoryPendingOrderRepository.remove(listViewPedidos.getItems().get(0));
        listViewPedidos.refresh();
        mostrarAlerta("Pedido servido");

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
    void nuevoPedido(MouseEvent event){

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaNuevoPedidoControler vistaNuevoPedidoControler = new VistaNuevoPedidoControler(inMemoryProductRepository, inMemoryPendingOrderRepository, inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaNuevoPedidoControler, "/vista/vista_nuevo_pedido.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void verDetalle(MouseEvent event) {
        Order pedidoSeleccionado = listViewPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {

            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                VistaDetallePedido vistaDetallePedido = new VistaDetallePedido(this,"/vista/vista_pedidos_pendientes.fxml");
                vistaDetallePedido.añadirPedido(pedidoSeleccionado);
                ChangeScene.change(stage, vistaDetallePedido, "/vista/vista_detalle_pedido.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
