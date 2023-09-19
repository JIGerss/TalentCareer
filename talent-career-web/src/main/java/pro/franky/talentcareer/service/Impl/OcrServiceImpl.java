package pro.franky.talentcareer.service.Impl;

import com.ecloud.sdk.commtextverify.v1.Client;
import com.ecloud.sdk.commtextverify.v1.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.franky.talentcareer.exception.ECloudException;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.OcrVo;
import pro.franky.talentcareer.service.OcrService;

/**
 * @author Steveny
 * @since 2023/6/22
 */
@Service
@Slf4j
public final class OcrServiceImpl implements OcrService {
    private final Client client;

    public OcrServiceImpl(Client client) {
        this.client = client;
    }

    public ResponseResult<OcrVo> requestOcr(String fileBase64) {
        WebimageRequest webimageRequest = new WebimageRequest();
        WebimageBody webimageBody = new WebimageBody();
        webimageBody.setImage(fileBase64);
        webimageRequest.setWebimageBody(webimageBody);
        WebimageResponse result = new WebimageResponse();
        try {
            result = client.webimage(webimageRequest);
            WebimageResponseContent content = result.getBody().getContent();
            StringBuilder word = new StringBuilder();
            content.getPrismWordsInfo().forEach(info -> word.append(info.getWord()).append(" "));
            return ResponseResult.okResult(OcrVo
                    .builder()
                    .line(content.getPrismWnum())
                    .word(word.toString())
                    .build()
            );
        } catch (Exception e) {
            throw new ECloudException(e, result.getErrorCode());
        }
    }
}
