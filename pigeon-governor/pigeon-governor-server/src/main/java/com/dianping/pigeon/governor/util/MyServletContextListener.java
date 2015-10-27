package com.dianping.pigeon.governor.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dianping.lion.client.Lion;
import com.dianping.pigeon.governor.lion.registry.HostDbProcess;
import com.dianping.pigeon.util.NetUtils;

public class MyServletContextListener implements ServletContextListener {

	private Logger logger = LogManager.getLogger();

	private ApplicationContext applicationContext;
	private HostDbProcess hostDbProcess;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("----------- 终止环境 ------------");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("----------- 启动环境 ------------");
		
		//临时
		initWeight(sce.getServletContext());

	}

	/**
	 * @author chenchongze
	 * @param servletContext 
	 */
	private void initWeight(ServletContext servletContext) {
		boolean enable = false;
		String server = Lion.get("pigeon-governor-server.zk.listener");
		
		if(StringUtils.isBlank(server)) {
			logger.warn("服务ip为空");
			return;
		}
		
		if (NetUtils.getFirstLocalIp().equals(server)) {
			enable = true;
		}
		
		if(enable){
			applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			hostDbProcess = (HostDbProcess) BeanFactoryUtils.beanOfType(applicationContext, HostDbProcess.class);
			hostDbProcess.init();
		}
		
	}

}