package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication {

	public static void main(String[] args) {
		// 스프링 컨테이너 생성, DispatcherServlet은 GenericWebApplicationContext를 사용
		// 서블릿 컨테이너 초기화 작업을 스프링 컨테이너 초기화 작업에 종속
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();
				
				// 서블릿 등록
				ServletWebServerFactory serverFactory =  new TomcatServletWebServerFactory();
				// getWebServer : servlet 컨테이너를 만드는 생성 함수
				// ServletContextInitializer : selvlet 컨테이너에 servlet 연결 
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/*");
				});
				
				// Tomcat servlet 컨테이너 동작
				webServer.start();
			}
		};
		
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();
		
	}

}
