import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;



public class App extends Application{
        
    public static boolean is_app_running = false;
    public static Map<Integer,KeyCode> keyCodesMap = new HashMap<>();

    public static void main(String[] args) throws Exception {


            new Thread(()->mapKeyCodes()).start();
            launch(args); //Start the application
        
    }

    @Override

    public void start(Stage main_stage) throws Exception {
        is_app_running = true;
        listenToKeyPresses(); //Start listen for key presses from the client

        //Load the scene from the fxml file
        FXMLLoader scene_loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = scene_loader.load(); 

        Scene scene = new Scene(root);
        
        
        scene.setOnKeyPressed(event -> { //Listen for keys the user pressed

            //TODO merge the if statements together
            if(MainController.is_setting_hotkey){
                MainController.hotkey = event.getCode();
                if (!MainController.hotkey.equals(MainController.targeted_key)) {
                    MainController.is_setting_hotkey = false;
                }
            }else if(MainController.is_setting_target_key){
                MainController.targeted_key = event.getCode();
                if (!MainController.hotkey.equals(MainController.targeted_key)) {
                    MainController.is_setting_target_key = false;
                }
            }
        });

        //Window configuration
        main_stage.setTitle("Multi-Clicker");
        main_stage.setScene(scene);
        main_stage.setResizable(false);
        
        main_stage.setOnCloseRequest(event->closeProgram());


        //Display the window on the screen
        main_stage.show(); 
    }

    private void listenToKeyPresses(){
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

        //Start listen for client's pressed keys
        GlobalScreen.addNativeKeyListener(new AutoClicker());
    }


    public static void mapKeyCodes(){
        for (KeyCode keyCode : KeyCode.values()) {
            try {
                String jnativehookKeyName = "VC_" + (keyCode.name().contains("DIGIT")? //Check if is a digit
                String.valueOf(keyCode.name().charAt(5)) //Save only the digit
                :keyCode.name()); //Else save the key normally

                
                Field field = NativeKeyEvent.class.getField(jnativehookKeyName);
                int jnativehookKeyCode = (int)field.get(null);
                
                keyCodesMap.put(jnativehookKeyCode, keyCode);

            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                System.out.println("Not Found Key: VC_" + keyCode.name());
                continue; //Continue if key not found
                
            }
            
        }
    } 

    private void closeProgram(){
        is_app_running = false;
        System.exit(0);
    }
}
