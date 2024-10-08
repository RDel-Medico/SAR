package task3;

public class Task extends Thread {
	private QueueBroker qb;
	
	public Task(QueueBroker b, Runnable r) {
	}
	
	void post(Runnable r) {}
	static Task task() {return null;}
	void kill() {}
	boolean killed() {return false;}
	
	public QueueBroker getQueueBroker() {
		return this.qb;
	}
	
	public static Task getTask() {
		return (Task) Thread.currentThread();
	}
}
