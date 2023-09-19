package pro.franky.talentcareer;

import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Steveny
 * @since 2023/6/30
 */
public class TestWord {
    @Test
    void testWord() {
        try (InputStream is = new FileInputStream("C:\\Users\\Crazbeans\\IdeaProjects\\TalentCareer\\talent-career-web\\src\\main\\resources\\64.docx")){
            XWPFDocument document = new XWPFDocument(is);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                System.out.println(paragraph.getText());
            }
            System.out.println(wordExtractor.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDoc() {
        try (InputStream is = new FileInputStream("C:\\Users\\Crazbeans\\IdeaProjects\\TalentCareer\\talent-career-web\\src\\main\\resources\\65.doc")){
            WordExtractor wordExtractor = new WordExtractor(is);
            System.out.println(wordExtractor.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAll() {
        try (InputStream is = new FileInputStream("C:\\Users\\Crazbeans\\IdeaProjects\\TalentCareer\\talent-career-web\\src\\main\\resources\\64.docx")){
            POITextExtractor extractor = ExtractorFactory.createExtractor(is);
            System.out.println(extractor.getText());
        } catch (IOException | OpenXML4JException | XmlException e) {
            throw new RuntimeException(e);
        }
    }
}
