package cn.et;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class ChangeConnection2 {

	public static void main(String[] args) {
		//zookeeper的连接地址
		String zkUrl = "localhost:2181";
		//创建连接服务的实例
		ZkClient zk = new ZkClient(zkUrl, 100000, 5000,new BytesPushThroughSerializer());
		//改变数据连接
		zk.writeData("/db/url", "jdbc:oracle:thin:@localhost:1521:orcl".getBytes());
		zk.writeData("/db/driverClass", "oracle.jdbc.OracleDriver".getBytes());
		zk.writeData("/db/username", "stu".getBytes());
		zk.writeData("/db/password", "123456".getBytes());
	}

}
