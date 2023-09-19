package pro.franky.talentcareer.pojo.dto;

import jakarta.validation.constraints.NotBlank;
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
public class DeleteUsernameDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    @NotBlank(message = "用户名不能为空")
    private String username;
}
