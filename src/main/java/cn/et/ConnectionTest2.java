package cn.et;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class ConnectionTest2 {
	static Connection conn=null;
	static ZkClient zk=null;
	public static void main(String[] args) throws InterruptedException {

		//zookeeper�����ӵ�ַ
		String zkUrl = "localhost:2181";
		//�������ӷ����ʵ��
		zk = new ZkClient(zkUrl, 100000, 5000,new BytesPushThroughSerializer());

		//�������ݿ�
		getConnection(zk);
		
		//���db���ݸı� (��������)
		zk.subscribeDataChanges("/db/url", new IZkDataListener() {
			
			//���ݱ�ɾ��ʱ����
			public void handleDataDeleted(String path) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			//���ݱ��ı�ʱ����
			public void handleDataChange(String path, Object arg1) throws Exception {
				getConnection(zk);
			}
		});
		
		//ȷ������ļ��һֱ��Ч
		while(true){
			TimeUnit.SECONDS.sleep(5);
		}
	}

	/**
	 * �������ݿ�
	 * @param zk
	 */
	private static void getConnection(ZkClient zk) {
		
		byte[] url = zk.readData("/db/url");
		byte[] driverClass = zk.readData("/db/driverClass");
		byte[] username = zk.readData("/db/username");
		byte[] password = zk.readData("/db/password");
		
		try {
			Class.forName(new String(driverClass));
			conn = DriverManager.getConnection(new String(url),new String(username),new String(password));	
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
