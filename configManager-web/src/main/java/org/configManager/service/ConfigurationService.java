package org.configManager.service;

import java.util.List;

import org.configManager.domain.Configuration;
import org.configManager.domain.TreeList;
import org.configManager.exception.MyException;





/**
 * @author Mingpoint
 * @date 2017年9月1日 下午4:02:11 
 *
 */
public interface ConfigurationService {
	public boolean insertConfiguration(Configuration configuration);

	public boolean updateConfiguration(Configuration configuration);

	public boolean deleteConfiguration(Configuration configuration);
	public boolean deleteConfiguration(String path);

	public Configuration queryConfiguration(Configuration configuration) throws MyException;
	public Configuration queryConfiguration(String path) ;
	/**
	 * @author Mingpoint
	 * @throws
	 * 返回所有的节点
	 */
	public List<TreeList> queryConfigurationAll();
}
