package pro.franky.talentcareer.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResumeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    @Range(min = 0, max = 1000, message = "requestId范围有误")
    @NotNull(message = "requestId不能为空")
    private Integer requestId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = ".+\\..+", message = "文件名格式错误")
    private String filename;

    @NotBlank(message = "文件内容不能为空")
    private String fileBase64;
}
