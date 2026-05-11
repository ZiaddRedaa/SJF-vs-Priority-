
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/main_view.fxml"));

        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());

        stage.setTitle("SJF vs Priority CPU Scheduling");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

