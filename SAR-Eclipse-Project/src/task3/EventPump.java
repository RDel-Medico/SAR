package task3;

public class EventPump extends Thread {
	
	public synchronized void post(Event event) {}
	
	public void kill() {}
	
	public static synchronized EventPump getInstance() {
        return null;
    }
}
