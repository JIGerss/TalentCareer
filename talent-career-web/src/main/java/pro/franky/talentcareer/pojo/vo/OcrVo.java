package pro.franky.talentcareer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * OCR结果封装
 *
 * @author Steveny
 * @since 2023/7/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OcrVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 行数
     */
    private Long line;

    /**
     * 文本
     */
    private String word;
}
