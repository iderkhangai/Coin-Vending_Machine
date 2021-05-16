FROM java:11
EXPOSE 8080
ADD target/ibm-vm.jar ibm-vm.jar
ENTRYPOINT ["java", "-jar", "ibm-vm.jar"]