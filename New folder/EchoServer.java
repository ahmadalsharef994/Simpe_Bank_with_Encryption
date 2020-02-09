
import java.io.*;
import java.security.Security;
import java.security.PrivilegedActionException;

import javax.net.ssl.*;
import com.sun.net.ssl.*;
import com.sun.net.ssl.internal.ssl.Provider;

/**
 * @author Joe Prasanna Kumar
 * This program simulates an SSL Server listening on a specific port for client requests
 * 
 * Algorithm:
 * 1. Regsiter the JSSE provider
 * 2. Set System property for keystore by specifying the keystore which contains the server certificate
 * 3. Set System property for the password of the keystore which contains the server certificate
 * 4. Create an instance of SSLServerSocketFactory
 * 5. Create an instance of SSLServerSocket by specifying the port to which the SSL Server socket needs to bind with
 * 6. Initialize an object of SSLSocket
 * 7. Create InputStream object to read data sent by clients
 * 8. Create an OutputStream object to write data back to clients.
 * 
 */ 


public class EchoServer {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws Exception{

		int intSSLport = 4443; // Port where the SSL Server needs to listen for new requests from the client

		{
			// Registering the JSSE provider
			Security.addProvider(new Provider());
                                
			//Specifying the Keystore details
			System.setProperty("javax.net.ssl.keyStore","E:\\NS Final\\src\\server.ks");

			System.setProperty("javax.net.ssl.keyStorePassword","password");
                                 System.out.println(System.getProperty("javax.net.ssl.keyStore"));

		}

		try {
				// Initialize the Server Socket
				SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
				SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(intSSLport);
				SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
                                            
                                                                             System.out.println("server");

           InputStreamReader streamreader = new InputStreamReader(sslSocket.getInputStream());
           BufferedReader  re = new BufferedReader(streamreader);
           PrintWriter  wr= new PrintWriter(sslSocket.getOutputStream(),true);

wr.println("fdfd");                       wr.flush();             System.out.println("server");

                                            sslSocket.close();

                      }
                      catch(Exception e){}

}
}
