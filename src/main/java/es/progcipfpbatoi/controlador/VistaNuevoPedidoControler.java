package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class VistaNuevoPedidoControler implements Initializable {

    @FXML
    private ListView<Product> listViewPedidos;

    @FXML
    private Button botonAtras;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private Button botonConfirmar;

    @FXML
    private DatePicker datePickerFecha;

    @FXML
    private CheckBox checkBox;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;


    public VistaNuevoPedidoControler(ProductRepository productRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository) {
        this.productRepository = productRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listViewPedidos.setItems(getData());
            listViewPedidos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listViewPedidos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                actualizarBotonConfirmar();
            });
            datePickerFecha.setEditable(false);
            datePickerFecha.setOnMouseClicked(e -> {
                if (!datePickerFecha.isEditable()) {
                    datePickerFecha.hide();
                }
            });
        }catch (DatabaseErrorException ex) {
            // Mostrar una alerta
        }
    }

    @FXML
    private void handleChangeFechaCheckbox(){
        datePickerFecha.setEditable(true);
    }

    private ObservableList<Product> getData() throws DatabaseErrorException {
        return FXCollections.observableArrayList(productRepository.findAll());
    }

    @FXML
    void atras(MouseEvent event) {

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPedidosPendientesControler vistaPedidosPendientesControler = new VistaPedidosPendientesControler(inMemoryPendingOrderRepository, productRepository,inMemoryArchiveHistoryOrderRepository);
            ChangeScene.change(stage, vistaPedidosPendientesControler, "/vista/vista_pedidos_pendientes.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void guardarPedido(MouseEvent event) {
        if (textFieldNombre.getText().isEmpty()) {
            mostrarAlerta("Debe introducir un nombre para el pedido");
            return;
        }
        if (listViewPedidos.getSelectionModel().getSelectedItems().isEmpty()) {
            mostrarAlerta("Debe seleccionar al menos un producto");
            return;
        }
        try {

            Order order = new Order(codActualizado(), textFieldNombre.getText());
            for (Product product : listViewPedidos.getSelectionModel().getSelectedItems()) {
                order.addNewProduct(product);
            }
            if (checkBox.isSelected() && datePickerFecha.getValue() != null) {
                order.setCreatedOn(datePickerFecha.getValue().atStartOfDay());
            } else {
                order.setCreatedOn(LocalDateTime.now());
            }
            inMemoryPendingOrderRepository.add(order);
            mostrarAlerta("Pedido creado correctamente");

            atras(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String codActualizado(){
        return "" + (inMemoryPendingOrderRepository.size() + inMemoryArchiveHistoryOrderRepository.size());
    }



    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("RESTAURANTE");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void actualizarBotonConfirmar() {
        boolean hayProductosSeleccionados = !listViewPedidos.getSelectionModel().getSelectedItems().isEmpty();
        botonConfirmar.setDisable(!hayProductosSeleccionados);
    }
}
