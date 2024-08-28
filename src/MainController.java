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

    

    @FXML
    public void setNewHotkey(ActionEvent event){
        setKey(KeyType.HOTKEY);
    }

    @FXML
    public void setTargetedKey(ActionEvent event){
        setKey(KeyType.TARGETED_KEY);
    }



    public void setAllUiComponentsDisable(boolean is_disabled){
        //Disable all UI components

        this.cooldown_text_field.setDisable(is_disabled);
        this.targeted_key_button.setDisable(is_disabled);
        this.hotkey_button.setDisable(is_disabled);
    }

   

    private void setKey(KeyType key_type){
    if (!(is_setting_hotkey || is_setting_target_key)) {
        setAllUiComponentsDisable(true); //Disable the UI components

        is_setting_target_key = key_type.equals(KeyType.TARGETED_KEY)?true:false;
        is_setting_hotkey = key_type.equals(KeyType.HOTKEY)?true:false;

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




    public void setHotkey(KeyCode keyCode) {hotkey = keyCode;}
    public void setTargetedKey(KeyCode keyCode) {targeted_key = keyCode;}


    public void setIsSettingHotkey(boolean isSettingHotkey) {is_setting_hotkey = isSettingHotkey;}
    public void setIsSettingTargetKey(boolean isSettingTargetKey) {is_setting_target_key = isSettingTargetKey;}

    public boolean getIsSettingTargetKey() {return is_setting_target_key;}
    public boolean getIsSettingHotkey() {return is_setting_hotkey;}
    public String getHotkey() {return hotkey.getName();}
    public String getTargetedKey() {return targeted_key.getName();}
    
}
