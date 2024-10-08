package tests;

import task1.Channel.DisconnectedException;
import task3.EventPump;
import task3.Message;
import task3.MessageQueue;
import task3.QueueBroker;
import task3.Task;

public class testTask3 {
	final static int DATA_SIZE = 100;
	final static int PORT = 1000;

	public static void main(String[] args) {

		EventPump.getInstance().start();

		QueueBroker qbs = new QueueBroker("Server");

		new Task(qbs, () -> {
			try {
				taskServer();
			} catch (InterruptedException | DisconnectedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error");
				e.printStackTrace();
			}
		});

		QueueBroker qbc = new QueueBroker("client");

		new Task(qbc, () -> {
			try {
				taskClient();
			} catch (InterruptedException | DisconnectedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error");
				e.printStackTrace();
			}
		});
	}

	static byte[] data;

	private static void taskServer() throws InterruptedException, DisconnectedException {
		QueueBroker qBroker = Task.getTask().getQueueBroker();
		AcceptListener l = new AcceptListener();
		
		if (!qBroker.bind(8080, l)) return;
	}

	private static void taskClient() throws InterruptedException, DisconnectedException {
		QueueBroker qBroker = Task.getTask().getQueueBroker();
		ConnectListener l = new ConnectListener();
		if (!qBroker.connect("server", 8080, l)) return;
	}
}

class AcceptListener implements QueueBroker.AcceptListener {

	@Override
	public void accepted(MessageQueue mq) {
	}
}

class ConnectListener implements QueueBroker.ConnectListener {

	@Override
	public void connected(MessageQueue mq) {
	}

	@Override
	public void refused() {
	}
	
}