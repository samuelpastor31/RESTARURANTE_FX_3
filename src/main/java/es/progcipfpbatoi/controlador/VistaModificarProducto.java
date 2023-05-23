package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.*;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import es.progcipfpbatoi.utils.AlertMessages;
import es.progcipfpbatoi.utils.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaModificarProducto implements Initializable{

    @FXML
    private ComboBox<String> typeSelector;

    @FXML
    private TextField name;

    @FXML
    private TextField prize;

    @FXML
    private TextField discount;

    @FXML
    private TextField tax;

    private String vistaPadre;

    private Initializable padreControler;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    private ArrayList<Product> products;

    public VistaModificarProducto(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.products = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(products.get(0).getName());
        prize.setText(String.valueOf(products.get(0).getOnlyPrize()));
        discount.setText(String.valueOf(products.get(0).getPercentageDiscount()));
        tax.setText(String.valueOf(products.get(0).getVat()));
        ArrayList<String> typeProductNameArrayList = findProductsName();
        typeSelector.setItems( FXCollections.observableArrayList( typeProductNameArrayList ));
    }

    @FXML
    public void confirmar(MouseEvent event) throws DatabaseErrorException {
        if ( algunFieldVacio() ) return;
        if ( algunFieldIncorrecto() ) return;
        crearProducto();
        atras(event);
    }

    public void añadirPedido(Product product){
        products.add(product);
    }

    private boolean algunFieldIncorrecto() {
        if ( !Validator.isValidProductPrize( prize.getText() ) ) {
            AlertMessages.mostrarAlertError( "Precio inválido" );
            return true;
        }
        if ( !Validator.isValidProductDiscount( discount.getText() ) ) {
            AlertMessages.mostrarAlertError( "Descuento inválido" );
            return true;
        }

        if ( !Validator.isValidProductVat( tax.getText() ) ) {
            AlertMessages.mostrarAlertError( "IVA inválido" );
            return true;
        }
        return false;
    }
    @FXML
    void atras(MouseEvent event){
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaDetalleProducto vistaDetalleProducto = new VistaDetalleProducto(productRepository,inMemoryPendingOrderRepository,inMemoryArchiveHistoryOrderRepository);
            vistaDetalleProducto.añadirPedido(products.get(0));
            ChangeScene.change(stage, vistaDetalleProducto, "/vista/vista_detalle_producto.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void crearProducto() throws DatabaseErrorException {
        if ( typeSelector.getSelectionModel().getSelectedItem().equals( Desert.class.getSimpleName() ) ) {
            Desert desertNew = new Desert(products.get(0).getCod(), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText())/100,Float.valueOf(tax.getText()),true,Characteristic.CELIAC_SUITABLE);
            try {
                this.productRepository.save( desertNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Sandwich.class.getSimpleName() ) ) {
            Sandwich sandwichNew = new Sandwich(products.get(0).getCod(), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText())/100,Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( sandwichNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Drink.class.getSimpleName() ) ) {
            Drink drinkNew = new Drink(products.get(0).getCod(), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText())/100,Float.valueOf(tax.getText()),true,true, Size.NORMAL);
            try {
                this.productRepository.save( drinkNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Starter.class.getSimpleName() ) ) {
            Starter starterNew = new Starter(products.get(0).getCod(), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText())/100,Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( starterNew );
            } catch (DatabaseErrorException | IOException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    private boolean algunFieldVacio() {
        if ( typeSelector.getSelectionModel().getSelectedItem() == null ) {
            AlertMessages.mostrarAlertError( "Debe seleccionar un tipo" );
            return true;
        }
        if ( name.getText().isBlank() ) {
            AlertMessages.mostrarAlertError( "Debe seleccionar un nombre" );
            return true;
        }
        if ( prize.getText().isBlank() ) {
            AlertMessages.mostrarAlertError( "Debe seleccionar un precio" );
            return true;
        }
        if ( discount.getText().isBlank() ) {
            AlertMessages.mostrarAlertError( "Debe seleccionar un descuento" );
            return true;
        }

        if ( tax.getText().isBlank() ) {
            AlertMessages.mostrarAlertError( "Debe seleccionar un tax" );
            return true;
        }
        return false;
    }

    private static ArrayList<String> findProductsName() {
        ArrayList<String> typeProductNameArrayList = new ArrayList<>();
        typeProductNameArrayList.add( Desert.class.getSimpleName() );
        typeProductNameArrayList.add( Sandwich.class.getSimpleName() );
        typeProductNameArrayList.add( Drink.class.getSimpleName() );
        typeProductNameArrayList.add( Starter.class.getSimpleName() );
        return typeProductNameArrayList;
    }
}
