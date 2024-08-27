import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class MainController {

    static boolean is_setting_hotkey = false;
    static boolean is_setting_target_key = false;
    static KeyCode hotkey = KeyCode.F6;
    static KeyCode targeted_key = KeyCode.SPACE;

    Thread changeHotkeyLabel;
    Thread changeTargetedKeyLabel;

    @FXML private Button hotkey_button;
    @FXML private Button targeted_key_button;
    @FXML private Text hotkey_label;
    @FXML private Text targeted_key_label;
    @FXML private TextField cooldown_text_field;

    
    //TODO Merge setNewHotKey and setTargetKey together

    @FXML
    public void setNewHotkey(ActionEvent event){
        if (!is_setting_hotkey) {
            disableAllUiComponents(); //Disable the UI components
            is_setting_hotkey = true;
            
            new Thread(()->{
                while (is_setting_hotkey)
                    this.hotkey_label.setText(hotkey.getName().toUpperCase()); //Set the hotkey label to the current hotkey
                

                enableAllUiComponents(); //Enable the UI components 
            }).start();

            
        }
    }

    @FXML
    public void setTargetedKey(ActionEvent event){
        if (!is_setting_target_key) {
            disableAllUiComponents(); //Disable the UI components
            is_setting_target_key = true;
            
            new Thread(()->{
                while (is_setting_target_key)
                    this.targeted_key_label.setText(targeted_key.getName().toUpperCase()); //Set the target key label to the current target key
                
                enableAllUiComponents(); //Enable the UI components 
            }).start();

            
        }
    }


    
    public void disableAllUiComponents(){
        //Disable all UI components

        this.cooldown_text_field.setDisable(true);
        this.targeted_key_button.setDisable(true);
        this.hotkey_button.setDisable(true);
    }

    public void enableAllUiComponents(){
        //Enable all UI components

        this.cooldown_text_field.setDisable(false);
        this.targeted_key_button.setDisable(false);
        this.hotkey_button.setDisable(false);
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
