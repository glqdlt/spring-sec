package com.glqdlt.ex.springsecurity;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {
	
	@Autowired
	@Qualifier("myDataSource")
	private DataSource dataSource;

	@Test
	public void contextLoads() {
	}

	@Test
	public void isSourceH2() throws Exception {
		final String _DRIVER_NAME = "org.h2.Driver";
		HikariDataSource sss = (HikariDataSource) this.dataSource;
		String className = sss.getDriverClassName();
		Assert.assertEquals(_DRIVER_NAME,className);
	}
}

