package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Desert;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Drink;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Sandwich;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Starter;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import es.progcipfpbatoi.utils.AlertMessages;
import es.progcipfpbatoi.utils.Validator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaNuevoProducto implements Initializable {

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

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    public VistaNuevoProducto(ProductRepository productRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository) {
        this.productRepository = productRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
    }

    @FXML
    public void confirm(ActionEvent event) throws DatabaseErrorException {
        if ( isAnyFieldInBlank() ) return;
        if ( isAnyFieldRegexInvalid() ) return;
        if ( isNameDuplicated() ) return;
        createProduct();
    }

    private boolean isNameDuplicated() {
        if ( this.productRepository.isNameExists( name.getText() ) ) {
            AlertMessages.mostrarAlertError( "Ya existe la descripcion" );
            return true;
        }
        return false;
    }

    private boolean isAnyFieldRegexInvalid() {
        if ( !Validator.isValidProductPrize( prize.getText() ) ) {
            mostrarAlertaError( "Precio inválido" );
            return true;
        }
        if ( !Validator.isValidProductDiscount( discount.getText() ) ) {
            mostrarAlertaError( "Descuento inválido" );
            return true;
        }

        if ( !Validator.isValidProductVat( tax.getText() ) ) {
            mostrarAlertaError( "IVA inválido" );
            return true;
        }
        return false;
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

    private void createProduct() throws DatabaseErrorException {
        if ( typeSelector.getSelectionModel().getSelectedItem().equals( Desert.class.getSimpleName() ) ) {
            Desert desertNew = new Desert(String.valueOf(this.productRepository.findAll().size()+1), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText()),Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( desertNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Sandwich.class.getSimpleName() ) ) {
            Sandwich sandwichNew = new Sandwich(String.valueOf(this.productRepository.findAll().size()+1), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText()),Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( sandwichNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Drink.class.getSimpleName() ) ) {
            Drink drinkNew = new Drink(String.valueOf(this.productRepository.findAll().size()+1), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText()),Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( drinkNew );
            } catch ( DatabaseErrorException e ) {
                throw new RuntimeException( e );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ( typeSelector.getSelectionModel().getSelectedItem().equals( Starter.class.getSimpleName() ) ) {
            Starter starterNew = new Starter(String.valueOf(this.productRepository.findAll().size()+1), name.getText(),Float.valueOf(prize.getText()),Float.valueOf(discount.getText()),Float.valueOf(tax.getText()),true);
            try {
                this.productRepository.save( starterNew );
            } catch (DatabaseErrorException | IOException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    private boolean isAnyFieldInBlank() {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> typeProductNameArrayList = findProductsName();
        typeSelector.setItems( FXCollections.observableArrayList( typeProductNameArrayList ) );
    }

    private static ArrayList<String> findProductsName() {
        ArrayList<String> typeProductNameArrayList = new ArrayList<>();
        typeProductNameArrayList.add( Desert.class.getSimpleName() );
        typeProductNameArrayList.add( Sandwich.class.getSimpleName() );
        typeProductNameArrayList.add( Drink.class.getSimpleName() );
        typeProductNameArrayList.add( Starter.class.getSimpleName() );
        return typeProductNameArrayList;
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
