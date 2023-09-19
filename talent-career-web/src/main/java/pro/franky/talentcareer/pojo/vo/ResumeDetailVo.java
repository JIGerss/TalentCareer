package pro.franky.talentcareer.pojo.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.franky.talentcareer.pojo.domain.Resume;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 简历细节封装
 *
 * @author Steveny
 * @since 2023/7/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDetailVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 简历唯一标识ID
     */
    private String id;

    /**
     * 简历文件名
     */
    private String filename;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 居住地
     */
    private String nativePlace;

    /**
     * 最高学历
     */
    private String maxCareer;

    /**
     * 毕业院校
     */
    private String college;

    /**
     * 政治面貌
     */
    private String polity;

    /**
     * 荣誉证书
     */
    private List<String> honor;

    /**
     * 工作年限
     */
    private String workDate;

    public static ResumeDetailVo genResumeDetailVo(Resume resume) {
        ResumeDetailVo detailVo = new ResumeDetailVo();
        BeanUtil.copyProperties(resume, detailVo);
        detailVo.honor = JSONUtil.toList(resume.getHonor(), String.class);
        return detailVo;
    }
}
