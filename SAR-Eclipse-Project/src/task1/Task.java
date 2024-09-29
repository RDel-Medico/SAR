package task1;

public class Task extends Thread {
	private Broker b;
	private Runnable r;
	
	public Task(Broker b, Runnable r){
		this.b = b;
		this.r = r;
		this.start();
	}
	
	@Override
	public void run() {
		this.r.run();
	}
	
	private Broker getBroker() {
		return this.b;
	}
	
	public static Broker getBrokerS() {
		return ((Task) currentThread()).getBroker();
	}
}
