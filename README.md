# soap2jms

This project expose a jms queue through a standard SOAP over HTTP web service.
It allows to read and send messages from/to a JMS queue. Clients doesn't have dependency on any JMS libraries. They are simple JAX-WS web service client, that can be used with any implementation (Apache CXF for instance).

It is NOT Soap over JMS. Soap Over JMS encapsulates a soap message into a jms envelope. It has the same problems of JMS in terms of interoperability and it requires a jms client implementation to connect to the server.

## goals:
 * Simplicity: It is simple to integrate. It has few dependencies. It will be well documented.
 * Reliability: All the operations are idempotent. If clients are coded properly, messages will not be lost in case of network or system failure.
 * Interoperability: It is independent from the specific jms provider used. It can work in any application server, without any change. 

# Similar projects

Before starting the implementation I've evaluated similar project, each one has it's own strenghts.

## [ActiveMQ REST](https://activemq.apache.org/artemis/docs/1.4.0/rest.html) Interface
ActiveMQ has a separate project that exposes most of the jms functions through a rest interface.
Pros:
  * Well mantained, it has a large community.
  * Well documented.
  * Well tested, security is ensured.

Cons:
  * The protocol is over complicated. It mimic jms. It requires to open a session, and later to close it.
  * It's ActiveMQ specific.
  * It's rest. It doesn't have a wsdl to conform to. 
  
## ActiveSoap
Hosted on codehaus, the site seems dead (22/10/2016).
