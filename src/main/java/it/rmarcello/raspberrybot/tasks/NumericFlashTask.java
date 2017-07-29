package it.rmarcello.raspberrybot.tasks;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import java.util.List;
import java.util.Observer;

/**
 * Task che fa lampeggiare i pin contando un ritardo.
 * 
 * @author rmarcello
 */
public class NumericFlashTask extends AbstractTask {

    public NumericFlashTask(List<GpioPinDigitalOutput> pinList, int ripetition, Observer ob) {
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
            
            for( int i=0;i<pinList.size();i++) {
                flash(pinList.get(i), i+1);
                Thread.sleep(200);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void flash(GpioPinDigitalOutput p, int n) throws InterruptedException {
        
        for(int i=0;i<n;i++) {
            p.toggle();
            Thread.sleep(200);
            p.toggle();
            Thread.sleep(200);
        }
        
        
    }

}
