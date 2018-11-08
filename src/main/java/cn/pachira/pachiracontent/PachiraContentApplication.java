package cn.pachira.pachiracontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 起始点
 *
 * @author Han Qizhe
 * @version 2018-03-31 14:52
 **/

@SpringBootApplication
public class PachiraContentApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(PachiraContentApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder.sources(PachiraContentApplication.class));
    }
}