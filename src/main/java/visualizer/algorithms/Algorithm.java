package visualizer.algorithms;

public abstract class Algorithm extends Thread{
    
    protected int sleepTime;
    public Algorithm(int sleep){
        this.sleepTime = sleep;
    }
}
