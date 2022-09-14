package VehicleControl;

import FaultMonitor.FaultMonitor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receiver extends UnicastRemoteObject implements ReceiverInterface {

    private static final int MONITORING_INTERVAL = 4000;
    private static final String REGISTRY_HOST = "localhost";
    private static long lastHeartbeatTime;
    private static int currentlocation;

    protected Receiver() throws RemoteException {
    }

    public void receiver(){
        Registry registry;
        try{
            /*create and export remote object*/
            Receiver receiver = new Receiver();

            /*register remote object with registry*/
            registry = LocateRegistry.getRegistry(REGISTRY_HOST);
            registry.rebind("VehicleControl.ReceiverInterface", receiver);

        }catch(Exception e){
            System.out.println(" VehicleControl.Receiver: Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* monitors the time difference between the last 2 heart beat signals */
    @Override
    public void healthMonitor() throws RemoteException {

        while (true) {
            try {
                Thread.sleep(MONITORING_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!isAlive()) {
                System.out.println("VehicleControl.Receiver: Heartbeat interval exceeded - Localization Component failed - View log for details");
                FaultMonitor.handleFault("Localization", this);
            }
        }
    }

    private boolean isAlive(){
        long interval = System.currentTimeMillis() - lastHeartbeatTime;
        int error = 100; //100ms error tolerable
        return interval <= (MONITORING_INTERVAL + error);
    }

    public int getCurrentlocation(){
        return this.currentlocation;
    }

    public void status(int location) throws RemoteException{
        this.currentlocation = location;
        lastHeartbeatTime = System.currentTimeMillis();
        System.out.println("VehicleControl.Receiver: received current location: " + location);
        System.out.println("VehicleControl.Receiver: Received heartbeat signal at : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static void main(String [] args) throws RemoteException {
        Receiver receiver = new Receiver();
        receiver.receiver();
        try{
            receiver.healthMonitor();
        }catch(Exception ex){
            System.out.println("Vehicle control - receiver exception  - " + ex.getMessage());
        }
    }

}
