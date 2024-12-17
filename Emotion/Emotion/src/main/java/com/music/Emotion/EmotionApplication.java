package com.music.Emotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.music.Emotion")
public class EmotionApplication {	
		public static void main(String[] args) {
			SpringApplication.run(EmotionApplication.class, args);
		}
}	
