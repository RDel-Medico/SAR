package tests;

import task1.Broker;
import task1.Channel.DisconnectedException;
import task2.MessageQueue;
import task2.QueueBroker;
import task2.Task;

public class testTask2 {
	final static int DATA_SIZE = 100;
	final static int PORT = 1000;

	public static void main(String[] args) {
		Broker bs = new Broker("server");
		QueueBroker qbs = new QueueBroker(bs);
		
		
		new Task(qbs, () -> {
			try {
				taskServer();
			} catch (InterruptedException | DisconnectedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error");
				e.printStackTrace();
			}
		});

		
		Broker bc = new Broker("client");
		QueueBroker qbc = new QueueBroker(bc);
		
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
		MessageQueue mq = qBroker.accept(PORT);
		

		byte[] temp;
		

		
		temp = mq.receive();
		
		
		for (int i = 0; i < DATA_SIZE; i++)
			if (data[i] != temp[i]) return;
		
		System.out.println("Success");
		
		mq.close();
	}

	private static void taskClient() throws InterruptedException, DisconnectedException {
		QueueBroker b = Task.getTask().getQueueBroker();
		MessageQueue mq = b.connect("server", PORT);
		
		if (mq == null) return;

		data = new byte[DATA_SIZE];
		for (int i = 0; i < DATA_SIZE; i++)
			data[i] = (byte) i;

		mq.send(data, 0, DATA_SIZE);
		
		mq.close();
	}
}