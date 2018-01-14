package cn.et;

import org.I0Itec.zkclient.ZkClient;

public class ZkAAA {

	public static void main(String[] args) {
		//zookeeper的连接地址 
		String zkUrl = "localhost:2181";
		
		//zookeeper连接服务的实例
		ZkClient zk = new ZkClient(zkUrl, 10000,5000);
		//修改数据
		zk.writeData("/db", "oracle");
		
	}

}
