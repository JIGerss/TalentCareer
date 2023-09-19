package pro.franky.talentcareer.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.franky.talentcareer.pojo.enums.Process;

import java.io.Serial;
import java.io.Serializable;

/**
 * 进度信息类
 *
 * @author Steveny
 * @since 2023/7/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    /**
     * 请求ID
     */
    private Integer requestId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 进度
     */
    private Process process;
}
