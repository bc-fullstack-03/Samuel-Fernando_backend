FROM eclipse-temurin:17-jdk-alpine
ADD target/sysmapparrot-*.jar sysmapparrot.jar
ENTRYPOINT [ "java", "-jar", "/sysmapparrot.jar" ]