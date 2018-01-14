package cn.et;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ConnectionTest {
	static Connection conn;
	static ZkClient zk;
	public static void main(String[] args) throws InterruptedException {

		//zookeeper�����ӵ�ַ
		String zkUrl = "localhost:2181";
		//�������ӷ����ʵ��
		zk = new ZkClient(zkUrl, 100000, 5000);
		
		if(!zk.exists("/db")){
			//����һ�����ýڵ�/db
			zk.createPersistent("/db","mysql");
			
			//�����������ݾݵ��ĸ�Ҫ�صĽڵ�
			zk.createPersistent("/db/url", "jdbc:mysql://localhost:3306/test");
			zk.createPersistent("/db/driverClass", "com.mysql.jdbc.Driver");
			zk.createPersistent("/db/username", "root");
			zk.createPersistent("/db/password", "123456");
		}
		
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
		
		String url = zk.readData("/db/url");
		String driverClass = zk.readData("/db/driverClass");
		String username = zk.readData("/db/username");
		String password = zk.readData("/db/password");
		
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url,username,password);		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
