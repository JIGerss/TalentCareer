package pro.franky.talentcareer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 简历页面封装
 *
 * @author Steveny
 * @since 2023/6/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumePageVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 简历
     */
    private List<ResumeVo> resumes;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 总页码
     */
    private Integer total;

    /**
     * 总简历数
     */
    private Integer size;
}