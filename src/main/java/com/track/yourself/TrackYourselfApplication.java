package com.track.yourself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan("com.track.yourself.models")
public class TrackYourselfApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackYourselfApplication.class, args);
	}

}
