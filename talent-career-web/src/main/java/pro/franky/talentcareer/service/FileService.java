package pro.franky.talentcareer.service;

import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.ResumeVo;

/**
 * 文件处理服务
 *
 * @author Steveny
 * @since 2023/6/22
 */
public interface FileService {
    /**
     * 文件上传服务
     *
     * @param uploadResumeDto 文件base64编码，文件名，用户名
     * @return 返回解析完的数据
     */
    ResponseResult<ResumeVo> upload(UploadResumeDto uploadResumeDto);

    /**
     * 解析PDF文件，获取PDF的文字内容并返回处理过的文字信息
     *
     * @param pdfFile PDF文件
     * @return 处理过的PDF文字信息
     */
    ResponseResult<String> readPdfText(byte[] pdfFile);

    /**
     * 解析DOC文件，获取DOC的文字内容并返回处理过的文字信息
     *
     * @param docFile DOC文件
     * @return 处理过的DOC文字信息
     */
    ResponseResult<String> readDocText(byte[] docFile);

    /**
     * 解析DOCX文件，获取DOCX的文字内容并返回处理过的文字信息
     *
     * @param docxFile DOCX文件
     * @return 处理过的DOCX文字信息
     */
    ResponseResult<String> readDocxText(byte[] docxFile);
}
