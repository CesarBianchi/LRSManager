package br.com.lrsbackup.LRSManager;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.lrsbackup.LRSManager.util.LRSDatabaseCredentials;


@SpringBootApplication
public class LRSManagerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LRSManagerApplication.class, args);
	}

	@Configuration
	public class DatasourceConfig {
	    		
		LRSDatabaseCredentials dbCredentials = new LRSDatabaseCredentials();
		
		@Bean
	    public DataSource datasource() {
	        return DataSourceBuilder.create()
	          .driverClassName("com.mysql.cj.jdbc.Driver")
	          .url("jdbc:mysql://".concat(dbCredentials.getIp()).concat(":").concat(dbCredentials.getPort()).concat("/").concat(dbCredentials.getDbName()))
	          .username(dbCredentials.getUsername())
	          .password(dbCredentials.getPassword())
	          .build();	
	    }
	}
	
}
