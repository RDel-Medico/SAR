package task2;

import task1.Broker;

public class QueueBroker {
	Broker b;
	
	public QueueBroker(Broker broker) {
		this.b = broker;
	}
	
	public String getName() {
		return this.b.getName();
	}
	
	public MessageQueue accept(int port) throws InterruptedException {
		return new MessageQueue(this.b.accept(port));
	}
	
	public  MessageQueue connect(String name, int port) throws InterruptedException {
		return new MessageQueue(this.b.connect(name, port));
	}
}