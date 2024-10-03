# Overview: Message Queues Event Based

This task is based on the implementation of Channel / Broker and Message Queues, see "../preImplemTask1/specification.md" and "../preImplemTask2/specification.md" for more details.

An Event based message queues is a framework to send and receive messages.
Those messages are sent and received as a whole and can have a variable size.

# Event Based

The main advantage of this event based implementation of Message Queues is that instead of the basic implementation this one is none blocking.
To do so we use listener that will be triggered every time there is an event.

# Connecting

The connection system uses listener that are setup when a connection is started from one side that will be triggered when the second side try to connect.

Signature: bind(int port, AcceptListener listener)boolean
Try to setup a listener on the port that will be triggered if there is an event. Return true if the listener is setup successfully and false otherwise.

Signature: unbind(int port)boolean
Try to kill the listener on the port (there must be a listener on this port). Return true if the listener is killed successfully and false otherwise.

# Listener
A listener set on a messageQueue can be triggered during specific event:

Signature: received(byte[] msg)
Triggered when a message is received

Signature: sent(Message message)
Triggered when a message is sent

Signature: closed()
Triggered when the connection is closed

# Sending / Receiving

Ignoring the event side of this task the sending / receiving is the same as for MessageQueue : "../preImplemTask2/specification.md"


# Communication details
All the communication details are mentionned in "../preImplemTask1/specification.md"
