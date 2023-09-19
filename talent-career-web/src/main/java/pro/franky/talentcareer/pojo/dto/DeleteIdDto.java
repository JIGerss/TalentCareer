package pro.franky.talentcareer.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIdDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    @NotEmpty(message = "ID不能为空")
    @Pattern(regexp = "[0123456789]{19}", message = "ID格式错误")
    private String id;

    @NotBlank(message = "用户名不能为空")
    private String username;
}
