import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class MainController {

    public static boolean is_setting_hotkey = false;
    public static boolean is_setting_target_key = false;
    public static KeyCode hotkey = KeyCode.F6;
    public static KeyCode targeted_key = KeyCode.SPACE;
    public static int auto_clicker_cooldown = 100;
    public static boolean is_cooldown_text_filed_selected = false;

    private String previous_cooldown_text = "";

    private final int MIN_COOLDOWN_SPEED = 1;      
    private final int MAX_COOLDOWN_SPEED = 600000; //10 minutes
    private final int DEFAULT_COOLDOWN_SPEED = 100;

    
    @FXML private TextField cooldown_text_field;
    @FXML private Button hotkey_button;
    @FXML private Button targeted_key_button;
    @FXML private Button increase_cooldown_button;
    @FXML private Button decrease_cooldown_button;
    @FXML private Text hotkey_label;
    @FXML private Text targeted_key_label;
    

    @FXML
    public void initialize() {
    
        this.cooldown_text_field.textProperty().addListener(
            (observable, oldValue, newValue) -> handleCooldownInput(newValue));
    }



    private void handleCooldownInput(String newValue){
        try{
            if(!newValue.isEmpty()){
                auto_clicker_cooldown = Integer.parseInt(newValue);
                previous_cooldown_text = newValue; //Store the last cooldown_text

                //Set auto_clicker_cooldown to default_cooldown if cooldown is above max_cooldown or below min_cooldown
                if(auto_clicker_cooldown<MIN_COOLDOWN_SPEED && auto_clicker_cooldown>MAX_COOLDOWN_SPEED)
                    auto_clicker_cooldown = DEFAULT_COOLDOWN_SPEED;

                    
            }else{
                previous_cooldown_text = ""; //Reset the previous_cooldown_text if cooldown text is empty
            }
        }
        catch(NumberFormatException e){
            
            restoreCooldownTextField(newValue);              
        }          
    }

    private void restoreCooldownTextField(String newValue){
        Platform.runLater(()->{
            this.cooldown_text_field.setText(previous_cooldown_text);
            
            //Return the caret to the end of the text
            this.cooldown_text_field.positionCaret(newValue.length());
            
            
            
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
        addCooldown(5);
    }

    @FXML
    public void decreaseCooldown(){
        addCooldown(-5);
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
