package cn.et;

import org.I0Itec.zkclient.ZkClient;

public class ZkAAA {

	public static void main(String[] args) {
		//zookeeper�����ӵ�ַ 
		String zkUrl = "localhost:2181";
		
		//zookeeper���ӷ����ʵ��
		ZkClient zk = new ZkClient(zkUrl, 10000,5000);
		//�޸�����
		zk.writeData("/db", "oracle");
		
	}

}
