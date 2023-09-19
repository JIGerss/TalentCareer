package pro.franky.talentcareer.service.Impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.franky.talentcareer.mapper.ResumeMapper;
import pro.franky.talentcareer.pojo.domain.Resume;
import pro.franky.talentcareer.pojo.dto.*;
import pro.franky.talentcareer.pojo.enums.HttpCodeEnum;
import pro.franky.talentcareer.pojo.enums.Process;
import pro.franky.talentcareer.pojo.result.AnalyseResult;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.result.ResumeData;
import pro.franky.talentcareer.pojo.vo.ResumeDetailVo;
import pro.franky.talentcareer.pojo.vo.ResumePageVo;
import pro.franky.talentcareer.pojo.vo.ResumeVo;
import pro.franky.talentcareer.service.ProcessService;
import pro.franky.talentcareer.service.ResumeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steveny
 * @since 2023/6/24
 */
@Slf4j
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
    private final String model;
    private final ProcessService processService;

    public ResumeServiceImpl(@Value("${career.model_url}") String model, ProcessService processService) {
        this.model = model;
        this.processService = processService;
    }

    @Override
    public ResponseResult<ResumeVo> analyseResume(AnalyseDto analyseDto) {
        log.info("简历解析，文件名为 --> " + analyseDto.getAnalyseResumeDto().getFileName());
        processService.setProcess(analyseDto.getRequestId(), Process.ANALISE);
        try {
            AnalyseResult result = JSONUtil.toBean(HttpRequest.post(model)
                            .contentType("application/json")
                            .body(JSONUtil.toJsonStr(analyseDto.getAnalyseResumeDto()))
                            .timeout(120 * 1000)
                            .execute().body()
                    , AnalyseResult.class
            );
            return saveResume(SaveResumeDto.builder()
                    .data(result.getData())
                    .requestId(analyseDto.getRequestId())
                    .username(analyseDto.getUsername())
                    .filename(analyseDto.getAnalyseResumeDto().getFileName())
                    .build()
            );
        } catch (IORuntimeException e) {
            log.info("请求超时");
            return ResponseResult.errorResult(HttpCodeEnum.TIME_OUT);
        } catch (Exception e) {
            return ResponseResult.errorResult(HttpCodeEnum.REQUEST_ERROR);
        }
    }

    @Override
    public ResponseResult<ResumeVo> saveResume(SaveResumeDto resumeDto) {
        log.info("简历保存，文件名为 --> " + resumeDto.getFilename());
        processService.setProcess(resumeDto.getRequestId(), Process.SAVE);
        ResumeData data = resumeDto.getData();
        Resume resume = Resume.builder()
                .username(resumeDto.getUsername())
                .filename(data.getFileName())
                .name(data.getName())
                .college(data.getSchool())
                .nativePlace(data.getNativePlace())
                .polity(data.getPoliticalAffiliation())
                .honor(JSONUtil.toJsonStr(data.getHonor()))
                .maxCareer(data.getAcademicQualifications())
                .birthday(Optional.ofNullable(data.getAge()).orElse(" "))
                .workDate(Optional.ofNullable(data.getWorkYears()).orElse(" "))
                .build();
        log.info(resume.toString());
        boolean isSucceed = save(resume);
        if (isSucceed) {
            processService.setProcess(resumeDto.getRequestId(), Process.DONE);
            return ResponseResult.okResult(ResumeVo.genResumeVo(resume));
        } else {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_UPLOAD_ERROR);
        }
    }

    @Override
    public ResponseResult<ResumePageVo> getResumeByUsername(GetResumeDto resumeDto) {
        log.info("简历查询，用户名为 --> " + resumeDto.getUsername());
        resumeDto.fillFields(0);
        // 排序并分页
        LambdaQueryWrapper<Resume> queryWrapper1 = new LambdaQueryWrapper<>();
        IPage<Resume> iPage = getBaseMapper().selectPage(new Page<>(resumeDto.getPage(), 10), queryWrapper1
                .eq(Resume::getUsername, resumeDto.getUsername())
                .orderBy(resumeDto.getId() != 0, resumeDto.getId() == 1, Resume::getId)
                .orderBy(resumeDto.getFilename() != 0, resumeDto.getFilename() == 1, Resume::getFilename)
                .orderBy(resumeDto.getName() != 0, resumeDto.getName() == 1, Resume::getName)
                .orderBy(resumeDto.getBirthday() != 0, resumeDto.getBirthday() == 1, Resume::getBirthday)
                .orderBy(resumeDto.getCollege() != 0, resumeDto.getCollege() == 1, Resume::getCollege)
                .orderBy(resumeDto.getMaxCareer() != 0, resumeDto.getMaxCareer() == 1, Resume::getMaxCareer)
                .orderBy(resumeDto.getWorkDate() != 0, resumeDto.getWorkDate() == 1, Resume::getWorkDate)
        );
        // 查询总记录数
        LambdaQueryWrapper<Resume> queryWrapper2 = new LambdaQueryWrapper<>();
        long count = count(queryWrapper2.eq(Resume::getUsername, resumeDto.getUsername()));
        List<Resume> list = iPage.getRecords();
        List<ResumeVo> voList = new ArrayList<>(list.size());
        list.forEach(resume -> voList.add(ResumeVo.genResumeVo(resume)));
        return ResponseResult.okResult(ResumePageVo.builder()
                .current((int) iPage.getCurrent())
                .total((int) iPage.getPages())
                .size((int) count)
                .resumes(voList)
                .build()
        );
    }

    @Override
    public ResponseResult<Object> delResumeById(DeleteIdDto deleteDto) {
        log.info("简历删除，ID为 --> " + deleteDto.getId());
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        int delete = getBaseMapper().delete(queryWrapper
                .eq(Resume::getUsername, deleteDto.getUsername())
                .eq(Resume::getId, deleteDto.getId())
        );
        if (delete > 0) return ResponseResult.okResult();
        else return ResponseResult.errorResult(HttpCodeEnum.INVALID_ID, "该ID查无数据，或用户名错误");
    }

    @Override
    public ResponseResult<Object> delResumeByUsername(DeleteUsernameDto deleteDto) {
        log.info("简历全部删除，Username为 --> " + deleteDto.getUsername());
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        int delete = getBaseMapper().delete(queryWrapper
                .eq(Resume::getUsername, deleteDto.getUsername())
        );
        if (delete > 0) return ResponseResult.okResult();
        else return ResponseResult.errorResult(HttpCodeEnum.INVALID_ID, "该用户名错误");
    }

    @Override
    public ResponseResult<ResumeDetailVo> getResumeById(String id) {
        log.info("简历查询，ID名为 --> " + id);
        Resume resume = getById(id);
        if (null != resume) return ResponseResult.okResult(ResumeDetailVo.genResumeDetailVo(resume));
        else return ResponseResult.errorResult(HttpCodeEnum.INVALID_ID);
    }
}
