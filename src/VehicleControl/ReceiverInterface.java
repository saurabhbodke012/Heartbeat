package VehicleControl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReceiverInterface extends Remote {

    public void receiver () throws RemoteException;

    public void status(int location) throws RemoteException;

    public void healthMonitor() throws RemoteException;

}
