package task2;

import task1.Channel;
import task1.Channel.DisconnectedException;

public class MessageQueue {
	
	private Channel c;
	
	public MessageQueue (Channel c) {
		this.c = c;
	}
	
	public void send(byte[] bytes, int offset, int length) throws DisconnectedException {
		
		byte[] size = new byte[4];
		int temp = length;
		
		for (int i = 0; i < size.length; i++) {
			size[i] = (byte)temp;
			temp = temp >> 8;
		}
		
		int nbBytes = 0;
		while (nbBytes < 4) {
			nbBytes += this.c.write(size, nbBytes, 4-nbBytes);
		}
		
		
		
		nbBytes = offset;
		
		while (nbBytes < offset+length) {
			
			nbBytes += this.c.write(bytes, nbBytes, offset+length - nbBytes);
			
		}
		
	}
	
	public int readSize() throws DisconnectedException {
		int nbBytes = 0;
		byte[] size = new byte[4];
		while (nbBytes < 4) {
			
			nbBytes += this.c.read(size, nbBytes, 4-nbBytes);
			
		}
		int res = 0;
		
		for (int i = size.length - 1; i > 0; i--) {
			res |= size[i];
			res = res << 8;
		}
		res |= size[0];
		
		return res;
	}
	
	
	public byte[] receive() throws DisconnectedException {
		int messSize = readSize();
		
		int nbBytes = 0;
		byte[] mess = new byte[messSize];
		while (nbBytes < messSize) {
			nbBytes += this.c.read(mess, nbBytes, messSize-nbBytes);
		}
		
		return mess;
	}
	
	public void close() {
		this.c.disconnect();
	}
	
	public boolean closed() {
		return this.c.disconnected();
	}
	
}