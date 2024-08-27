import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.AWTException;
import java.awt.Robot;


public class AutoClicker implements NativeKeyListener{

    public static boolean is_auto_active;

    private Robot clickerRobot;
    

    public void keyboardAutoPressThread(){
        while (is_auto_active) {
            try {
                this.clickerRobot.keyPress(MainController.targeted_key.getCode()); //Press the targeted key
                Thread.sleep(500); //Auto clicker delay
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                break;
            }
        }     
    }

    public AutoClicker(){
        try {
            this.clickerRobot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
            System.out.println(App.keyCodesMap.get(e.getKeyCode()) + "\n" + MainController.hotkey);

                if(!(MainController.is_setting_hotkey || MainController.is_setting_target_key)){
                    if(App.keyCodesMap.get(e.getKeyCode()) == MainController.hotkey){
                        is_auto_active = !is_auto_active; //Toggle on/off auto clicker

                        new Thread(()->keyboardAutoPressThread()).start();
                        
                    }
            }
    }
        
}

    

