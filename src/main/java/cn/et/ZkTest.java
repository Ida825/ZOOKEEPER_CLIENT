package cn.et;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkTest {

	public static void main(String[] args) throws InterruptedException {
		//zookeeper�����ӵ�ַ 
		String zkUrl = "localhost:2181";
		
		/**
		 * ����zookeeper ����5�����ӳ�ʱ   new ZkClient(zkServers, sessionTimeout, connectionTimeout)
		 * zkServers zk������ 
		 * sessionTimeout �رպ�session�ĳ�ʱʱ��
		 * connectionTimeout ���ӳ�ʱʱ��
		 */
		//zookeeper���ӷ����ʵ��
		ZkClient zk = new ZkClient(zkUrl, 10000,5000);
		//����ڵ�/user������  �ʹ���һ�����ýڵ� /user
		if(!zk.exists("/user")){
			zk.createPersistent("/user");

			//��������˳��ڵ� /user/ls ����˳��ڵ�
			String nodeName1 = zk.create("/user/ls", "boy",CreateMode.PERSISTENT_SEQUENTIAL);
			String nodeName2 = zk.create("/user/ls", "boy",CreateMode.PERSISTENT_SEQUENTIAL);
			
			System.out.println(nodeName1+"--"+nodeName2);
			//����һ����ʱ�ڵ� /zs
			zk.createEphemeral("/user/zs","girl");		
		}
		
		//���db���ݸı� (��������)
		zk.subscribeDataChanges("/db", new IZkDataListener() {
			
			//���ݱ�ɾ��ʱ����
			public void handleDataDeleted(String path) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			//���ݱ��ı�ʱ����
			public void handleDataChange(String path, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				System.out.println(path);
			}
		});
		
		//ȷ������ļ��һֱ��Ч
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
