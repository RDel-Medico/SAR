package task1;

public class Broker {
	String name;
	BrokerRegistry registry;
	
	public Broker(String name) {
		this.name = name;
	}
	
	public Channel accept(int port) {
		return null;
	}
	
	public Channel connect(String name, int port) {
		return null;
	}
}
