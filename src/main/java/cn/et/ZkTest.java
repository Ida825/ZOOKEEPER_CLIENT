package cn.et;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkTest {

	public static void main(String[] args) throws InterruptedException {
		//zookeeper的连接地址 
		String zkUrl = "localhost:2181";
		
		/**
		 * 连接zookeeper 设置5秒连接超时   new ZkClient(zkServers, sessionTimeout, connectionTimeout)
		 * zkServers zk服务器 
		 * sessionTimeout 关闭后session的超时时间
		 * connectionTimeout 连接超时时间
		 */
		//zookeeper连接服务的实例
		ZkClient zk = new ZkClient(zkUrl, 10000,5000);
		//如果节点/user不存在  就创建一个永久节点 /user
		if(!zk.exists("/user")){
			zk.createPersistent("/user");

			//创建两个顺序节点 /user/ls 返回顺序节点
			String nodeName1 = zk.create("/user/ls", "boy",CreateMode.PERSISTENT_SEQUENTIAL);
			String nodeName2 = zk.create("/user/ls", "boy",CreateMode.PERSISTENT_SEQUENTIAL);
			
			System.out.println(nodeName1+"--"+nodeName2);
			//创建一个临时节点 /zs
			zk.createEphemeral("/user/zs","girl");		
		}
		
		//监控db数据改变 (匿名函数)
		zk.subscribeDataChanges("/db", new IZkDataListener() {
			
			//数据被删除时调用
			public void handleDataDeleted(String path) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			//数据被改变时调用
			public void handleDataChange(String path, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				System.out.println(path);
			}
		});
		
		//确保上面的监控一直有效
		while(true){
			TimeUnit.SECONDS.sleep(5);
		}
		
		
		/*try {
			
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
	}

}
