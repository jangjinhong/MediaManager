package com.prog.mediamanager;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediamanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediamanagerApplication.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate5Module() {
		// 기본으로 초기화된 프록시 객체만 노출, 초기화되지 않은 프록시 객체는 노출 안 함
		Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
		hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING,
				true);
		return hibernate5Module;
	}

}
