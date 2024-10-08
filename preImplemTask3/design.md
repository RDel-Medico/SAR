# Event / Thread mix

## EventPump

The EventPump is the core component responsible for managing the lifecycle of tasks in an event-driven manner. Its main objective is to ensure that events are processed efficiently and in order, without blocking the system.

- **Threaded Execution**: The EventPump runs on a separate thread, constantly checking for new events.
- **Event Queue**: Events are stored in a FIFO queue, ensuring they are processed in the order they arrive.
- **Non-Blocking Mechanism**: A timeout is implemented for long-running tasks, preventing the EventPump from getting blocked.
- **Re-Postable Tasks**: If a task cannot be completed within the timeout, it is re-queued for later processing.

## TaskEvent

A TaskEvent is a generalized abstraction for any unit of work (task) that needs to be executed. TaskEvents are managed by the EventPump and have specific responsibilities depending on their specialization (e.g., accepting a connection, sending a message, etc.).

- **Specialization**: TaskEvent has multiple specialized subclasses (e.g., AcceptingTaskEvent, ConnectingTaskEvent).
- **Execution Logic**: Each specialization implements the run() method to perform its unique task.
- **Post and Re-Post Mechanisms**: Tasks can post themselves to the event queue, and handle retries as necessary.

## QueueBroker

The QueueBroker manages the lifecycle of network connections. It is responsible for both accepting incoming connections and connecting to external brokers.

- **Connection Management**: Handles binding, connecting, and accepting tasks.
- **Event-Driven Notification**: Uses the EventPump to trigger appropriate events (like connection established, refused, etc.) to the listener.
- **Thread-Based Processing**: While the EventPump handles the event-based flow, threads manage the actual connection processing, such as handling sockets.

## MessageQueue

The MessageQueue is a mechanism for managing full-duplex communication between brokers. It handles sending and receiving messages asynchronously, ensuring messages are processed efficiently without blocking.

- **Full-Duplex Communication**: Each broker has two MessageQueues, one for sending and one for receiving, enabling simultaneous communication.
- **Blocking Send/Receive with Timeout**: Each send and receive operation is wrapped with a timeout mechanism to prevent blocking the EventPump.
- **Event-Driven Notifications**: The MessageQueue uses the EventPump to trigger received(), sent(), and closed() events on the listener.

## Specialized TaskEvents
### AcceptingTaskEvent
Handles incoming connection requests by polling the server's listening socket. If a connection attempt is detected, it accepts the connection and notifies the listener.

### ConnectingTaskEvent
Attempts to establish an outgoing connection to another broker. If successful, it notifies the listener.

### SendingTaskEvent

Handles message sending operations using blocking I/O. A timeout ensures that if the operation stalls, it is retried.

## Closing System

The Closing System relies on the MessageQueue's channels. When a channel is closed, the corresponding listener is notified. The system ensures that no further communication is possible after closure.

- **Channel Close Event**: The event pump triggers the closed() event when a channel is closed.
- **Disconnect Notification**: Listeners are notified when disconnection happens.
