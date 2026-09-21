package com.reserva_hotel.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.reserva_hotel.huespedes", "com.dan.commons"})
public class HuespedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedesApplication.class, args);
	}

}
