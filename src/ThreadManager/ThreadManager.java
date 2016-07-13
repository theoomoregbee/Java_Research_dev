package ThreadManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultListModel;

/**
 *
 * @author SQ04
 */
public class ThreadManager {
    private ArrayList<Thread> threads= new ArrayList<>();
    private ThreadManagerGUI UI;
    private Timer process=new Timer();
    private int delay=500; //start running our checking every 500milisecond
    private DefaultListModel<ListView> view_model=new DefaultListModel<>();
    
    
    public ThreadManager() {
        UI=new ThreadManagerGUI(this);
          
         UI.setVisible(true);
         
         UI.getThreadView().setModel(view_model);
         
         run();
    }
 
    
    public ArrayList<Thread> getThreads() {
        return threads;
    }
    
    public void addThread(Thread x){
        threads.add(x);
        view_model.addElement(new ListView(x));
        //updateListView();
    }
    
    public void removeThread(Thread x){
        int i=threads.indexOf(x);
        threads.remove(x);
        view_model.removeElementAt(i);
        //updateListView();
    }
    
    private void fireThreadCheck(){
        //check if the threads are still alive or dead
         int size=threads.size();
         for(int i=0;i<size;i++){
             if(threads.get(i).isAlive()){
                 System.out.println(threads.get(i).getName()+" is Alive"); 
                 ((ListView)(UI.getThreadView().getModel().getElementAt(i))).status="Alive";
             }    
             else{
                 System.out.println(threads.get(i).getName()+" is Dead");  
                 ((ListView)(UI.getThreadView().getModel().getElementAt(i))).status="Dead";
                ((ListView)(UI.getThreadView().getModel().getElementAt(i))).state="Finished";
             }
           //  UI.validate();
             UI.repaint();
         } 
    }
    
    public void run(){
        UI.getRunningBar().setVisible(true);
        process.schedule(new TimerTask() {
            @Override
            public void run() {
               //System.out.println("Hello World");
                fireThreadCheck();
            }
                }, 0, delay);
    }
    
    public void stop(){
        process.cancel();
        UI.getRunningBar().setVisible(false);
    }
    
    public void suspend(int delay, Thread x) throws InterruptedException{
       x.wait(delay);
    }
    
     public void suspend( Thread x) throws InterruptedException{
        x.suspend();
    }

    private void updateListView() {
        view_model=new DefaultListModel<>();
         int size=threads.size();
         for(int i=0;i<size;i++)
              view_model.addElement(new ListView(threads.get(i)));
         
          
          UI.getThreadView().setModel(view_model);
    }
    
    public void resume(Thread x){
        x.resume();
    }
    
    public void kill(Thread x){
        x.interrupt();
    }
}



class ListView{ 
    public String status="";
    public Thread x;
    public String state="Running";//paused killed or running

    public ListView(Thread x) {
        this.x=x;
    }
 
    @Override
    public String toString() {
        return x.getName()+"-----"+status+"----"+state; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}