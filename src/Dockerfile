FROM tomcat:9.0.118-jdk8-corretto-al2                                                                                                              
RUN rm -rf /usr/local/tomcat/webapps/                                                                                                                         
COPY /insurance-app/target/insurance-app.war /usr/local/tomcat/webapps/ROOT.war                                                               
EXPOSE 8080                                                                                                                                                   
CMD ["catalina.sh","run"] 
