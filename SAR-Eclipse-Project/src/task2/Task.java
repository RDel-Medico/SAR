package task2;

import task1.Broker;

public class Task extends Thread {
	private Broker b;
	private QueueBroker qb;
	private Runnable r;
	
	public Task(Broker b, Runnable r) {
		this.b = b;
		this.r = r;
		this.start();
	}
	
	@Override
	public void run() {
		this.r.run();
	}
	
	public Task(QueueBroker b, Runnable r) {
		this.qb = b;
		this.r = r;
		this.start();
	}
	
	public Broker getBroker() {
		return this.b;
	}
	
	public QueueBroker getQueueBroker() {
		return this.qb;
	}
	
	public static Task getTask() {
		return (Task) Thread.currentThread();
	}
	
}
