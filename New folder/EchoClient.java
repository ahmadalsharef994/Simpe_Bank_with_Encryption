
import java.io.*;

import javax.net.ssl.*;
import com.sun.net.ssl.*;
import com.sun.net.ssl.internal.ssl.Provider;
import java.security.Security;

/**
 * @author Joe Prasanna Kumar
 * This program simulates a client socket program which communicates with the SSL Server
 * 
 * Algorithm:
 * 1. Determine the SSL Server Name and port in which the SSL server is listening
 * 2. Register the JSSE provider
 * 3. Create an instance of SSLSocketFactory
 * 4. Create an instance of SSLSocket
 * 5. Create an OutputStream object to write to the SSL Server
 * 6. Create an InputStream object to receive messages back from the SSL Server
 * 
 */ 

public class EchoClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String strServerName = "localhost"; // SSL Server Name
		int intSSLport = 4443; // Port where the SSL Server is listening
		PrintWriter out = null;
        BufferedReader in = null;
        			System.setProperty("javax.net.ssl.trustStore","E:\\NS Final\\src\\server.ks");


//		{
        
//			// Registering the JSSE provider
			Security.addProvider(new Provider());
//		}

		try {
			// Creating Client Sockets
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
			SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(strServerName,intSSLport);

                                 
                                 
           InputStreamReader streamreader = new InputStreamReader(sslSocket.getInputStream());
           BufferedReader  re = new BufferedReader(streamreader);
           PrintWriter  wr= new PrintWriter(sslSocket.getOutputStream(),true);
          
                                            System.out.println(re.readLine());

                                 sslSocket.close();
         
		}

		catch(Exception exp)
		{
			System.out.println(" Exception occurred .... " +exp);
			exp.printStackTrace();
		}

	}

}