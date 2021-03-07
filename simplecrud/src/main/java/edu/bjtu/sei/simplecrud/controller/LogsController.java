package edu.bjtu.sei.simplecrud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class LogsController {
	
	@RequestMapping(method = RequestMethod.GET, path = "/logs", produces = "application/json")
	public List<String> allLogs(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
		List<String> logs = (List<String>) request.getSession().getAttribute("LOGS_SESSION");
		
		return logs;
	}

}
