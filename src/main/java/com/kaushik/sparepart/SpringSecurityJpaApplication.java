package com.kaushik.sparepart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.kaushik.sparepart.repository.AccountRepository;
import com.kaushik.sparepart.repository.SparepartsRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {AccountRepository.class,SparepartsRepository.class})
public class SpringSecurityJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJpaApplication.class, args);
	}

}
