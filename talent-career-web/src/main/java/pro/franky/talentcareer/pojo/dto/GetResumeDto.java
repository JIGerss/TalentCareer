package pro.franky.talentcareer.pojo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResumeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 页码
     */
    @Min(value = 1, message = "页数不能为0")
    private Integer page = 1;

    /**
     * 以下为对应字段是否排序，0代表默认排序，1代表升序，2代表降序
     */
    private Integer id;

    private Integer filename;

    private Integer name;

    private Integer birthday;

    private Integer maxCareer;

    private Integer college;

    private Integer workDate;

    public void fillFields(Integer value) {
        Class<GetResumeDto> aClass = GetResumeDto.class;
        for (Field field : aClass.getDeclaredFields()) {
            if (field.getType().equals(Integer.class)) {
                try {
                    if (null == field.get(this)) {
                        try {
                            field.set(this, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
