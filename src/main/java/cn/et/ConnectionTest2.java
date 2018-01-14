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

		//zookeeper的连接地址
		String zkUrl = "localhost:2181";
		//创建连接服务的实例
		zk = new ZkClient(zkUrl, 100000, 5000,new BytesPushThroughSerializer());

		//连接数据库
		getConnection(zk);
		
		//监控db数据改变 (匿名函数)
		zk.subscribeDataChanges("/db/url", new IZkDataListener() {
			
			//数据被删除时调用
			public void handleDataDeleted(String path) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			//数据被改变时调用
			public void handleDataChange(String path, Object arg1) throws Exception {
				getConnection(zk);
			}
		});
		
		//确保上面的监控一直有效
		while(true){
			TimeUnit.SECONDS.sleep(5);
		}
	}

	/**
	 * 连接数据库
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
