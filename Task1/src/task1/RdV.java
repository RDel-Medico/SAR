package task1;

public class RdV {
	public Broker ba;
	public Broker bc;
	
	public RdV () {
		
	}
	
	public Channel connect (Broker b) {
		if (bc == null) {
			this.bc = b;
		} else {
			
		}
		
		return null;
	}
	
public Channel accept (Broker b) {
		if (ba == null) {
			this.ba = b;
		} else {
			
		}
		
		return null;
	}
}
