ARG version="27-ea-slim"                                                                                                                                      
FROM openjdk:$version AS Builder                                                                                                                              
RUN apt update -y && apt install maven -y && apt install git -y                                                                                               
RUN git clone https://github.com/laharikonda22/insurance-app.git                                                                                              
WORKDIR /insurance-app                                                                                                                                        
RUN git pull origin main                                                                                                                                     
RUN mvn clean install  

FROM tomcat:9.0.118-jdk8-corretto-al2 AS Deploy                                                                                                               
RUN rm -rf /usr/local/tomcat/webapps/                                                                                                                         
COPY --from=Builder /insurance-app/target/insurance-app.war /usr/local/tomcat/webapps/ROOT.war                                                               
EXPOSE 8080                                                                                                                                                   
CMD ["catalina.sh","run"] 
