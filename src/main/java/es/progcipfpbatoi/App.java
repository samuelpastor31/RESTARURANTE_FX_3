package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.VistaPrincipalControler;
import es.progcipfpbatoi.modelo.dao.FileProductoDAO;
import es.progcipfpbatoi.modelo.dao.ProductosDAO;
import es.progcipfpbatoi.modelo.repositorios.InMemoryArchiveHistoryOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.InMemoryPendingOrderRepository;
import es.progcipfpbatoi.modelo.repositorios.ProductRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private InMemoryPendingOrderRepository inMemoryPendingOrderRepository;
    private ProductRepository productRepository;
    private InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository;

    private ProductosDAO productosDAO;


    @Override
    public void start(Stage stage) throws IOException {

        InMemoryPendingOrderRepository inMemoryPendingOrderRepository = new InMemoryPendingOrderRepository();
        FileProductoDAO fileProductoDAO = new FileProductoDAO();
        ProductRepository productRepository = new ProductRepository(fileProductoDAO);

        InMemoryArchiveHistoryOrderRepository inMemoryArchiveHistoryOrderRepository = new InMemoryArchiveHistoryOrderRepository();
        FXMLLoader loader = new FXMLLoader();
        VistaPrincipalControler vistaPrincipalControler = new VistaPrincipalControler(inMemoryPendingOrderRepository, productRepository,inMemoryArchiveHistoryOrderRepository);
        ChangeScene.change(stage,vistaPrincipalControler,"/vista/vista_principal.fxml" );

    }


    public static void main(String[] args) {
        launch();
    }

}