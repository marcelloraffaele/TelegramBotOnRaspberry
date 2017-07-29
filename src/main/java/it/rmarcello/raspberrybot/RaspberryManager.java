package it.rmarcello.raspberrybot;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rmarcello
 */
public class RaspberryManager {
    
    final GpioController gpio;
    List<GpioPinDigitalOutput> pinList;
    
    public RaspberryManager() {
        gpio = GpioFactory.getInstance();
    }
    
    public void initialize() {

        System.out.println("GPIO Control Started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        Pin[] mypins = new Pin[] {RaspiPin.GPIO_23,RaspiPin.GPIO_24, RaspiPin.GPIO_26, RaspiPin.GPIO_27, RaspiPin.GPIO_28};
        
        pinList = new LinkedList<GpioPinDigitalOutput>();
        for(int i=0;i<mypins.length;i++) {
            Pin p = mypins[i];
            GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin( p , "LED_"+i, PinState.LOW);
            pin.setShutdownOptions(true, PinState.LOW);
            pinList.add( pin );            
        }
                
        System.out.println("Exiting ControlGpioExample");
    }

    public void finalize() {
        gpio.shutdown();
    }

    public List<GpioPinDigitalOutput> getPinList() {
        return pinList;
    }
    
    
    
}
