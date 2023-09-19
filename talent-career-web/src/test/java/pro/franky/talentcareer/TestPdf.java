package pro.franky.talentcareer;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.franky.talentcareer.pojo.Cv;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.service.FileService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Steveny
 * @since 2023/6/28
 */
@Slf4j
@SpringBootTest
public class TestPdf {
    @Autowired
    FileService fileService;

    @Test
    void getPdfContent() {
        File input = new File("./src/main/resources/284.pdf");
        File output = new File("./src/main/resources/output.json");
        try (FileOutputStream os = new FileOutputStream(output)) {
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);

            if (input.isFile()) {
                log.info("文件 --> " + input.getName());
                Cv cv = new Cv();
                ResponseResult<String> result = fileService.readPdfText(FileUtil.readBytes(input));
                cv.setContent(result.getData());
                cv.setFilename(input.getName());
                writer.write(JSONUtil.toJsonPrettyStr(cv));
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (PDDocument pdfFile = PDDocument.load(new File("C:\\Users\\Crazbeans\\IdeaProjects\\TalentCareer\\talent-career-web\\src\\main\\resources\\284.pdf"))) {
            PDPageTree pageTree = pdfFile.getPages();
            int count = pageTree.getCount();
            PDFTextStripper stripper = new PDFTextStripper() {
                @Override
                protected void startPage(PDPage page) throws IOException {
                    startOfLine = true;
                    super.startPage(page);
                }

                @Override
                protected void writeLineSeparator() throws IOException {
                    startOfLine = true;
                    super.writeLineSeparator();
                }

                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    if (startOfLine) {
//                        TextPosition firstPosition = textPositions.get(0);
//                        writeString(String.format("[%s,%s]", firstPosition.getXDirAdj(),
//                                firstPosition.getYDirAdj()));
                        startOfLine = false;
                    }
                    super.writeString(text, textPositions);
                }

                boolean startOfLine = true;
            };
            stripper.setSortByPosition(false);
            stripper.setStartPage(1);
            stripper.setEndPage(count);
            String content = stripper.getText(pdfFile);
            content = content.replaceAll(" +", " ")
                    .replaceAll("\r\n", " ")
                    .replaceAll("\n+", " ")
                    .replaceAll("\t+", " ")
                    .replaceAll(" +", " ")
                    .trim();
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
