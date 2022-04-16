/******************************************
* Controllable Timer class
* Written by Daniel Cormier,
* for use in CST8221
*
* See attached documentation for usage notes.
*********************************************/

package Controller;

import java.awt.EventQueue;

import View.RightPanel;

/**
ControllableTimer:  A timer that can be started and stopped.
@author Daniel Cormier
@version 1.1
@since 1.8_301
*/

class ControllableTimer extends Thread
{
    /** Resumes operation. */
    static final int START = 1;
    
    /** Pauses operation */
    static final int STOP = 2;
    
    /** Resets timer value. */
    static final int RESET = 3;
    
    /** For terminating this thread for a clean shutdown */
    static final int TERMINATE=4;
    
    private int status = START;
    private int elapsed=0;
    
    //Reference to YOUR class.  You may need to change the reference.
    RightPanel view=null;
    
    /**
    This method permits external control of the timer.
    Send ControllableTimer.START as the parameter to resume timing.
    ControllableTimer.STOP to pause timing.
    ControllableTimer.RESET to reset the timer to 0.
    @param cmd The control parameter, with values indicated above.
    */

    /**
    Constructor requires reference to a PiccrossView class.
    You may need to edit it.
    */
    
    public ControllableTimer (RightPanel remoteView)
    {
        view = remoteView;
    }

    /**
    Method through which the timer is controlled.  Timer starts on by default.
    @param cmd \nSTART: Starts timer.\nSTOP: Pauses timer.\nRESET: Sets timer back to 0.\nTERMINATE: Kills this thread for a clean shutdown.
    */

    public synchronized void setStatus(int cmd)
    {
        switch (cmd)
        {
            case START:
                status=START;
                notify();
                break;
            case STOP:
                status=STOP;
                break;
            case RESET:
                elapsed=0;
                break;
            case TERMINATE:
                status=TERMINATE;
        }
    }
    
    /**
    Permits checking the status of the timer. Mostly intended for internal use.
    @return status of the program in operation (START, STOP, TERMINATE)
    */
    
    public synchronized int getStatus()
    {
        if (status==STOP)
        {
            try
            {
                wait(); //wait can only be called from a synchronized method.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return status;
    }
    
    public synchronized int getTime()
    {
        return elapsed;
    }
    
    /**
    The timer's main loop.
    */
    
    public void run()
    {
        while (getStatus()!=TERMINATE)
        {
            try
            {
                sleep(1000); //Waits for 1000 milliseconds (one second)
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            
            //setTime method is part of your code.  Either modify this, or add an appropriate convenience method.
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                { 	
                    view.setTime(++elapsed); //This call updates your UI.
                }
            }); //End EventQueue

        }
    }
}