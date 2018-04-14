package com.lk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒 
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
