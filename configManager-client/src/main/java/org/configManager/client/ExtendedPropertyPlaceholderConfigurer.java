package org.configManager.client;



import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import org.configManager.core.ZkClientConnect;
import org.configManager.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;




/**
 * spring配置扩展
 * @author Mingpoint
 * @date 2017年9月5日 上午9:54:16 
 *
 */
public class ExtendedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer { 
	private static Logger logger = LoggerFactory.getLogger(ExtendedPropertyPlaceholderConfigurer.class);
	private Properties props;
	private  String address;
	private  String node;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		logger.info("================开始加载配置文件================");
		node = props.getProperty("manager.node");
		address = props.getProperty("zk.address");
		if (StringUtils.isEmpty(node) || StringUtils.isEmpty(address)) {
			logger.error("配置文件异常：manager.node,zk.address 不能为空");
			return;
		}
		logger.info("manager配置项：manager.node=" + node);
		logger.info("manager配置项：zk.address=" + address);
		try {
			ZkClientConnect zkClientConnect = new ZkClientConnect(address);
			loadPath(zkClientConnect, props);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		super.processProperties(beanFactory, props);
		this.props = props;
	}

	private void loadChildrenProps(List<String> children, ZkClientConnect zk,String loadPath,Properties props) throws Exception {
		for (String str : children) {
			String path = loadPath + Constant.SEPARATOR + str;
			logger.info("读取节点：" + path);
			List<String> children2 = zk.getChildren(path);
			if (!CollectionUtils.isEmpty(children2)) {
				logger.error("配置中心：" + loadPath + " 配置不正确！");
				throw new IOException("配置中心：" + loadPath + " 配置不正确！");
			}
			String configInfo = zk.readData(path) == null ? "" : zk.readData(
					path).toString();
			loadProps(configInfo, props);
		}
	}
	private void loadPath(ZkClientConnect zk,Properties props) throws Exception{
		logger.info("读取节点：" + node);
		boolean exists = zk.getZkClient().exists(node);
		if(!exists){
			logger.info(node+"在zk服务器上为空，创建"+node+"节点");
			String[] split = node.split(Constant.SEPARATOR);
			String path = "";
			for (int i = 1; i < split.length; i++) {
				path = path+Constant.SEPARATOR+split[i];
				zk.createNote(path, null);
			}
			return;
		}
		List<String> children = zk.getChildren(node);
		loadChildrenProps(children, zk, node,props);
	}
	private void loadProps(String configInfo,Properties props) throws Exception{
		try {
			logger.debug("配置项内容: \n" + configInfo);
			StringReader reader = new StringReader(configInfo);
			props.load(reader);
			PropertyConfigurer.load(props);
		} catch (IOException e) {
			logger.error("配置合并异常" + e.getMessage());
			throw new IOException(e);
		}
	}
	public Object getProperty(String key) {
		return props.get(key);
	}
}