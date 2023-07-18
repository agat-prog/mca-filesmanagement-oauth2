package mca.filesmanagement.oauth2.it;

import javax.net.ssl.SSLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DirtiesContext
//@Testcontainers
public abstract class AbstractControllerIT extends TestContainersBase {

	protected WebTestClient webClient;

	@LocalServerPort
	protected int port;

	@BeforeEach
	public void setup() throws SSLException {
		//WebClient webClient = WebClient.create("https://api.github.com");
		
//	    HttpClient httpClient = HttpClient.create()
//	            .secure(sslSpec -> sslSpec.sslContext(sslContext))
//	            .baseUrl("https://localhost:" + this.port);

//		ClientHttpConnector connector = new ReactorClientHttpConnector(
//				httpClient);
		this.webClient = WebTestClient
				  .bindToServer()
				  .baseUrl("http://localhost:" + this.port)
				  .build();
	}
}
