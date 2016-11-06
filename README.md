[![Build Status](https://travis-ci.org/gcontini/soap2jms.svg?branch=master)](https://travis-ci.org/gcontini/soap2jms)

# Soap2Jms

This project expose a JMS queue through a standard SOAP over HTTP web service.
It allows to read and send messages from/to a JMS queue. Clients doesn't have dependency on any JMS implementation libraries. They are simple JAX-WS web service client, that can be used with any JAX-WS implementation (Apache CXF for instance).

It is not to be confused with "Soap over JMS". Soap Over JMS encapsulates a soap message into a jms envelope. It has the same problems of JMS in terms of interoperability and it requires a jms client implementation to connect to the server.

## goals:
 * Simplicity: It is simple to integrate. It has few dependencies. It will be well documented.
 * Reliability: JMS has a plus over SOAP: its reliability. Soap2Jms try to replicate this at the minimum cost: all the operations are idempotent. If clients are coded properly, messages will not be lost or duplicated in case of network or system failure.
 * Interoperability: It is independent from the specific JMS provider used. It can work in any application, without any change. Clients can be coded in languages other than java (.NET ...). Web services are WS-I Basic Profile 1 compliant. 

## Current implementation status
The project is under active development (Nov/2016). The winter is coming, i have some time in my week-ends.
Actually the web service to read message from a queue works with the following limitations:
 - Acknowledge method is not supported.
 - Only text messages are supported.
First step will be remove the limitations above. Then I will address the following points 
 - Cleaning up error handling.
 - Documentation / web site 
 - Implementation of the service to send messages to a queue
 - another iteration of documentation
 - improve configuration
 

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
  * It's rest. It doesn't have a wsdl to conform to. 
  
## ActiveSoap
Hosted on codehaus, the site seems dead (22/10/2016).
