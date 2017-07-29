package it.rmarcello.raspberrybot.tasks;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe astratta Runnable, rappresenta il thread che verra' utilizzato dall'esecutore per performare i task previsti.
 * 
 * 
 * @author rmarcello
 */
public abstract class AbstractTask extends Observable implements Runnable{
    
    protected List<GpioPinDigitalOutput> pinList;
    protected int ripetition;
    
    public AbstractTask(List<GpioPinDigitalOutput> pinList, int ripetition, Observer ob) {
        this.pinList=pinList;
        this.ripetition=ripetition;
        this.addObserver(ob);
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        
        initTask();
        
        int i=0;
        while(true) {
            if( ripetition>0 && i>=ripetition ) {
                break;
            }
            System.out.println("running the task... "+ i );
            runTask();
            
            i++;
            
        }
        
        setChanged();
        notifyObservers("finish");
        
    }

    protected void initTask() {
        //empty
    }
    
    protected abstract void runTask();
    
}
