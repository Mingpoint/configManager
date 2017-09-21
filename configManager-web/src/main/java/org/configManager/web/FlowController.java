package org.configManager.web;


import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.configManager.domain.Configuration;
import org.configManager.exception.MyException;
import org.configManager.service.ConfigurationService;
import org.configManager.web.response.JsonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/conf/flow")
public class FlowController {
	private static Logger logger = LoggerFactory.getLogger(FlowController.class);
	@Autowired
	private ConfigurationService configurationService;

	@RequestMapping("/insertConfiguration.do")
	@ResponseBody
	public JsonMessage insertConfiguration(Configuration configuration) {
		JsonMessage jsonMessage = new JsonMessage();
		try {
			String path = configuration.getDataId();
			if(StringUtils.isEmpty(path)){
				logger.error("error: 路径为空");
				jsonMessage.setCode(500);
				jsonMessage.setMessage("path路径为空");
				return jsonMessage;
			}
			boolean result = configurationService.insertConfiguration(configuration);
			if (!result) {
				jsonMessage.setCode(500);
				jsonMessage.setMessage("节点文件为：" + configuration.getGroupId() + "-" + configuration.getDataId() + "已经存在!");
			}
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}

	@RequestMapping("/updateConfiguration.do")
	@ResponseBody
	public JsonMessage updateConfiguration(Configuration configuration) {
		JsonMessage jsonMessage = new JsonMessage();
		try {
			boolean result = configurationService.updateConfiguration(configuration);
			if (!result) {
				jsonMessage.setCode(500);
				jsonMessage.setMessage("节点文件为：" + configuration.getDataId() + "不存在,修改失败!");
			}
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}

	@RequestMapping("/deleteConfiguration.do")
	@ResponseBody
	public JsonMessage deleteConfiguration(Configuration configuration) {
		JsonMessage jsonMessage = new JsonMessage();
		try {
			boolean result = configurationService.deleteConfiguration(configuration.getDataId());
			if (!result) {
				jsonMessage.setCode(500);
				jsonMessage.setMessage("节点文件为："  + configuration.getDataId() + "不存在,删除失败!");
			}
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}

	@RequestMapping("/queryConfiguration.do")
	@ResponseBody
	public JsonMessage queryConfiguration(Configuration configuration) {
		JsonMessage jsonMessage = new JsonMessage();
		try {
			Configuration configurationResult = configurationService.queryConfiguration(configuration);
			jsonMessage.setResult(configurationResult);
		}catch (MyException myEx){
			logger.error("error: ", myEx.getMessage());
			jsonMessage.setMessage(myEx.getMessage());
			jsonMessage.setCode(400);
		} catch (ZkNoNodeException zkEx) {
			logger.error("error: ", zkEx);
			jsonMessage.setMessage("获取节点配置异常：节点文件为："  + configuration.getDataId()+"不存在!");
			jsonMessage.setCode(500);
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}
}
