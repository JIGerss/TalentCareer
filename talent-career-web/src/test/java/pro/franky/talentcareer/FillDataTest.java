package pro.franky.talentcareer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.franky.talentcareer.mapper.ResumeMapper;
import pro.franky.talentcareer.pojo.ResumeData;
import pro.franky.talentcareer.pojo.domain.Resume;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author Steveny
 * @since 2023/7/15
 */
@SpringBootTest
public class FillDataTest {
    @Autowired
    private ResumeMapper resumeMapper;

    @Test
    void addData() {
        JSON json = JSONUtil.readJSON(new File("C:\\Users\\Crazbeans\\IdeaProjects\\TalentCareer\\talent-career-web\\src\\main\\resources\\final_result.json"), StandardCharsets.UTF_8);
        for (int i = 101; i <= 300; i++) {
            ResumeData data = BeanUtil.toBean(json.getByPath(i + ""), ResumeData.class);
            if(data != null) {
                Resume resume = Resume.builder()
                        .username("姬念")
                        .filename(i + ".pdf")
                        .name(data.getName())
                        .college(data.getSchool())
                        .maxCareer(data.getEducation())
                        .birthday(data.getAge())
                        .workDate(data.getWork_time())
                        .build();
                resumeMapper.insert(resume);
            }
        }
    }
}
