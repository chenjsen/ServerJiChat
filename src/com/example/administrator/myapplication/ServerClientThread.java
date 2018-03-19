package com.example.administrator.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ServerClientThread extends Thread{
	public Socket s;
	public ObjectInputStream ois;
	public ObjectOutputStream oos;
	
	public ServerClientThread(Socket s,ObjectInputStream ois) {
		this.ois = ois;
		this.s = s;
	}
	
	@Override
	public void run() {
		while(true) {
			//ObjectInputStream ois = null;
			JiChatMsg jcm = null;
			//InputStream is = null;
			try {
				//is = s.getInputStream();
				//ois=new ObjectInputStream(is);
				jcm=(JiChatMsg) ois.readObject();
				if(jcm.getType().equals(JiChatMessageType.COM_MES)) {
					try{
						//取得接收人的通信线程
						ServerClientThread scc=ServerClientThreadManager.getClientThread(jcm.getReceiver());
						ObjectOutputStream oos=new ObjectOutputStream(scc.s.getOutputStream());
						//向接收人发送消息
						oos.writeObject(jcm);
					}catch(Exception e){
						e.printStackTrace();
						break;
					}
				}
				else if(jcm.getType().equals(JiChatMessageType.GET_ONLINE_FRIENDS)) {//返回在线好友
					try{
						String res = server.user1.getAccount() + " " +server.user2.getAccount() + " " + server.user3.getAccount();
						//取得接收人的通信线程
						ServerClientThread scc=ServerClientThreadManager.getClientThread(jcm.getSender());
						ObjectOutputStream oos=new ObjectOutputStream(scc.s.getOutputStream());
						JiChatMsg jcm_friends = new JiChatMsg();
						jcm_friends.setType(JiChatMessageType.RET_ONLINE_FRIENDS);
						jcm_friends.setContent(res);
						//向接收人发送消息
						oos.writeObject(jcm_friends);
						System.out.println(res);
					}catch(Exception e){
						e.printStackTrace();
						break;
					}
					
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				try {
					e.printStackTrace();
					s.close();
					ois.close();
				} catch (IOException e1) {	
					e1.printStackTrace();
				}
				e.printStackTrace();
				break;
			}
		}
	}
}
