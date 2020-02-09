import com.sun.net.ssl.internal.ssl.Provider;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;



public class Server {

 static String hostnames[]=new String[] {"zero", "one", "two", "three", "four",
     "five", "six", "seven", "eight", "nine","ten", "eleven", "twelve", "thirteen", "fourteen",
     "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

  public static void main(String[] args) {

		{
			// Registering the JSSE provider
			Security.addProvider(new Provider());
                                
			//Specifying the Keystore details
			System.setProperty("javax.net.ssl.keyStore","E:\\NS Final\\src\\server.ks");

			System.setProperty("javax.net.ssl.keyStorePassword","password");
                                 System.out.println(System.getProperty("javax.net.ssl.keyStore"));

                      }
   try {

                int Port = 5787;
                int NumberOfThreads = 20;
                SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
				SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(Port);
                ExecutorService threadExecutor = Executors.newFixedThreadPool(NumberOfThreads);
                Task Threads[] = new Task[NumberOfThreads];
                System.out.println("Server started ...");
                
                int j=0;
                
                while(true){
                    System.out.println("waiting for clients ...");
		SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
                    System.out.println("clieint "+j+ " connected");
                    Threads[j] = new Task(sslSocket,hostnames[j]);
                    threadExecutor.execute(Threads[j]);
        j++;
            }
        } 
        catch (Exception ex) {        }
    }//end main

}
