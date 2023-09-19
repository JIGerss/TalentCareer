package pro.franky.talentcareer.service.Impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.io.FileMagicNumber;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import pro.franky.talentcareer.pojo.dto.AnalyseDto;
import pro.franky.talentcareer.pojo.dto.AnalyseResumeDto;
import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.enums.HttpCodeEnum;
import pro.franky.talentcareer.pojo.enums.Process;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.OcrVo;
import pro.franky.talentcareer.pojo.vo.ResumeVo;
import pro.franky.talentcareer.service.FileService;
import pro.franky.talentcareer.service.OcrService;
import pro.franky.talentcareer.service.ProcessService;
import pro.franky.talentcareer.service.ResumeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Steveny
 * @since 2023/6/22
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private final OcrService ocrService;
    private final ResumeService resumeService;
    private final ProcessService processService;

    public FileServiceImpl(OcrService ocrService, ResumeService resumeService, ProcessService processService) {
        this.ocrService = ocrService;
        this.resumeService = resumeService;
        this.processService = processService;
    }

    @Override
    public ResponseResult<ResumeVo> upload(UploadResumeDto uploadResumeDto) {
        // 初始化进度
        processService.initProcess(uploadResumeDto);
        processService.setProcess(uploadResumeDto.getRequestId(), Process.READ);
        byte[] fileBytes = Base64Decoder.decode(uploadResumeDto.getFileBase64());
        FileMagicNumber fileType;
        try {
            String fileHexes = HexUtil.encodeHexStr(fileBytes);
            String type = Optional.ofNullable(FileTypeUtil.getType(fileHexes)).orElseThrow();
            fileType = FileMagicNumber.valueOf(type.toUpperCase());
        } catch (NullPointerException e) {
            return ResponseResult.errorResult(HttpCodeEnum.FILE_READ_ERROR);
        } catch (NoSuchElementException e) {
            fileType = FileMagicNumber.UNKNOWN;
        }

        // 验证文件格式
        processService.setProcess(uploadResumeDto.getRequestId(), Process.CHECK);
        String content = "";
        for (int checkedTime = 0; checkedTime < 1; checkedTime++) {
            log.info("文件上传 --> " + fileType);
            switch (fileType) {
                case PNG, JPEG -> {
                    ResponseResult<OcrVo> response = ocrService.requestOcr(uploadResumeDto.getFileBase64());
                    content = response.getData().getWord();
                }
                case PDF -> {
                    ResponseResult<String> response = readPdfText(fileBytes);
                    content = response.getData();
                }
                case DOC -> {
                    ResponseResult<String> response = readDocText(fileBytes);
                    content = response.getData();
                }
                case DOCX -> {
                    ResponseResult<String> response = readDocxText(fileBytes);
                    content = response.getData();
                }
                case UNKNOWN -> {
                    content = new String(fileBytes, StandardCharsets.UTF_8);
                }
                default -> {
                    try {
                        if (checkedTime == 0) {
                            String[] filename = uploadResumeDto.getFilename().split("\\.", 2);
                            fileType = FileMagicNumber.valueOf(filename[1].toUpperCase());
                            checkedTime--;
                        } else {
                            throw new RuntimeException();
                        }
                    } catch (Exception e) {
                        return ResponseResult.errorResult(HttpCodeEnum.FILE_TYPE_ERROR, "文件格式错误");
                    }
                }
            }
        }
        content = content.replaceAll(" +", " ")
                .replaceAll("\r\n", " ")
                .replaceAll("\n+", " ")
                .replaceAll("\t+", " ")
                .replaceAll(" +", " ")
                .trim();

        // 解析简历
        return resumeService.analyseResume(AnalyseDto.builder()
                .requestId(uploadResumeDto.getRequestId())
                .username(uploadResumeDto.getUsername())
                .analyseResumeDto(AnalyseResumeDto.builder()
                        .content(content)
                        .fileName(uploadResumeDto.getFilename())
                        .build())
                .build()
        );
    }

    @Override
    public ResponseResult<String> readPdfText(byte[] pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDPageTree pageTree = document.getPages();
            int count = pageTree.getCount();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(false);
            stripper.setStartPage(1);
            stripper.setEndPage(count);
            String content = stripper.getText(document);
            return ResponseResult.okResult(content);
        } catch (Exception e) {
            return ResponseResult.errorResult(HttpCodeEnum.PDF_READ_ERROR);
        }
    }

    @Override
    public ResponseResult<String> readDocText(byte[] docFile) {
        try (InputStream is = new ByteArrayInputStream(docFile)) {
            WordExtractor wordExtractor = new WordExtractor(is);
            return ResponseResult.okResult(wordExtractor.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult<String> readDocxText(byte[] docxFile) {
        try (InputStream is = new ByteArrayInputStream(docxFile)) {
            XWPFDocument document = new XWPFDocument(is);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
            return ResponseResult.okResult(wordExtractor.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
