/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SQ04
 */
public class ThreadTest {
    
    
    public static void main(String[] args) {
       ThreadManager manager= new ThreadManager();
       
     Thread t1=  new Thread(new Runnable() {
                
           @Override
           public void run() {
               System.out.println("Running me");
           }
           
       });
       t1.setName("T1 Thread");
       Thread t2=  new Thread(new Runnable() {
                
           @Override
           public void run() {
               System.out.println("Running me v2");
               try {
                   Thread.sleep(5000);
               } catch (InterruptedException ex) {
                   System.out.println("I just interrrupted a thread");
                   Logger.getLogger(ThreadTest.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
       });
       t1.setName("T2 Thread");
       
       manager.addThread(t1);  
       manager.addThread(t2);
       try{
       t1.start();
       t2.join();
       t2.start();
       }catch(InterruptedException ex){
           System.out.println(ex.getMessage()+", Stopped");
       }
    }
}

