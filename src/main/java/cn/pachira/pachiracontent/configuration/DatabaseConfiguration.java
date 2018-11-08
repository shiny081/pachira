package cn.pachira.pachiracontent.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 数据库的配置
 *
 * @author Han Qizhe
 * @version 2018-04-26 14:56
 **/

@Configuration
@PropertySource(value = {"classpath:db.properties",
        "file:/home/tomcat/nlpconfig/PachiraContent/db.properties"},
        ignoreResourceNotFound = true)
public class DatabaseConfiguration {

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private String port;

    @Value("${db.database}")
    private String database;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    /**
     * 注入Druid数据源
     *
     * @return 数据源
     */
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        druidDataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database
                + "?characterEncoding=UTF-8");
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }
}