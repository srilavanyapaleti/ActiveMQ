# ActiveMQ
Determine how the redelivery schedule works when there are multiple messages waiting for the redelivery. 
The queue seems to get blocked leading to slow processing of messages. This project is developed to understand the
activemq redelivery mechanism and concurrent processing of messages.


The project can be imported as a maven project and the war is deployable on tomcat.
Using ActiveMQ console, we can send the messages which are picked by the listeners for the redelivery.

