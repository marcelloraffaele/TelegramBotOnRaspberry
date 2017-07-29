package it.rmarcello.raspberrybot.tasks;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import java.util.List;
import java.util.Observer;

/**
 * Task che accende un un pin per volta da sinistra a destra e poi torna indietro.
 * 
 * @author rmarcello
 */
public class GoAndBackTask extends AbstractTask {

    public GoAndBackTask(List<GpioPinDigitalOutput> pinList, int ripetition, Observer ob) {
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
            pinList.get(0).setState(PinState.HIGH);
            for( int i=1;i<pinList.size();i++) {
                pinList.get(i-1).toggle();
                pinList.get(i).toggle();
                Thread.sleep(100);
            }
            
            for( int i=pinList.size()-1;i>0;i--) {
                pinList.get(i-1).toggle();
                pinList.get(i).toggle();
                Thread.sleep(100);
            }
            pinList.get(0).setState(PinState.LOW);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
