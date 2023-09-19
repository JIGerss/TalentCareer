package pro.franky.talentcareer.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分析简历结果封装
 *
 * @author Steveny
 * @since 2023/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private String age;

    /**
     * 居住地
     */
    private String nativePlace;

    /**
     * 学历
     */
    private String academicQualifications;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 政治面貌
     */
    private String politicalAffiliation;

    /**
     * 荣誉证书
     */
    private List<String> honor;

    /**
     * 工作年限
     */
    private String workYears;
}
