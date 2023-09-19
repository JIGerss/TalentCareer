package pro.franky.talentcareer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.franky.talentcareer.pojo.domain.Resume;

import java.io.Serial;
import java.io.Serializable;

/**
 * 简历概述封装
 *
 * @author Steveny
 * @since 2023/7/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 简历ID
     */
    private String id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private String birthday;

    /**
     * 学历
     */
    private String maxCareer;

    /**
     * 毕业院校
     */
    private String college;

    /**
     * 工作年限
     */
    private String workDate;

    public static ResumeVo genResumeVo(Resume resume) {
        return ResumeVo.builder()
                .id(resume.getId())
                .filename(resume.getFilename())
                .name(resume.getName())
                .birthday(resume.getBirthday())
                .college(resume.getCollege())
                .maxCareer(resume.getMaxCareer())
                .workDate(resume.getWorkDate())
                .build();
    }
}
