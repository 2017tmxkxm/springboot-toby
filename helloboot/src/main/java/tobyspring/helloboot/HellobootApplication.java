package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

public class HellobootApplication {

	public static void main(String[] args) {
		ServletWebServerFactory serverFactory =  new TomcatServletWebServerFactory();
		// getWebServer : servlet 컨테이너를 만드는 생성 함수
		WebServer webServer = serverFactory.getWebServer();
		// Tomcat servler 컨테이너 동작
		webServer.start();
	}

}
