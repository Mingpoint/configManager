package org.configManager.core;

import java.util.List;

import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mingpoint
 * @date 2017年8月31日 下午6:06:13 
 *
 */
public class ZkClientConnect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientConnect.class);
	private ZkClient zkClient;

	public ZkClientConnect() {

	}

	public ZkClientConnect(String address) {
		this(address, Integer.MAX_VALUE);
	}

	public ZkClientConnect(String address, int timeOut) {
		this.zkClient = new ZkClient(address, timeOut);
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

	/**
	 * 获取节点数据
	 * 
	 * @param path
	 * @return
	 */
	public Object readData(String path) {
		return zkClient.readData(path);
	}

	/**
	 * 获取节点下的文件列表
	 * 
	 * @param path
	 * @return
	 */
	public List<String> getChildren(String path) {
		return zkClient.getChildren(path);
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 * @param data
	 */
	public boolean createNote(String path, Object data) {
		if (!zkClient.exists(path)) {
			zkClient.createPersistent(path, data);
			return true;
		} else {
			LOGGER.info("path = " + path + " exists!");
			return false;
		}
	}
	
	public boolean exists(String path) {
		return zkClient.exists(path);
	}

	/**
	 * 修改节点数据
	 * 
	 * @param path
	 * @param data
	 */
	public boolean updateNote(String path, final Object data) {
		if (zkClient.exists(path)) {
			zkClient.updateDataSerialized(path, new DataUpdater<Object>() {
				public Object update(Object currentData) {
					currentData = data;
					return currentData;
				}
			});
			return true;
		} else {
			LOGGER.info("update path = " + path + " exists!");
			return false;
		}
	}

	/**
	 * 删除节点数据
	 * 
	 * @param path
	 */
	public boolean deleteNote(String path) {
		if (zkClient.exists(path)) {
			zkClient.delete(path);
			return true;
		} else {
			LOGGER.info("delete path = " + path + " exists!");
			return false;
		}
	}

	/**
	 * 订阅children变化
	 * 
	 * @param zkClient
	 * @param path
	 */
	public void childChangesListener(ZkClient zkClient, final String path) {
		zkClient.subscribeChildChanges(path, new IZkChildListener() {

			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				LOGGER.info("clildren of path " + parentPath + ":" + currentChilds);
			}

		});
	}

	/**
	 * 订阅节点数据变化
	 * 
	 * @param zkClient
	 * @param path
	 */
	public void dataChangesListener(ZkClient zkClient, final String path) {
		zkClient.subscribeDataChanges(path, new IZkDataListener() {
			public void handleDataChange(String dataPath, Object data) throws Exception {
				LOGGER.info("Data of " + dataPath + " has changed.");
			}

			public void handleDataDeleted(String dataPath) throws Exception {
				LOGGER.info("Data of " + dataPath + " has changed.");
			}
		});
	}

	/**
	 * 订阅连接状态的变化
	 * 
	 * @param zkClient
	 */
	public void stateChangesListener(ZkClient zkClient) {
		zkClient.subscribeStateChanges(new IZkStateListener() {
			public void handleStateChanged(KeeperState state) throws Exception {
				LOGGER.info("handleStateChanged");
			}

			@SuppressWarnings("unused")
			public void handleSessionEstablishmentError(Throwable error) throws Exception {
				LOGGER.info("handleSessionEstablishmentError");
			}

			public void handleNewSession() throws Exception {
				LOGGER.info("handleNewSession");
			}
		});
	}
}
