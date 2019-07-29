package com.cmb.bankcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EntityScan("com.cmb.bankcheck.entity")
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class BankCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCheckApplication.class, args);
    }

}
