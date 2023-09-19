package pro.franky.talentcareer.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ecloud.sdk.commtextverify.v1.Client;
import com.ecloud.sdk.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 职选家SpringBoot配置
 *
 * @author Steveny
 */
@Configuration
public class CareerConfig {
    @Value("${career.user.ak}")
    String user_ak;
    @Value("${career.user.sk}")
    String user_sk;
    @Value("${career.pool_id}")
    String poolId;

    /**
     * 移动云OCR请求客户端
     */
    @Bean
    public Client getClient() {
        Config config = new Config();
        config.setAccessKey(user_ak);
        config.setSecretKey(user_sk);
        config.setPoolId(poolId);
        return new Client(config);
    }

    /**
     * MybatisPlus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mpInterceptor(){
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mpInterceptor;
    }

    /**
     * Websocket末端
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
