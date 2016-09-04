# ktspring
Various spring modules that allow to easily setup complicated integration like JTA+JPA+JMS

The following modules are available:

- atomikos: Atomikos implementation of JTA
- hibernate-jpa: Hibernate implementation of JPA
- activemq-artemis: ActiveMQ artemis implementation of JMS
- standalone-ee: Provides a standalone subset of JEE capabilities (JTA, JPA, JMS) for spring

*IMPORTANT NOTE*

Currently JPA + JTA (either atomikos or bitronix) is broken, see http://stackoverflow.com/questions/39312716/spring-hibernate-jpa-jta-atomikos-bitronix-failing
