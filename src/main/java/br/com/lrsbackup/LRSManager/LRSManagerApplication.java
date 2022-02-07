package br.com.lrsbackup.LRSManager;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.opentracing.Tracer;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.metrics.NoopMetricsFactory;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.reporters.RemoteReporter.Builder;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;
import io.jaegertracing.thrift.internal.senders.UdpSender;
import io.opentracing.util.GlobalTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;


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
		/*
		@Configuration
		public class JaegerFilterConfiguration {

		    @Bean
		    public TracingFilter tracingFilter(Tracer tracer) {
		        return new TracingFilter(tracer);
		    }
		}
		*/
		
		
		
		
	}
	
}
