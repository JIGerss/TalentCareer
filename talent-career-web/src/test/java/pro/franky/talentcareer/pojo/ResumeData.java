package pro.franky.talentcareer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Steveny
 * @since 2023/7/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeData {
    private String name;
    private String age;
    private String education;
    private String school;
    private String work_time;
    private String match_position;
}
