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
		//1.����һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
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
	        //2.����accept()�ȴ��ͻ�������
	        System.out.println("~~~������Ѿ������ȴ��ͻ��˽���~�������ip��ַ: " + ip);
	        socket = serverSocket.accept();
	        //3.���Ӻ��ȡ����������ȡ�ͻ�����Ϣ
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
	        while((temp=br.readLine())!=null){//ѭ����ȡ�ͻ��˵���Ϣ
	        	info += temp;
	            System.out.println("�ͻ��˷��͹�������Ϣ" + info);
	        }
	
	        String []getStr=info.split(" ");
	    	if("login".equals(getStr[0])) {
	    		ClientIp = getStr[1];
	    		account = Integer.parseInt(getStr[2]);
	    		passWord = getStr[3];
	    		User usr = new User(account,passWord);
                System.out.println(allUsers.toString());
	    		//�����Ԥ�����������˻������¼�ɹ�������0
	    		if(usr.isCorrectAcount()) {
	    			
	                pout.println(0);  
	                System.out.println("������ȷ");
	                //����һ���̱߳��ֳ�����
	                //ServerClientThread sct=new ServerClientThread(socket);//����һ���̣߳��ø��߳���ÿͻ��˱�������
					//ServerClientThreadManager.addClientThread(usr.getAcount(),sct);
					//sct.start();//������ÿͻ���ͨ�ŵ��߳�
	    		}
	    		else {
	    			pout.println(1);  
	    		}
	        }	
	
	        //socket.shutdownInput();//�ر�������
	        //socket.close();
	
		}
	}

}
