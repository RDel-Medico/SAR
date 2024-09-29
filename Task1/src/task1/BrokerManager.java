package task1;

import java.util.HashMap;

public class BrokerManager {
	protected static BrokerManager self;
	
	static { // Automatiquement executé à l'initialisation de la classe (1 bloc static par classe)
		self = new BrokerManager();
	}
	
	public static BrokerManager getSelf() {
		return self;
	}
	
	HashMap<String, Broker> brokers;
	
	public BrokerManager() {
		this.brokers = new HashMap<String, Broker>();
	}
	
	public synchronized void add(Broker b) {
		String name = b.getName();
		if (this.brokers.containsKey(name))
			throw new IllegalStateException("Can't have two broker with the same name!");
		
		this.brokers.put(name, b);
	}
	
	public synchronized Broker getbroker(String name) {
		return this.brokers.get(name);
	}
}
