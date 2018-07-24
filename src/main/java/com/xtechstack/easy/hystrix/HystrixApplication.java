package com.xtechstack.easy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@RestController
public class HystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}

	@Component
	public class HelloIntegration {
		@HystrixCommand(fallbackMethod = "fallbackHello")
		public String hello(String name) throws Exception {
			if(name.equals("xtechstack")){
				throw new Exception("xtechstack");
			}
			return "Hello, "+name+"!";
		}

		public String fallbackHello(String name) {
			return "blog.xtechstack.com";
		}
	}
	@Autowired
	private HelloIntegration hi;

	@GetMapping("/hello")
	public String home(@RequestParam("name") String name) throws Exception{
		return hi.hello(name);
	}
}