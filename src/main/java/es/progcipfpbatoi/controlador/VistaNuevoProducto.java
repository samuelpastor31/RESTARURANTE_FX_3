package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.*;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import es.progcipfpbatoi.utils.AlertMessages;
import es.progcipfpbatoi.utils.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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

    @FXML
    private Spinner portionSizeSpinner;

    @FXML
    private CheckBox extraCheckBox;

    @FXML
    private ComboBox<String> drinkSizeComboBox;

    @FXML
    private RadioButton celiacRadioButton;

    @FXML
    private RadioButton diabeticRadioButton;

    private String vistaPadre;

    private Initializable padreControler;

    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;


    public VistaNuevoProducto(ProductRepository productRepository, InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository, InMemoryPendingOrderRepository inMemoryPendingOrderRepository, Initializable padreControler, String vistaPadre) {
        this.productRepository = productRepository;
        this.inMemoryArchiveHistoryOrderRepository = inMemoryArchiveHistoryOrderRepository;
        this.inMemoryPendingOrderRepository = inMemoryPendingOrderRepository;
        this.padreControler = padreControler;
        this.vistaPadre = vistaPadre;
    }

    @FXML
    public void confirmar(MouseEvent event) throws DatabaseErrorException {
        if (algunFieldVacio()) return;
        if (algunFieldIncorrecto()) return;
        if (nombreDuplicado()) return;
        crearProducto();
        atras(event);
    }

    private boolean nombreDuplicado() {
        if (this.productRepository.isNameExists(name.getText())) {
            AlertMessages.mostrarAlertError("Ya existe la descripción");
            return true;
        }
        return false;
    }

    private boolean algunFieldIncorrecto() {
        if (!Validator.isValidProductPrize(prize.getText())) {
            AlertMessages.mostrarAlertError("Precio inválido");
            return true;
        }
        if (!Validator.isValidProductDiscount(discount.getText())) {
            AlertMessages.mostrarAlertError("Descuento inválido");
            return true;
        }

        if (!Validator.isValidProductVat(tax.getText())) {
            AlertMessages.mostrarAlertError("IVA inválido");
            return true;
        }
        return false;
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

    private void crearProducto() throws DatabaseErrorException {
        String selectedType = typeSelector.getSelectionModel().getSelectedItem();
        String productId = String.valueOf(this.productRepository.findAll().size() + 1);
        String productName = name.getText();
        float productPrize = Float.valueOf(prize.getText());
        float productDiscount = Float.valueOf(discount.getText()) / 100;
        float productTax = Float.valueOf(tax.getText());
        boolean productEnabled = true;

        if (selectedType.equals(Desert.class.getSimpleName())) {
            Characteristic aptoCeliacos = celiacRadioButton.isSelected() ? Characteristic.CELIAC_SUITABLE : Characteristic.NO_APTO;
            Desert desertNew = new Desert(productId, productName, productPrize, productDiscount, productTax, productEnabled, aptoCeliacos);
            try {
                this.productRepository.save(desertNew);
            } catch (DatabaseErrorException | IOException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedType.equals(Sandwich.class.getSimpleName())) {
            Sandwich sandwichNew = new Sandwich(productId, productName, productPrize, productDiscount, productTax, productEnabled);
            try {
                this.productRepository.save(sandwichNew);
            } catch (DatabaseErrorException | IOException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedType.equals(Drink.class.getSimpleName())) {
            boolean refillable = extraCheckBox.isSelected();
            Size drinkSize = Size.valueOf(drinkSizeComboBox.getSelectionModel().getSelectedItem());
            Drink drinkNew = new Drink(productId, productName, productPrize, productDiscount, productTax, productEnabled, refillable, drinkSize);
            try {
                this.productRepository.save(drinkNew);
            } catch (DatabaseErrorException | IOException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedType.equals(Starter.class.getSimpleName())) {
            Starter starterNew = new Starter(productId, productName, productPrize, productDiscount, productTax, productEnabled);
            try {
                this.productRepository.save(starterNew);
            } catch (DatabaseErrorException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean algunFieldVacio() {
        if (typeSelector.getSelectionModel().getSelectedItem() == null) {
            AlertMessages.mostrarAlertError("Debe seleccionar un tipo");
            return true;
        }
        if (name.getText().isBlank()) {
            AlertMessages.mostrarAlertError("Debe seleccionar un nombre");
            return true;
        }
        if (prize.getText().isBlank()) {
            AlertMessages.mostrarAlertError("Debe seleccionar un precio");
            return true;
        }
        if (discount.getText().isBlank()) {
            AlertMessages.mostrarAlertError("Debe seleccionar un descuento");
            return true;
        }

        if (tax.getText().isBlank()) {
            AlertMessages.mostrarAlertError("Debe seleccionar un tax");
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> typeProductNameArrayList = findProductsName();
        typeSelector.setItems(FXCollections.observableArrayList(typeProductNameArrayList));
        celiacRadioButton.setDisable(true);
        diabeticRadioButton.setDisable(true);
        extraCheckBox.setDisable(true);
        drinkSizeComboBox.setDisable(true);

        typeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(Desert.class.getSimpleName())) {
                    celiacRadioButton.setDisable(false);
                    diabeticRadioButton.setDisable(false);
                } else {
                    celiacRadioButton.setDisable(true);
                    diabeticRadioButton.setDisable(true);
                }
            }
        });

        typeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(Drink.class.getSimpleName())) {
                    drinkSizeComboBox.setDisable(false);
                    extraCheckBox.setDisable(false);
                } else {
                    drinkSizeComboBox.setDisable(true);
                    extraCheckBox.setDisable(true);
                }
            }
        });

        // Configurar ToggleGroup para los RadioButtons
        ToggleGroup radioButtonGroup = new ToggleGroup();
        celiacRadioButton.setToggleGroup(radioButtonGroup);
        diabeticRadioButton.setToggleGroup(radioButtonGroup);

        // Configurar opciones del ComboBox de tamaños de bebida
        drinkSizeComboBox.getItems().addAll(Size.SMALL.name(), Size.NORMAL.name(), Size.BIG.name());

        // Configurar el Spinner de tamaño de raciones
        portionSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
    }

    private static ArrayList<String> findProductsName() {
        ArrayList<String> typeProductNameArrayList = new ArrayList<>();
        typeProductNameArrayList.add(Desert.class.getSimpleName());
        typeProductNameArrayList.add(Sandwich.class.getSimpleName());
        typeProductNameArrayList.add(Drink.class.getSimpleName());
        typeProductNameArrayList.add(Starter.class.getSimpleName());
        return typeProductNameArrayList;
    }
}
