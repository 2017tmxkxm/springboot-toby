package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class HellobootApplication {
	
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
	
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
	
	public static void main(String[] args) {
		// AnnotationConfigWebApplicationContext : @Bean을 인식하기 위해 변경
		// 스프링 컨테이너 생성, DispatcherServlet은 GenericWebApplicationContext를 사용
		// 서블릿 컨테이너 초기화 작업을 스프링 컨테이너 초기화 작업에 종속
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();
				
				ServletWebServerFactory serverFactory =  this.getBean(ServletWebServerFactory.class);
				DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
				
				// DispatcherServlet 등록
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/*");
				});
				
				// Tomcat servlet 컨테이너 동작
				webServer.start();
			}
		};
		
		applicationContext.register(HellobootApplication.class);
		applicationContext.refresh();
		
	}

}
