package com.grj.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.grj.kafka.MemoryCPUComsumer;

public class SimpleAjaxPolling extends HttpServlet {
	
	public static Logger logger = LoggerFactory.getLogger(SimpleAjaxPolling.class);

	private static final long serialVersionUID = 2L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemoryCPUComsumer memoryCPUComsumer = MemoryCPUComsumer.getInstance();
		
		PrintWriter out = resp.getWriter();
		String msg = null;
		if((msg = memoryCPUComsumer.subscribeTopic()) != null){
			logger.info("................................"+msg);
			out.write(msg);
			out.close();
		}
	}

}
