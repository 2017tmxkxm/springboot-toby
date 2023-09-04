package tobyspring.helloboot;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class HellobootApplication {

	public static void main(String[] args) {
		// 스프링 컨테이너 생성
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.registerBean(HelloController.class);
		applicationContext.refresh();
		
		ServletWebServerFactory serverFactory =  new TomcatServletWebServerFactory();
		// getWebServer : servlet 컨테이너를 만드는 생성 함수
		// ServletContextInitializer : selvlet 컨테이너에 servlet 연결 
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					
					if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						String name = req.getParameter("name");
						
						HelloController helloController = applicationContext.getBean(HelloController.class);
						String ret = helloController.hello(name);
						
						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret);
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
					
				} 
			}).addMapping("/*");
		});
		
		// Tomcat servlet 컨테이너 동작
		webServer.start();
	}

}
