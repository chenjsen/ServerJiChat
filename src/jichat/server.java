package jichat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class server {
	
	public static User user1 = new User(111,"111");
	public static User user2 = new User(222,"222");
	public static User user3 = new User(333,"333");
	public static Vector<User> allUsers = new Vector<User>();
	
	
	public static void main(String[] args) throws IOException {
		//1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
		int account;
		String passWord;
		String ClientIp;
        ServerSocket serverSocket = new ServerSocket(12345);
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        while(true)
        {
	        Socket socket = null;
	        //2.调用accept()等待客户端连接
	        System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址: " + ip);
	        socket = serverSocket.accept();
	        //3.连接后获取输入流，读取客户端信息
	        InputStream is = socket.getInputStream();;
	        InputStreamReader isr = new InputStreamReader(is,"UTF-8");;
	        BufferedReader br = new BufferedReader(isr);
	        
	        //output
	        OutputStream os = socket.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
	        BufferedWriter bw = new BufferedWriter(osw);
	        PrintWriter pout = new PrintWriter(bw,true); 
	        
	        String temp = null;
	        String info = "";
	        while((temp=br.readLine())!=null){//循环读取客户端的信息
	        	info += temp;
	            System.out.println("客户端发送过来的信息" + info);
	        }
	
	        String []getStr=info.split(" ");
	    	if("login".equals(getStr[0])) {
	    		ClientIp = getStr[1];
	    		account = Integer.parseInt(getStr[2]);
	    		passWord = getStr[3];
	    		User usr = new User(account,passWord);
                System.out.println(allUsers.toString());
	    		//如果是预留的这两个账户，则登录成功，返回0
	    		if(usr.isCorrectAcount()) {
	    			
	                pout.println(0);  
	                System.out.println("密码正确");
	                //开辟一条线程保持长连接
	                //ServerClientThread sct=new ServerClientThread(socket);//单开一个线程，让该线程与该客户端保持连接
					//ServerClientThreadManager.addClientThread(usr.getAcount(),sct);
					//sct.start();//启动与该客户端通信的线程
	    		}
	    		else {
	    			pout.println(1);  
	    		}
	        }	
	
	        //socket.shutdownInput();//关闭输入流
	        //socket.close();
	
		}
	}

}
