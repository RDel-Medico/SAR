package tests;

import task1.Broker;
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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Broker bc = new Broker("client");
		QueueBroker qbc = new QueueBroker(bc);
		new Task(qbc, () -> {
			try {
				taskClient();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private static void taskServer() throws InterruptedException {
		QueueBroker broker = Task.getQueueBroker();
		MessageQueue mq = broker.accept(PORT);

		byte[] data;

		data = mq.receive();
		mq.send(data, 0, data.length);
			
		mq.close();
	}

	private static void taskClient() throws InterruptedException {
		QueueBroker b = Task.getQueueBroker();
		MessageQueue mq = b.connect("server", PORT);
		if (mq == null) return;

		byte[] data = new byte[DATA_SIZE];
		for (int i = 0; i < DATA_SIZE; i++)
			data[i] = (byte) i;

		mq.send(data, 0, DATA_SIZE);

		byte[] receiveData = mq.receive();

		for (int i = 0; i < DATA_SIZE; i++)
			if (data[i] != receiveData[i]) return;
		
		System.out.println("Success");
		mq.close();
	}
}