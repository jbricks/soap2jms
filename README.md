[![Build Status](https://travis-ci.org/gcontini/soap2jms.svg?branch=master)](https://travis-ci.org/gcontini/soap2jms)

# Soap2Jms

This project expose a JMS queue through a standard SOAP over HTTP web service.
It allows to read and send messages from/to a JMS queue. Clients don't have to depend on any JMS implementation libraries. They are simple JAX-WS web service clients, that can be supported by any JAX-WS implementation (Apache CXF for instance).

It is not to be confused with "Soap over JMS". Soap Over JMS encapsulates a soap message into a JMS envelope. It has the same problems of JMS in terms of interoperability and it requires a JMS client implementation to connect to the server.

## goals:
 * Simplicity: It is simple to integrate. It has few dependencies. It will be well documented.
 * Reliability: JMS has a plus over SOAP: its reliability. Soap2Jms try to replicate this at the minimum cost: all the operations are idempotent. If clients are coded properly, messages will not be lost or duplicated in case of network or system failure.
 * Interoperability: It is independent from the specific JMS provider used. It can work in any application, without any change. Clients can be coded in languages other than java (.NET ...). Web services are WS-I Basic Profile 1 compliant. 

## Requirements

 - Java 7/8 
 - Jms 2.0, CDI
 - Currently tested in wildfly 10. Any JavaEE 6 container should be supported.    

## Current implementation status

The project is under active development (Dec/2016). 

Read message from a queue through a SOAP web service, and sending a message are completed (Text,Map).
 
These points will need to be addressed: 
 - Support for BytesMessages, StreamMessages and ObjectMessages.
 - Cleaning up error handling.
 - Documentation / web site 
 - make soap2jms simply configurable 
 - downgrade the JavaEE version required to 5 (?) ... 
 

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
