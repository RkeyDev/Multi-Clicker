import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainController {

    private final String APP_SETTINGS_PATH = "app-settings.properties";

    private int MIN_COOLDOWN_SPEED;      
    private int MAX_COOLDOWN_SPEED;
    private int DEFAULT_COOLDOWN_SPEED;
    private int COOLDOWN_INCREMENT;

    public static int auto_clicker_cooldown;
    public static boolean is_setting_hotkey = false;
    public static boolean is_setting_target_key = false;
    public static KeyCode hotkey = KeyCode.F6;
    public static KeyCode targeted_key = KeyCode.SPACE;
    public static boolean is_cooldown_text_filed_selected = false;


    
    @FXML private TextField cooldown_text_field;
    @FXML private Button hotkey_button;
    @FXML private Button targeted_key_button;
    @FXML private Button increase_cooldown_button;
    @FXML private Button decrease_cooldown_button;
    @FXML private Text hotkey_label;
    @FXML private Text targeted_key_label;
    

    @FXML
    public void initialize() {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(APP_SETTINGS_PATH)) {
            properties.load(input);

            this.MIN_COOLDOWN_SPEED = Integer.parseInt(properties.getProperty("cooldown.min"));
            this.MAX_COOLDOWN_SPEED = Integer.parseInt(properties.getProperty("cooldown.max"));
            this.DEFAULT_COOLDOWN_SPEED = Integer.parseInt(properties.getProperty("cooldown.default"));
            this.COOLDOWN_INCREMENT = Integer.parseInt(properties.getProperty("cooldown.increment"));



        } catch (IOException ex) {
            showAlertMessage("Could not find \"" + APP_SETTINGS_PATH+"\". Try to reinstall the application and do not rename app files.");
            App.shutDownApplication();
            
        }

        //Set the cooldown to be equals to the default cooldown
        auto_clicker_cooldown = this.DEFAULT_COOLDOWN_SPEED;
        this.cooldown_text_field.setText(String.valueOf(auto_clicker_cooldown)); 

        this.cooldown_text_field.textProperty().addListener(
            (observable, oldValue, newValue) -> handleCooldownInput(newValue));
    }



    private void handleCooldownInput(String newValue){
        try{
            if(!newValue.isEmpty()){
                auto_clicker_cooldown = Integer.parseInt(newValue);

                //Set auto_clicker_cooldown to default_cooldown if cooldown is above max_cooldown or below min_cooldown
                if(auto_clicker_cooldown<MIN_COOLDOWN_SPEED || auto_clicker_cooldown>MAX_COOLDOWN_SPEED){

                    showAlertMessage("Please enter a number between the limits\nMax cooldown: " 
                    + MAX_COOLDOWN_SPEED + "ms"+ 
                    "\nMin cooldown: " +
                     MIN_COOLDOWN_SPEED +"ms");

                    auto_clicker_cooldown = DEFAULT_COOLDOWN_SPEED;
                }
            }else{
                 //Set auto_clicker_cooldown to default if cooldown text field is empty
                auto_clicker_cooldown = DEFAULT_COOLDOWN_SPEED;
            }
            
        }
        catch(NumberFormatException e){
            showAlertMessage("Please enter a valid positive number.");
            restoreCooldownTextField(String.valueOf(auto_clicker_cooldown));              
        }          
    }

    private void restoreCooldownTextField(String textInput){
        Platform.runLater(()->{
            this.cooldown_text_field.setText(textInput);
            
            //Return the caret to the end of the text
            this.cooldown_text_field.positionCaret(textInput.length());
            
            
            
        }); 
    }

    @FXML
    public void setNewHotkey(ActionEvent event){
        setKey(KeyType.HOTKEY);
    }

    @FXML
    public void setTargetedKey(ActionEvent event){
        setKey(KeyType.TARGETED_KEY);
    }

    @FXML
    public void increaseCooldown(){
        addCooldown(COOLDOWN_INCREMENT);
    }

    @FXML
    public void decreaseCooldown(){
        addCooldown(-COOLDOWN_INCREMENT);
    }


    private void addCooldown(int cooldown){
        if(auto_clicker_cooldown + cooldown>MIN_COOLDOWN_SPEED && auto_clicker_cooldown + cooldown < MAX_COOLDOWN_SPEED){
            auto_clicker_cooldown += cooldown;
            this.cooldown_text_field.setText(String.valueOf(auto_clicker_cooldown));
        }

    }


    public void setAllUiComponentsDisable(boolean is_disabled){
        //Disable all UI components

        cooldown_text_field.setDisable(is_disabled);
        this.targeted_key_button.setDisable(is_disabled);
        this.hotkey_button.setDisable(is_disabled);
        this.increase_cooldown_button.setDisable(is_disabled);
        this.decrease_cooldown_button.setDisable(is_disabled);
    }

      

    private void setKey(KeyType key_type){
    if (!(is_setting_hotkey || is_setting_target_key)) {
        setAllUiComponentsDisable(true); //Disable the UI components

        is_setting_target_key = key_type.equals(KeyType.TARGETED_KEY);
        is_setting_hotkey = key_type.equals(KeyType.HOTKEY);

        new Thread(()->{ //New thread to set the key
            while (is_setting_target_key || is_setting_hotkey){
                if (is_setting_hotkey) {
                    //Set the target key label to the current target key
                    this.hotkey_label.setText(hotkey.getName().toUpperCase()); 
                }else if (is_setting_target_key) {
                    //Set the target key label to the current target key
                    this.targeted_key_label.setText(targeted_key.getName().toUpperCase()); 
                }
                
            }

            setAllUiComponentsDisable(false); //Enable the UI components 
        }).start();
    }
}



    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
}


    public void setHotkey(KeyCode keyCode) {hotkey = keyCode;}
    public void setTargetedKey(KeyCode keyCode) {targeted_key = keyCode;}


    public void setIsSettingHotkey(boolean isSettingHotkey) {is_setting_hotkey = isSettingHotkey;}
    public void setIsSettingTargetKey(boolean isSettingTargetKey) {is_setting_target_key = isSettingTargetKey;}

    public boolean getIsSettingTargetKey() {return is_setting_target_key;}
    public boolean getIsSettingHotkey() {return is_setting_hotkey;}
    public String getHotkey() {return hotkey.getName();}
    public String getTargetedKey() {return targeted_key.getName();}
    
}
