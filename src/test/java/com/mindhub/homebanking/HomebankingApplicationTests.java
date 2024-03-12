package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.NumberRamdom;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class HomebankingApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void testGenerateNumberCard() {
		String cardNumber = NumberRamdom.generateCardNumber();
		assertThat(cardNumber.length(), equalTo(19)); //19 caracteres porque los "-" cuentan como un caracter mas
	}

}
