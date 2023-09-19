package pro.franky.talentcareer.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.franky.talentcareer.pojo.dto.DeleteIdDto;
import pro.franky.talentcareer.pojo.dto.DeleteUsernameDto;
import pro.franky.talentcareer.pojo.dto.GetResumeDto;
import pro.franky.talentcareer.pojo.dto.UploadResumeDto;
import pro.franky.talentcareer.pojo.enums.HttpCodeEnum;
import pro.franky.talentcareer.pojo.enums.Process;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.ResumeDetailVo;
import pro.franky.talentcareer.pojo.vo.ResumePageVo;
import pro.franky.talentcareer.pojo.vo.ResumeVo;
import pro.franky.talentcareer.service.FileService;
import pro.franky.talentcareer.service.ProcessService;
import pro.franky.talentcareer.service.ResumeService;

/**
 * 简历控制器
 *
 * @author Steveny
 * @since 2023/6/24
 */
@CrossOrigin
@RestController
@RequestMapping("/resume")
public class ResumeController {
    private final FileService fileService;
    private final ResumeService resumeService;
    private final ProcessService processService;

    public ResumeController(FileService fileService, ResumeService resumeService, ProcessService processService) {
        this.fileService = fileService;
        this.resumeService = resumeService;
        this.processService = processService;
    }

    /**
     * 上传简历
     */
    @PostMapping("/upload")
    public ResponseResult<ResumeVo> upload(@RequestBody @Validated UploadResumeDto uploadResumeDto) {
        ResponseResult<ResumeVo> responseResult = fileService.upload(uploadResumeDto);
        if(!responseResult.getCode().equals(HttpCodeEnum.SUCCESS.getCode())) {
            processService.setProcess(uploadResumeDto.getRequestId(), Process.ERROR);
        }
        return responseResult;
    }

    /**
     * 通过用户名查找简历
     */
    @GetMapping("/getResumeByUsername")
    public ResponseResult<ResumePageVo> getResumeByUsername(@Validated GetResumeDto getResumeDto) {
        return resumeService.getResumeByUsername(getResumeDto);
    }

    /**
     * 通过ID删除简历
     */
    @DeleteMapping("/delResumeById")
    public ResponseResult<Object> delResumeById(@RequestBody @Validated DeleteIdDto deleteDto) {
        return resumeService.delResumeById(deleteDto);
    }

    /**
     * 通过用户名删除简历
     */
    @DeleteMapping("/delResumeByUsername")
    public ResponseResult<Object> delResumeByUsername(@RequestBody @Validated DeleteUsernameDto deleteDto) {
        return resumeService.delResumeByUsername(deleteDto);
    }

    /**
     * 获取简历详细信息
     */
    @GetMapping("/getResumeById")
    public ResponseResult<ResumeDetailVo> getResumeById(String id) {
        return resumeService.getResumeById(id);
    }
}
