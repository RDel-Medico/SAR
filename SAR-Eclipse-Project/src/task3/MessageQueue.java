package task3;

public class MessageQueue {
	public interface Listener {
		void received(byte[] msg);
		void sent(Message msg);
		void closed();
	}
	
	public void setListener(Listener l) {}
	public boolean send(Message msg) {return false;}
	public void close() {}
	public boolean closed() {return false;}
}