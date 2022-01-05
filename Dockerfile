FROM openjdk:17.0-jdk-slim
COPY target/shopping-cart-service-0.0.1-SNAPSHOT.jar target/shopping-cart-service-0.0.1-SNAPSHOT.jar
CMD java $JAVA_OPTS -jar target/shopping-cart-service-0.0.1-SNAPSHOT.jar
