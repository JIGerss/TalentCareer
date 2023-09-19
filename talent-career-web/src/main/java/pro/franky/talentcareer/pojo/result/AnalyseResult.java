package pro.franky.talentcareer.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分析结果封装
 *
 * @author Steveny
 * @since 2023/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 时间戳
     */
    private String time;

    /**
     * 状态码
     */
    private String status;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 简历数据封装
     */
    private ResumeData data;
}