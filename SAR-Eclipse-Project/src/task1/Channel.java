package task1;

public class Channel {
	
	@SuppressWarnings("serial")
	public class DisconnectedException extends Exception { 
	    public DisconnectedException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	private CircularBuffer buffIn;
	private CircularBuffer buffOut;
	private Channel remoteCh;
	private boolean disconnected;
	public boolean dangling;
	
	public Channel () {
		this.buffIn = new CircularBuffer(1000);
	}
	
	public int read(byte[] bytes, int offset, int length) throws DisconnectedException {
		if (disconnected) throw new DisconnectedException("Can't read if you're not connected");
		
		int nbRead = 0;
		
		try {
			while (nbRead == 0) {
				if (this.buffIn.empty()) {
					synchronized(this.buffIn) {
						while(this.buffIn.empty()) {
							if (this.disconnected) {
								throw new DisconnectedException("Can't read if you're not connected");
							}
							try {
								this.buffIn.wait();
							} catch (InterruptedException e) {
								
							}
						}
					}
				}
				while (nbRead < length && !this.buffIn.empty()) {
					bytes[offset + nbRead++] = this.buffIn.pull();
				}
				if (nbRead != 0) {
					synchronized (this.buffIn) {
						this.buffIn.notify();
					}
				}
			}
		} catch (DisconnectedException e) {
			if (!this.disconnected) {
				this.disconnected = true;
				synchronized (this.buffOut) {
					this.buffOut.notifyAll();
				}
			}
			throw e;
		}
		
		return nbRead;
	}
	
	public int write(byte[] bytes, int offset, int length) throws DisconnectedException {
		if (disconnected) throw new DisconnectedException("Can't write if you're not connected");
		
		int nbWritten = 0;
		
		while (nbWritten == 0) {
			if (this.buffOut.full()) {
				synchronized(this.buffOut) {
					while(this.buffOut.full()) {
						if (this.disconnected || this.dangling) {
							throw new DisconnectedException("Can't read if you're not connected");
						}
						if (this.dangling) {
							return length;
						}
						try {
							this.buffOut.wait();
						} catch (InterruptedException e) {
							
						}
					}
				}
			}
			while (nbWritten < length && !this.buffOut.full()) {
				this.buffOut.push(bytes[offset + nbWritten++]);
			}
			if (nbWritten != 0) {
				synchronized (this.buffOut) {
					this.buffOut.notify();
				}
			}
		}
		
		return nbWritten;
	}
	
	public void disconnect() {
		synchronized(this) {
			if (this.disconnected) {
				return;
			}
			this.disconnected = true;
			this.remoteCh.dangling = true;
			this.notifyAll();
		}
		
		synchronized(this.buffOut) {
			this.buffOut.notifyAll();
		}
		
		synchronized(this.buffIn) {
			this.buffIn.notifyAll();
		}
	}
	
	public boolean disconnected() {
		return this.disconnected;
	}

	public void connect(Channel cc, String name) {
		this.remoteCh = cc;
		cc.remoteCh = this;
		this.buffOut = cc.buffIn;
		cc.buffOut = this.buffIn;
	}
}