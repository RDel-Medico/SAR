# Overview: Message Queues

Message queues is based on the implementation of Channel / Broker, see "../preImplemTask1/specification.md" for more details.

A message queues is a framework to send and receive messages.
Those messages are sent and received as a whole and can have a variable size.

The typical use of message queues is by two tasks to establish a communication and exchange messages (String, Integer...).

# Connecting

A channel is established, in a fully connected state, when a connect 
matches an accept. When connecting, the given name is the one of the remote broker, the given port is the one of an accept on that remote broker.

There is no precedence between connect and accept, this is a symmetrical rendez-vous: the first operation waits for the second one. Both accept and connect operations are therefore blocking calls, blocking until the rendez-vous happens, both returning a fully connected and usable full-duplex channel.

When connecting, we may want to distinguish between two cases:
(i) there is no accept yet and (ii) there is not such broker. 
When the named broker does not exist, the connect returns null, 
but if the remote broker is found, the connect blocks until 
there is a matching accept otherwise so that a channel can be
constructed and returned. 

Note: we could consider introducing a timeout here, limiting the wait for the rendez-vous to happen.

# Sending message

Signature: send(byte[] bytes,int offset,int length)

The method send start by sending 4 bytes that represent an Integer. This Integer is the size of the message that will be sent in bytes.

When sending a message, the given byte array contains the bytes to send
from the given offset and for the given length. The range [4+offset,4+offset+length[ must be within the array boundaries, without wrapping around at either ends. 

The method "send" blocks if there is not enough room to write the message.

If the method "send" is currently blocked and the channel becomes
disconnected, the method will throw a channel exception. Invoking a write operation on a disconnected also throws a channel exception.

# Receiving

Signature: receive()byte[]

The method start by reading 4bytes that will be the size of the message read and the size of the byte array returned.

When receiving, the given byte array will contain the message,
starting at 0. The byte array will be the size of the message. The byte array represent the message.

The method "receive" blocks if there is less byte than mentionned in the Integer represented by the first 4 bytes.

# Communication details
All the communication details are mentionned in "../preImplemTask1/specification.md"
