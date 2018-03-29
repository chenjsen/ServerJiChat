package com.example.administrator.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ServerSocket serverSocket = null;
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        try {
        	serverSocket = new ServerSocket(12345);
        	while(true){
		        Socket socket = null;
		        //2.����accept()�ȴ��ͻ�������
		        System.out.println("~~~������Ѿ������ȴ��ͻ��˽���~�������ip��ַ: " + ip);
		        socket = serverSocket.accept();

				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				JiChatMsg m=new JiChatMsg();
				User usr=(User) ois.readObject();
				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
				
				if(usr.getOperation().equals("login")){ //��¼
					if(isCorrect(usr)) {
						System.out.println("["+usr.getAccount()+"]�����ˣ�");
						m.setType(JiChatMessageType.SUCCESS);
						oos.writeObject(m);
						ServerClientThread sct=new ServerClientThread(socket);
						ServerClientThreadManager.addClientThread(usr.getAccount(),sct);
						sct.start();
					}
					else{
						m.setType(JiChatMessageType.FAIL);
						oos.writeObject(m);
					}
		        }
	        }
				
	        } catch (Exception e) {
				e.printStackTrace();
			}	        	
	}
	
	static boolean isCorrect(User user) {
		boolean result = false;
		for(User u:allUsers) {
			if((u.getAccount() == user.getAccount()) &&(u.getPassword().equals(user.getPassword()))) {
				result = true;
				break;
			}
		}
		return result;
	}

}
