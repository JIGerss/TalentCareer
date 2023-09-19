package pro.franky.talentcareer.pojo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 进度枚举
 *
 * @author Steveny
 * @since 2023/7/1
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Process implements Serializable {
    INIT(0, 0, "初始化"),
    READ(1, 20, "正在读取文件..."),
    CHECK(2, 40, "正在校验文件..."),
    ANALISE(3, 60, "正在分析简历..."),
    SAVE(4, 80, "正在保存简历..."),
    DONE(100, 100, "已完成"),
    ERROR(500, 0, "解析出错");


    private final Integer status;
    private final Integer percentage;
    private final String message;
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    Process(Integer status, Integer percentage, String message) {
        this.status = status;
        this.percentage = percentage;
        this.message = message;
    }
}
