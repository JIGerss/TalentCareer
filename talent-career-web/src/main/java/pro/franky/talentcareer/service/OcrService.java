package pro.franky.talentcareer.service;

import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.OcrVo;

/**
 * OCR服务
 *
 * @author Steveny
 * @since 2023/6/22
 */
public interface OcrService {
    /**
     * 请求OCR服务
     * @param fileBase64 文件base64编码
     * @return OcrVo
     */
    ResponseResult<OcrVo> requestOcr(String fileBase64);
}
