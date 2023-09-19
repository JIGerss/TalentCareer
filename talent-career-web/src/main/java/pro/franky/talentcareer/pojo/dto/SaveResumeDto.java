package pro.franky.talentcareer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.franky.talentcareer.pojo.result.ResumeData;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveResumeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    private String id;

    private Integer requestId;

    private String username;

    private String filename;

    private ResumeData data;
}
