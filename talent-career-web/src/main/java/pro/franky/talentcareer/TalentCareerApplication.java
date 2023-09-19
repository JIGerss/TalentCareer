package pro.franky.talentcareer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot主程序
 *
 * @author zaki
 */
@SpringBootApplication(scanBasePackages = {"pro.franky"})
public class TalentCareerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentCareerApplication.class, args);
    }

}
