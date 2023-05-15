package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaDetallePedido implements Initializable {

    @FXML
    private ListView<Product> listViewPedidos;

    @FXML
    private Button botonAtras;

    @FXML
    private Label precio;

    @FXML
    private Label cliente;

    @FXML
    private Label fecha;

    @FXML
    private Label codigo;

    @FXML
    private Label estado;

    private Initializable padreControler;

    private String vistaPadre;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    private ArrayList<Order> pedido;

    public VistaDetallePedido(Initializable padreControler, String vistaPadre) {
        this.padreControler = padreControler;
        this.vistaPadre = vistaPadre;
        this.pedido = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        precio.setText(String.valueOf(pedido.get(0).getOrderPrize())+"€");
        cliente.setText(pedido.get(0).getClientName());
        fecha.setText(pedido.get(0).getFormattedCreatedOn());
        codigo.setText(pedido.get(0).getCode());
        estado.setText(pedido.get(0).getStatus());
        for (int i = 0; i < pedido.size() ; i++) {
            listViewPedidos.setItems(getData(i));
        }



    }

    public void añadirPedido(Order order){
        pedido.add(order);
    }

    private ObservableList<Product> getData(int i){
        return FXCollections.observableArrayList(pedido.get(i).getProducts());
    }

    @FXML
    public void atras(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeScene.change(stage, padreControler, vistaPadre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
