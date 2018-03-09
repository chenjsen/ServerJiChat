package jichat;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ServerClientThread extends Thread{
	public Socket s;
	
	public ServerClientThread(Socket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		while(true) {
			ObjectInputStream ois = null;
			JiChatMessage jcm = null;
			InputStream is = null;
			try {
				is = s.getInputStream();
				ois=new ObjectInputStream(is);
				jcm=(JiChatMessage) ois.readObject();
				if(jcm.getType().equals(JiChatMessageType.COM_MES)) {
					try{
						//ȡ�ý����˵�ͨ���߳�
						ServerClientThread scc=ServerClientThreadManager.getClientThread(jcm.getReceiver());
						ObjectOutputStream oos=new ObjectOutputStream(scc.s.getOutputStream());
						//������˷�����Ϣ
						oos.writeObject(jcm);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				else if(jcm.getType().equals(JiChatMessageType.GET_ONLINE_FRIENDS)) {//�������ߺ���
					try{
						String res = ""+server.user1.getAcount() + " " +server.user2.getAcount() + " " + server.user3.getAcount();
						//ȡ�ý����˵�ͨ���߳�
						ServerClientThread scc=ServerClientThreadManager.getClientThread(jcm.getSender());
						ObjectOutputStream oos=new ObjectOutputStream(scc.s.getOutputStream());
						JiChatMessage jcm_friends = new JiChatMessage();
						jcm_friends.setType(JiChatMessageType.GET_ONLINE_FRIENDS);
						jcm_friends.setContent(res);
						//������˷�����Ϣ
						oos.writeObject(jcm_friends);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
