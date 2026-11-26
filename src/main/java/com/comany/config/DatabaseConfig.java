
package com.comany.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);

        factory.setPackagesToScan("com.comany.entity");

        factory.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter()
        );

        Properties properties = new Properties();

        properties.put(
                "hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect"
        );

        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            jakarta.persistence.EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
