package task1;

public abstract class Task extends Thread {
	public Task(Broker b, Runnable r){}
	public abstract Broker getBroker();
}
