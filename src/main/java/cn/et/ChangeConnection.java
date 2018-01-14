package cn.et;

import org.I0Itec.zkclient.ZkClient;

public class ChangeConnection {

	public static void main(String[] args) {
		//zookeeper的连接地址
		String zkUrl = "localhost:2181";
		//创建连接服务的实例
		ZkClient zk = new ZkClient(zkUrl, 100000, 5000);
		//改变数据连接
		zk.writeData("/db/url", "jdbc:oracle:thin:@localhost:1521:orcl");
		zk.writeData("/db/driverClass", "oracle.jdbc.OracleDriver");
		zk.writeData("/db/username", "stu");
		zk.writeData("/db/password", "123456");
	}

}
