package task1;

public class Channel {
	private CircularBuffer buff;
	private boolean connected;
	
	public int read(byte[] bytes, int offset, int length) {
		while(this.buff.empty());
		
		int nbRead = 0;
		
		while (nbRead < length && !this.buff.empty()) {
			nbRead++;
			bytes[offset++] = this.buff.pull();
		}
		
		return nbRead;
	}
	
	public int write(byte[] bytes, int offset, int length) {
		while(this.buff.full());
		
		int nbWritten = 0;
		
		while (nbWritten < length && !this.buff.full()) {
			nbWritten++;
			this.buff.push(bytes[offset++]);
		}
		
		return nbWritten;
	}
	
	public void disconnect() {
		this.connected = false;
	}
	
	public boolean disconnected() {
		return this.connected;
	}
}