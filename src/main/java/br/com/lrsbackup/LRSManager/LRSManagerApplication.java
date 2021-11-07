package br.com.lrsbackup.LRSManager;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class LRSManagerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LRSManagerApplication.class, args);
	}

	@Configuration
	public class DatasourceConfig {
	    @Bean
	    public DataSource datasource() {
	        return DataSourceBuilder.create()
	          .driverClassName("com.mysql.cj.jdbc.Driver")
	          .url("jdbc:mysql://192.168.0.101:3306/LRSBackup")
	          .username("cesar_bianchi")
	          .password("notSafe@IKnow2butILazyToday")
	          .build();	
	    }
	}
	
}
