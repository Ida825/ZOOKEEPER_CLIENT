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

		//zookeeper的连接地址
		String zkUrl = "localhost:2181";
		//创建连接服务的实例
		zk = new ZkClient(zkUrl, 100000, 5000);
		
		if(!zk.exists("/db")){
			//创建一个永久节点/db
			zk.createPersistent("/db","mysql");
			
			//创建连接数据据的四个要素的节点
			zk.createPersistent("/db/url", "jdbc:mysql://localhost:3306/test");
			zk.createPersistent("/db/driverClass", "com.mysql.jdbc.Driver");
			zk.createPersistent("/db/username", "root");
			zk.createPersistent("/db/password", "123456");
		}
		
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
