package es.progcipfpbatoi.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessages {
    public static void mostrarAlertError(String msg) {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setHeaderText( null );
        alert.setTitle( "Error" );
        alert.setContentText( msg );
        alert.showAndWait();
    }

    public static void mostrarAlertWarning(String msg) {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setHeaderText( null );
        alert.setTitle( "Aviso" );
        alert.setContentText( msg );
        alert.showAndWait();
    }

    public static void mostrarAlert(String title, String msg, Alert.AlertType alertType) {
        Alert alert = new Alert( alertType );
        alert.setHeaderText( null );
        alert.setTitle( title );
        alert.setContentText( msg );
        alert.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String title, String content) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
        alert.setHeaderText( null );
        alert.setTitle( title );
        alert.setContentText( content );
        Optional<ButtonType> action = alert.showAndWait();
        if ( action.get() == ButtonType.OK ) {
            return true;
        }
        return false;
    }
}