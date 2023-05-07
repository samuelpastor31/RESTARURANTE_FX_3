package es.progcipfpbatoi.controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {
    public static void change(Stage stage, Initializable controller,
                              String pathToViewFile) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ChangeScene.class.getResource(pathToViewFile));
        fxmlLoader.setController(controller);

        AnchorPane rootLayout = fxmlLoader.load();

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}

