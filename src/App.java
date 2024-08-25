import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
        
    public static void main(String[] args) throws Exception {
            launch(args); //Start the application
        
    }

    @Override
    public void start(Stage main_stage) throws Exception {
        //Load the scene from the fxml file
        FXMLLoader scene_loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = scene_loader.load(); 

        MainController controller = new MainController();
        Scene scene = new Scene(root);
        
        
        scene.setOnKeyPressed(event -> { //Listen for keys the user pressed

            //TODO merge the if statements together

            if(controller.getIsSettingHotkey()){
                controller.setHotkey(event.getCode().toString());
                if (!controller.getHotkey().equals(controller.getTargetedKey())) {
                    controller.setIsSettingHotkey(false);
                }
            }else if(controller.getIsSettingTargetKey()){
                controller.setTargetedKey(event.getCode().toString());
                if (!controller.getHotkey().equals(controller.getTargetedKey())) {
                    controller.setIsSettingTargetKey(false);
                }
            }
        });

        main_stage.setTitle("Multi-Clicker");
        main_stage.setScene(scene);
        main_stage.setResizable(false);
        
        main_stage.show(); 
    }
}
