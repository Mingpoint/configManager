package org.configManager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.configManager.domain.Configuration;
import org.configManager.domain.TreeList;
import org.configManager.service.ConfigurationService;
import org.configManager.web.response.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/conf")
public class IndexController {
	@Autowired
	private ConfigurationService configurationService;
	@RequestMapping("/index.do")
	public String index(Model model) {
		return "index";
	}
	@RequestMapping("/index/queryData.do")
	public @ResponseBody Object queryData(Model model) {
		List<TreeList> list = configurationService.queryConfigurationAll();
		JsonMessage json = new JsonMessage();
		json.setResult(list);
		return json;
	}

	@RequestMapping("/index/query.do")
	public String query(HttpServletRequest request,Model model) {
		String path = request.getParameter("queryParam") == null ? "" :  request.getParameter("queryParam").trim();
		Configuration queryConfiguration = configurationService.queryConfiguration(path);
		model.addAttribute("config", queryConfiguration.getConfiguration());
		model.addAttribute("path", path);
		return "query";
	}
}
