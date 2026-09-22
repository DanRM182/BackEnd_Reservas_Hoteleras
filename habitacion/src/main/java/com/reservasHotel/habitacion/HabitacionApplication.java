package com.reservasHotel.habitacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.reservasHotel.habitacion","com.dan.commons"})
public class HabitacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionApplication.class, args);
	}

}
