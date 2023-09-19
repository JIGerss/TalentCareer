package pro.franky.talentcareer.pojo.dto;

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
public class AnalyseResumeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919081114514233L;

    private String fileName;

    private String content;
}
