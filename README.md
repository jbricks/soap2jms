[![Build Status](https://travis-ci.org/jbricks/soap2jms.svg?branch=master)](https://travis-ci.org/jbricks/soap2jms)

#Jms Over Soap

This project allows to access a JMS queue through a standard SOAP over HTTP web service.
It allows to read and send messages from/to a JMS queue. Clients don't have to depend on any JMS implementation libraries. They are simple JAX-WS web service clients, that can be supported by any JAX-WS implementation (Apache CXF for instance).

It is not to be confused with "Soap over JMS". Soap Over JMS encapsulates a soap message into a JMS envelope. It has the same problems of JMS in terms of interoperability and it requires a JMS client implementation to connect to the server.

Visit the [website](https://jbricks.github.io/soap2jms/index.html) for more informations.

## goals:
 * Simplicity: It is simple to integrate. It has few dependencies. It will be well documented.
 * Reliability: JMS has a plus over SOAP: its reliability. Soap2Jms try to replicate this at the minimum cost: all the operations are idempotent. If clients are coded properly, messages will not be lost or duplicated in case of network or system failure.
 * Interoperability: It is independent from the specific JMS provider used. It can work in any application, without any change. Clients can be coded in languages other than java (.NET ...). Web services are WS-I Basic Profile 1 compliant. 



# Similar projects

Before starting the implementation I've evaluated similar project, each one has it's own strengths.

## [ActiveMQ REST](https://activemq.apache.org/artemis/docs/1.4.0/rest.html) Interface
ActiveMQ has a separate project that exposes most of the JMS functions through a rest interface.
Pros:
  * Well manteined, it has a large community.
  * Well documented.
  * Well tested, security is ensured.

Cons:
  * The protocol is over complicated. It mimic JMS. It requires to open a session, and later to close it.
  * It's ActiveMQ specific.
  * It's JSON over HTTP. It doesn't have a WSDL to conform to (only a very lengthly description of the protocol). 

## [OpenMQ UMS](https://mq.java.net/4.3-content/ums/umsIntro.html) Interface



## ActiveSoap
Hosted on codehaus, the site seems dead (10/11/2016).
