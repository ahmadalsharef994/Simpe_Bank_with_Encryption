
import java.io.IOException;
import java.io.OutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class mmmm {
  public static void main(String[] args) 
     {
          try {
    // Execute command
    String command = "cmd /c start C:\\Users\\USER\\Documents\\NetBeansProjects\\NS\\src\\java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 EchoServer";
    Process child = Runtime.getRuntime().exec(command);
    child = Runtime.getRuntime().exec("java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 EchoServer");
    /*
    // Get output stream to write from it
    OutputStream out = child.getOutputStream();

    out.write("java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 EchoServer".getBytes());
    out.flush();
    out.write("java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=123456 EchoClient".getBytes());
    out.close();
     out.write("-Djava.protocol.handler.pkgs=com.sun.net.ssl.internal.www.protocol -Djavax.net.debug=ssl".getBytes());
    out.close();*/
} catch (IOException e) {
}
     }}
