package pro.franky.talentcareer;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pro.franky.talentcareer.pojo.Cv;
import pro.franky.talentcareer.pojo.Result;
import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.service.FileService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class MockTest {
    @Value("${career.user.ak}")
    String user_ak;
    @Value("${career.user.sk}")
    String user_sk;
    @Value("${career.pool_id}")
    String poolId;
    @Autowired
    FileService fileService;

    @Test
    void getAllPdfContent() throws Exception {
        File input = new File("./src/main/resources/result");
        File output = new File("./src/main/resources/output.json");
        try (FileOutputStream os = new FileOutputStream(output)) {
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            File[] files = input.listFiles();

            if (files != null) {
                Result response = new Result();
                List<Cv> cvList = new ArrayList<>(files.length);
                for (File file : files) {
                    log.info("文件 --> " + file.getName());
                    String type = FileTypeUtil.getType(file);
                    ResponseResult<String> result;
                    if("pdf".equals(type)) {
                        result = fileService.readPdfText(FileUtil.readBytes(file));
                    } else {
                        result = fileService.readDocText(FileUtil.readBytes(file));
                    }
                    Cv cv = new Cv();
                    String data = result.getData().replaceAll(" +", " ")
                            .replaceAll("\r\n", " ")
                            .replaceAll("\n+", " ")
                            .replaceAll("\t+", " ")
                            .replaceAll(" +", " ")
                            .trim();
                    cv.setContent(data);
                    cv.setFilename(file.getName());
                    cvList.add(cv);
                }
                response.setData(cvList);
                writer.write(JSONUtil.toJsonPrettyStr(response));
                writer.flush();
            }
        }
    }

    @Test
    @Rollback(value = false)
    @Transactional()
    void addData(@Autowired MockMvc mockMvc) {
        File file = new File("./src/main/resources/input.json");
        String read = FileUtil.readString(file, StandardCharsets.UTF_8);
        UploadResumeDto uploadResumeDto = JSONUtil.toBean(read, UploadResumeDto.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/resume/upload");
        request.content(read);
        int n = 30;
        for (int i = 0; i < n; i++) {
            try {
                ResultActions action = mockMvc.perform(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
