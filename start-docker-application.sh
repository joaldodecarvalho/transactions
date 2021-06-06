mvn spring-boot:build-image
docker run -it -p8080:8080 transactions:0.0.1-SNAPSHOT 
