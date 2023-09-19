package pro.franky.talentcareer.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.franky.talentcareer.pojo.domain.ProcessInfo;
import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.enums.Process;
import pro.franky.talentcareer.service.ProcessService;
import pro.franky.talentcareer.websocket.TalentCareerEndPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steveny
 * @since 2023/6/22
 */
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {
    private final Map<Integer, ProcessInfo> processInfo = new HashMap<>();

    @Override
    public void initProcess(UploadResumeDto uploadResumeDto) {
        processInfo.put(uploadResumeDto.getRequestId(), ProcessInfo.builder()
                .requestId(uploadResumeDto.getRequestId())
                .username(uploadResumeDto.getUsername())
                .process(Process.INIT)
                .build()
        );
    }

    @Override
    public void setProcess(Integer requestId, Process process) {
        ProcessInfo info = processInfo.get(requestId);
        if (null != info) {
            info.setProcess(process);
            sendMessage(info);
        }
    }

    /**
     * 发送消息
     *
     * @param processInfo processInfo
     */
    private void sendMessage(ProcessInfo processInfo) {
        if (TalentCareerEndPoint.END_POINTS.containsKey(processInfo.getUsername())) {
            try {
                TalentCareerEndPoint.sendMessage(processInfo.getUsername(),
                        new ObjectMapper().writeValueAsString(processInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
