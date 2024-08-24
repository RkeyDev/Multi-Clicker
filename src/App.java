import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    
    public static void main(String[] args) throws Exception {
            launch(args);
        
    }

    @Override
    public void start(Stage main_stage) throws Exception {
        FXMLLoader scene_loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = scene_loader.load();

        Scene scene = new Scene(root);

        main_stage.setTitle("Multi-Clicker");
        main_stage.setScene(scene);
        
        main_stage.show();
    }
}
