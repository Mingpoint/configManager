package org.configManager.web.system;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.configManager.properties.Properties;
import org.configManager.utils.Md5Utils;
import org.configManager.web.response.JsonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author Mingpoint
 * @date 2017年9月19日 上午11:00:47 
 *
 */
@Controller
@RequestMapping("/system")
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static String login_user ;
	private static String login_password ;
	
	/**
	 * 初始化配置文件用户、密码
	 */
	public static void initConfig(){
		try {
			String path = LoginController.class.getResource("/login.properties").getPath();
			Properties.initProperties(path);
			login_user =  Properties.getByKey("login.user");
			login_password = Properties.getByKey("login.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 登录
	 * @param request
	 * @param loginUser
	 * @param loginPassword
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonMessage login(HttpServletRequest request,String loginUser,String loginPassword){
		JsonMessage jsonMessage = new JsonMessage();
		try {
			if(loginUser==null||"".equals(loginUser)||loginPassword==null||"".equals(loginPassword)) {
				jsonMessage.setMessage("用户名或密码不能为空");
				jsonMessage.setCode(500);
				return jsonMessage;
			}
			loginUser = Md5Utils.getMd5Str(loginUser);
			loginPassword = Md5Utils.getMd5Str(loginPassword);
			LoginController.initConfig();//初始化配置用户和密码
			if(login_user.equals(loginUser)&&login_password.equals(loginPassword)){
				request.getSession().setAttribute("loginUser", "loginUser");
			}else{
				jsonMessage.setMessage("用户名或密码错误");
				jsonMessage.setCode(500);
			}
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}
	
	/**
	 * 注销
	 * @param request
	 * @param loginUser
	 * @param loginPassword
	 * @return
	 */
	@RequestMapping(value = "/loginOut.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonMessage loginOut(HttpServletRequest request){
		JsonMessage jsonMessage = new JsonMessage();
		try {
			request.getSession().setAttribute("loginUser", null);
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
			jsonMessage.setMessage("服务器内部异常：请联系管理员！");
			jsonMessage.setCode(500);
		}
		return jsonMessage;
	}
	
	public static void main(String[] args) {
//		String user = Md5Utils.getMd5Str("admin");
//		String password = Md5Utils.getMd5Str("admin123");
	}
}
