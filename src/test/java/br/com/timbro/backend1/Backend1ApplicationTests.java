package br.com.timbro.backend1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "app.security.jwt.secret=chave-secreta-de-teste-timbro-com-mais-de-32-caracteres",
    "app.security.jwt.expiration=86400000"
})
class Backend1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
