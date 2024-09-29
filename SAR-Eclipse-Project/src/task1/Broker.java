package task1;

import java.util.HashMap;

public class Broker {
	String name;
	BrokerManager manager;
	HashMap<Integer, RdV> rdv;
	
	public Broker(String name) {
		this.name = name;
		this.rdv = new HashMap<	>(); 
		this.manager = BrokerManager.getSelf();
		this.manager.add(this);
	}
	
	public Channel accept(int port) throws InterruptedException {
		RdV rendezVous;
		synchronized (this.rdv) {
			rendezVous = rdv.get(port);
			if (rendezVous != null) {
				throw new InterruptedException("Already a rendez vous");
			}
			rendezVous = new RdV();
			rdv.put(port, rendezVous);
			rdv.notifyAll();
		}
		
		return rendezVous.accept(this, port);
	}
	
	public Channel connect(String name, int port) throws InterruptedException {
		Broker b = this.manager.getbroker(name);
		if (b == null) return null;
		return b.connectTo(this, port);
	}
	
	public Channel connectTo (Broker b, int port) throws InterruptedException {
		RdV rendezVous;
		synchronized (this.rdv) {
			rendezVous = rdv.get(port);
			while (rendezVous == null) {
				try {
					rdv.wait();
					rendezVous = rdv.get(port);
				} catch (InterruptedException e) {
					
				}
			}
			rdv.remove(port);
		}
		return rendezVous.connect(b, port);
	}
	
	public String getName () {
		return this.name;
	}
}
