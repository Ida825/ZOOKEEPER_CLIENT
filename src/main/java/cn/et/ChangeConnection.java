package cn.et;

import org.I0Itec.zkclient.ZkClient;

public class ChangeConnection {

	public static void main(String[] args) {
		//zookeeper�����ӵ�ַ
		String zkUrl = "localhost:2181";
		//�������ӷ����ʵ��
		ZkClient zk = new ZkClient(zkUrl, 100000, 5000);
		//�ı���������
		zk.writeData("/db/url", "jdbc:oracle:thin:@localhost:1521:orcl");
		zk.writeData("/db/driverClass", "oracle.jdbc.OracleDriver");
		zk.writeData("/db/username", "stu");
		zk.writeData("/db/password", "123456");
	}

}
