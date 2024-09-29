package task1;

public class RdV {
	public Channel ca;
	public Channel cc;
	public Broker ba;
	public Broker bc;
	
	synchronized public Channel connect (Broker b, int port) throws InterruptedException {
		this.bc = b;
		this.cc = new Channel();
		if (this.ca != null) {
			this.cc.connect(this.ca, this.bc.getName());
			notify();
		} else {
			while (this.ca == null || this.cc == null) {
				wait();
			}
		}
		
		return cc;
	}
	
	synchronized public Channel accept (Broker b, int port) throws InterruptedException {
		this.ba = b;
		this.ca = new Channel();
		if (this.cc != null) {
			this.ca.connect(this.cc, this.ba.getName());
			notify();
		} else {
			while (this.cc == null || this.ca == null) {
				wait();
			}
		}
		
		return ca;
	}
}
