package tests;

import task1.Broker;
import task1.Channel;
import task1.Channel.DisconnectedException;
import task1.Task;

public class testTask1 {
	
	final static int DATA_SIZE = 100;
	final static int PORT = 1000;

	public static void main(String[] args) {
		Broker bs = new Broker("server");
		new Task(bs, () -> {
			try {
				taskServer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Broker bc = new Broker("client");
		new Task(bc, () -> {
			try {
				taskClient();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private static void taskServer() throws InterruptedException {
		Broker broker = Task.getBrokerS();
		Channel channel = broker.accept(PORT);

		byte[] data = new byte[DATA_SIZE];
		int nbRead = 0;

		while (nbRead != -1) {
			try {
				nbRead = channel.read(data, 0, data.length);
				channel.write(data, 0, nbRead);
			} catch (DisconnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		channel.disconnect();
	}

	private static void taskClient() throws InterruptedException {
		Broker b = Task.getBrokerS();
		Channel c = b.connect("server", PORT);
		if (c == null) return;

		byte[] data = new byte[DATA_SIZE];
		for (int i = 0; i < DATA_SIZE; i++) {
			data[i] = (byte) i;
		}

		try {
			int nbWrite = 0;
			while (nbWrite < data.length) {
				nbWrite += c.write(data, nbWrite, DATA_SIZE - nbWrite);
			}

			byte[] receiveData = new byte[DATA_SIZE];
			int nbRead = c.read(receiveData, 0, DATA_SIZE);

			for (int i = 0; i < nbRead; i++)
				if (data[i] != receiveData[i]) return;

			if (nbRead != DATA_SIZE) return;
			
			System.out.println("Success");
		} catch (DisconnectedException e) {
			System.out.println("Failed");
		}
		
		c.disconnect();
	}
}