import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController {

    static boolean is_setting_hotkey = false;
    static String start_hotkey = "F6";

    @FXML private Button hotkey_button;
    @FXML private Button targeted_key_button;
    @FXML private Text hotkey_label;
    @FXML private TextField cooldown_text_field;

    Thread changeHotkeyLabel;

    @FXML
    public void setNewHotkey(ActionEvent event){
        if (is_setting_hotkey == false) {
            disableAllUiComponents(); //Disable the UI components
            is_setting_hotkey = true;
            
            changeHotkeyLabel = new Thread(()->{
                while (is_setting_hotkey){
                    this.hotkey_label.setText(start_hotkey.toUpperCase()); //Set the hotkey label to the current hotkey
                }

                enableAllUiComponents(); //Enable the UI components 
            });

            changeHotkeyLabel.start();
        }
    }

    
    public void disableAllUiComponents(){
        //Disable all UI components

        this.cooldown_text_field.setDisable(true);
        this.targeted_key_button.setDisable(true);
    }

    public void enableAllUiComponents(){
        //Enable all UI components

        this.cooldown_text_field.setDisable(false);
        this.targeted_key_button.setDisable(false);
    }





    public void setHotkey(String hotkey){start_hotkey = hotkey;}

    public void setIsSettingHotkey(boolean isSettingHotkey) {is_setting_hotkey = isSettingHotkey;}

    public boolean getIsSettingHotkey() {return is_setting_hotkey;}


    
}
