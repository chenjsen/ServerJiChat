package com.example.administrator.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ServerClientThreadManager {

	public static HashMap hm=new HashMap<Integer,ServerClientThread>();
	
	//���һ���ͻ���ͨ���߳�
	public static void addClientThread(int account, ServerClientThread cc){
		hm.put(account,cc);
	}
	//�õ�һ���ͻ���ͨ���߳�
	public static ServerClientThread getClientThread(int i){
		return (ServerClientThread)hm.get(i);
	}
	//���ص�ǰ�����˵����
	public static List getAllOnLineUserid(){
		List list=new ArrayList();
		//ʹ�õ��������
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			list.add((int) it.next());
		}
		return list;
	}
	
	public static boolean isOnline(int a){
		List list=new ArrayList();
		//ʹ�õ��������
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			int account=(int) it.next();
			if(account==a){
				return true;
			}
		}
		return false;
	}

}
