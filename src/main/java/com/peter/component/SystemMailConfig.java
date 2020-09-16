package com.peter.component;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lcc
 * @date 2020/9/16 下午4:17
 */
@Configuration
@ConfigurationProperties(prefix = "system.mail")
@Data
@Accessors(chain = true)
public class SystemMailConfig {
    private String from;
    private String valicodeSubject;
}
