package org.configManager.service;

import java.util.ArrayList;
import java.util.List;

import org.configManager.core.ZkClientConnect;
import org.configManager.domain.Configuration;
import org.configManager.domain.TreeList;
import org.configManager.exception.MyException;
import org.configManager.properties.Properties;
import org.configManager.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;




/**
 * 配置管理service实现类
 * 
 * @author Mingpoint
 * @date 2017年9月4日 上午11:13:25 
 *
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	private ZkClientConnect zkClientConnect = new ZkClientConnect(Properties.getString("zk.address"));
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

	public boolean insertConfiguration(Configuration configuration) {
		String path = configuration.getDataId();
		path = path.substring(path.indexOf(Constant.SEPARATOR)+1);
		String[] split = path.split(Constant.SEPARATOR);
		String confPath = "";
		boolean flag = true;
		for (int i = 0; i < split.length; i++) {
			confPath = confPath + Constant.SEPARATOR+ split[i];
			if(i == split.length-1){
				flag = zkClientConnect.createNote(confPath, configuration.getConfiguration());
			}else{
				flag = zkClientConnect.createNote(confPath, null);
			}
		}
		return flag;
	}

	public boolean updateConfiguration(Configuration configuration) {
		return zkClientConnect.updateNote(configuration.getDataId(), configuration.getConfiguration());
	}	
	public boolean deleteConfiguration(String path) {
		boolean boo = true;
		List<String> list = new ArrayList<String>();
		findPathList(path, list);
		list.add(path);
		for(String str : list) {
			boo = zkClientConnect.deleteNote(str);
			logger.info("delete path : "+str);
		}
		return boo;
	}
	private void findPathList(String path,List<String> list){
		List<String> children = zkClientConnect.getChildren(path);
		if(!CollectionUtils.isEmpty(children)){
			for(String str : children){
				String confPath = path + Constant.SEPARATOR + str;
				findPathList(confPath, list);
				list.add(confPath);
			}
		}
		
	}
	public Configuration queryConfiguration(Configuration configuration) throws MyException{
		List<String> children = zkClientConnect.getChildren(configuration.getDataId());
		
		if(!CollectionUtils.isEmpty(children)){
			throw new MyException(configuration.getDataId()+" 路径下还有子节点");
		}
		String configurationStr = (String) zkClientConnect.readData(configuration.getDataId());
		configuration.setConfiguration(configurationStr);
		return configuration;
	}
	public Configuration queryConfiguration(String path) {
		String configurationStr = (String) zkClientConnect.readData(path);
		Configuration configuration = new Configuration();
		configuration.setConfiguration(configurationStr);
		return configuration;
	}
	@Override
	public List<TreeList> queryConfigurationAll() {
		List<TreeList> list = new ArrayList<TreeList>();
		String path = Constant.ROOT_PATH;
		TreeList tree = new TreeList();
		String sub = path.substring(path.indexOf(Constant.SEPARATOR)+1);
		tree.setpStr(sub);
		tree.setPid(null);
		tree.setId(1);
		tree.setStr(sub);
		list.add(tree);
		handleConfig(path, list, tree);
		return list;
	}
	int test = 2;
	private List<TreeList> handleConfig(String path,List<TreeList> list,TreeList parent) {
		List<String> children = zkClientConnect.getChildren(path);
		if (!CollectionUtils.isEmpty(children)) {
			for (int i = 0; i < children.size(); i++) {
				TreeList tree = new TreeList();
				tree.setId(++test);
				tree.setPid(parent.getId());
				tree.setStr(children.get(i));
				tree.setpStr(parent.getStr());
				list.add(tree);
				handleConfig(path +Constant.SEPARATOR+ children.get(i), list, tree);
			}
		}
		return list;
	}

	@Override
	public boolean deleteConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		return false;
	}
}

