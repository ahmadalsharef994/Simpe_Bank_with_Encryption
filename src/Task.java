import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;
import javax.net.ssl.SSLSocket;
import com.didisoft.pgp.PGPLib;
import javax.crypto.BadPaddingException;

public class Task extends Thread{
          PrintWriter writer;
          BufferedReader reader;
          private SSLSocket ClientSocket = null;
          String hostname;
          
public Task(SSLSocket ClientSocket,String hostname) throws IOException,javax.crypto.IllegalBlockSizeException {
        try {    
        this.hostname=hostname;
        this.ClientSocket = ClientSocket;
        reader = new BufferedReader(new InputStreamReader(this.ClientSocket.getInputStream()));
        writer= new PrintWriter(this.ClientSocket.getOutputStream());
             } catch (Exception ex) {    }
    }//end constructor Task

    @Override
     public void run() {
        //open DataBase and insert new user
        String dbURL = "jdbc:mysql://127.0.0.1:3306/nsdb";
        String dbusername ="root";
        String dbpassword = "user";
        Connection dbCon = null;
        Statement stmt1 = null;
        ResultSet ResultSet1 = null;
          try { dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
               stmt1 = dbCon.createStatement();
                                Statement stmt2=dbCon.createStatement();
    stmt2.executeUpdate("INSERT INTO `user`(name,balance) VALUE ('"+hostname+"','"+1000+"')");
                         
}catch (SQLException ex) { }

try {

String AgentRequest =reader.readLine();
if ((AgentRequest.equals("PGP")))
{
    PGPLib pgp = new PGPLib();
           ServerFrame.output.append("\nClient needs to transfer money using PGP .\n\n");

//   String privateKeyFile = "c:\\private.key";
              String privateKeyFile = "private.key";

		String privateKeyPass = "changeit";
                      ServerFrame.output.append("private key file is : private.key\nprivate key password is : 'changeit'\n");
                      StringBuilder encRec=new StringBuilder();
                      String Temp =reader.readLine();
                      while (!(Temp.equals("-----END PGP MESSAGE-----")))
                              {
                                   encRec.append("\n"+Temp);
                                   Temp =reader.readLine();
                              }
                             encRec.append("\n"+Temp);
                              
                    //  System.out.println("\n"+encRec);
                      String decRec = pgp.decryptString(encRec.toString(), privateKeyFile, privateKeyPass);
                      StringBuilder encBal=new StringBuilder();

                      Temp =reader.readLine();
                      while (!(Temp.equals("-----END PGP MESSAGE-----")))
                              {
                                   encBal.append("\n"+Temp);
                                   Temp =reader.readLine();
                              }
                             encBal.append("\n"+Temp);
                              String decBal = pgp.decryptString(encBal.toString(), privateKeyFile, privateKeyPass);
                                ServerFrame.output.append("\nEncrypted reciver is : "+encRec+"\nEncrypted balance is"+encBal+"\n");
  
                               ServerFrame.output.append("Decrypted reciver and balance is : "+decRec+" :  "+decBal+"\n");
                              int balance = Integer.parseInt(decBal);
                              stmt1.execute("INSERT INTO `trans`(host1,host2,value) VALUE ('"+hostname+"','"+decRec+"','"+decBal+"')");

                               PreparedStatement ds= dbCon.prepareStatement("UPDATE user SET balance =(balance-?)WHERE user.name=?");
ds.setInt(1,balance);
ds.setString(2, hostname);
ds.executeUpdate();
ds.close();
PreparedStatement ps= dbCon.prepareStatement("UPDATE user SET balance =(balance +? )WHERE user.name = ?");
ps.setInt(1, balance);
ps.setString(2, decRec);
ps.executeUpdate();
ps.close();
//	String originalFileName = pgp.decryptFile("encrypted.pgp",  privateKeyFile,privateKeyPass, "OUTPUT.txt");   
//           ServerFrame.output.append((originalFileName)); 
}
else
if (AgentRequest.equals("RSA"))
{RSA rsa =new RSA(1024);

    ServerFrame.output.append("\ntransfer using RSA selected :\n");
 String host2enc=reader.readLine();
String host2=rsa.decrypt(host2enc);
System.out.println(host2enc);
//ServerFrame.output.append(host2);
String encbalance =reader.readLine();
int balance=Integer.parseInt(rsa.decrypt(encbalance));
    ServerFrame.output.append("Encrypted Reciver : "+host2enc+"\nencrypted balance : "+encbalance+"\n");
    ServerFrame.output.append("Decrypted Reciver : "+host2+"\nDecrypted balance : "+balance+"\n");

stmt1.execute("INSERT INTO `trans`(host1,host2,value) VALUE ('"+hostname+"','"+host2+"','"+balance+"')");
//ServerFrame.output.append(Integer.toString(balance));
PreparedStatement ds= dbCon.prepareStatement("UPDATE user SET balance =(balance-?)WHERE user.name=?");
ds.setInt(1,balance);
ds.setString(2, hostname);
ds.executeUpdate();
ds.close();
PreparedStatement ps= dbCon.prepareStatement("UPDATE user SET balance =(balance +? )WHERE user.name = ?");
ps.setInt(1, balance);
ps.setString(2, host2);
ps.executeUpdate();
ps.close();
//ServerFrame.output.append(Integer.toString(balance)); 
}
else if (AES.encrypt("GET AGENTS      ", AES.AESPublicKey).equals(AgentRequest))
 {

        ResultSet ResultSet2=stmt1.executeQuery("SELECT count(*) AS total FROM user ");
        ResultSet2.next();
        //total is rows count
        int total=ResultSet2.getInt("total");

        
 ResultSet1=stmt1.executeQuery("SELECT * FROM user ");
ResultSetMetaData rsmd=ResultSet1.getMetaData();
int columncount =rsmd.getColumnCount();

ArrayList <String> agentsList = new ArrayList<String>();
ArrayList <String> rowList = new ArrayList<String>(total);

// while there is more rows
//int k=0;
while (ResultSet1.next()) {
   // k++;
    int i = 1;
  //  while there is more columns
    while (i <= columncount) {
        rowList.add(ResultSet1.getString(i++));
    }
    agentsList.add(rowList.toString());
rowList.clear();
}

String Agents =agentsList.toString();
ServerFrame.output.append("done creating agents list : \n"+Agents);

String encAgentts = AES.encrypt(Agents, AES.AESPublicKey);
writer.println(encAgentts);
writer.flush();
ServerFrame.output.append("Encrypted List : \n"+encAgentts);
    }       //END IF C EQUALS GET AGENTS



}
//catch (BadPaddingException k) {System.out.println("baaad ");}

       catch (Exception e){System.out.println(e.getMessage()); System.out.println(e.toString());}
         
}//end run
}//end class Task
