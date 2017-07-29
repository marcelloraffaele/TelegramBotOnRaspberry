package it.rmarcello.raspberrybot.tasks;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import java.util.List;
import java.util.Observer;

/**
 * Task che fa lampeggiare tutti i pin.
 * 
 * @author rmarcello
 */
public class ToggleTask extends AbstractTask {
    
    public ToggleTask(List<GpioPinDigitalOutput> pinList, int ripetition, Observer ob) {
        super(pinList, ripetition, ob);
    }
    
    @Override
    protected void initTask() {
        for (GpioPinDigitalOutput pin : pinList) {
            pin.setState(PinState.LOW);
        }
    }
    
    @Override
    protected void runTask() {
        try {
            
            for (GpioPinDigitalOutput pin : pinList) {
                pin.toggle();
            }
            Thread.sleep(1000);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
