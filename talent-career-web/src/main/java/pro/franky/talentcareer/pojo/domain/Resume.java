package pro.franky.talentcareer.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

/**
 * 简历数据库映射类
 *
 * @author Steveny
 * @since 2023/6/24
 */
@Data
@Builder
@TableName("tb_resume")
public class Resume {
    /**
     * 简历唯一标识ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 简历文件名
     */
    @TableField("filename")
    private String filename;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 居住地
     */
    @TableField("native_place")
    private String nativePlace;

    /**
     * 最高学历
     */
    @TableField("max_career")
    private String maxCareer;

    /**
     * 毕业院校
     */
    @TableField("college")
    private String college;

    /**
     * 政治面貌
     */
    @TableField("polity")
    private String polity;

    /**
     * 荣誉证书
     */
    @TableField("honor")
    private String honor;

    /**
     * 工作年限
     */
    @TableField("work_date")
    private String workDate;

    /**
     * 逻辑删除
     */
    @TableLogic()
    private String exist;
}
