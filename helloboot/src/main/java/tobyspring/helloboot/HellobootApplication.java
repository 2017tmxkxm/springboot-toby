package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication {

	public static void main(String[] args) {
		// 스프링 컨테이너 생성, DispatcherServlet은 GenericWebApplicationContext를 사용
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();
		
		// 서블릿 등록
		ServletWebServerFactory serverFactory =  new TomcatServletWebServerFactory();
		// getWebServer : servlet 컨테이너를 만드는 생성 함수
		// ServletContextInitializer : selvlet 컨테이너에 servlet 연결 
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext)).addMapping("/*");
		});
		
		// Tomcat servlet 컨테이너 동작
		webServer.start();
	}

}
