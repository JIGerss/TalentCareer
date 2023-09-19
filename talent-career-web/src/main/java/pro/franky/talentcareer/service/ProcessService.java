package pro.franky.talentcareer.service;

import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.enums.Process;

/**
 * 进度服务
 *
 * @author Steveny
 * @since 2023/6/22
 */
public interface ProcessService {
    /**
     * 初始化进度
     *
     * @param uploadResumeDto uploadResumeDto
     */
    void initProcess(UploadResumeDto uploadResumeDto);


    /**
     * 设置进度
     *
     * @param requestId requestId
     * @param process process
     */
    void setProcess(Integer requestId, Process process);
}
