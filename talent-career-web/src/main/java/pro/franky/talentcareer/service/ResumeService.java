package pro.franky.talentcareer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pro.franky.talentcareer.pojo.domain.Resume;
import pro.franky.talentcareer.pojo.dto.*;
import pro.franky.talentcareer.pojo.result.ResponseResult;
import pro.franky.talentcareer.pojo.vo.ResumeDetailVo;
import pro.franky.talentcareer.pojo.vo.ResumePageVo;
import pro.franky.talentcareer.pojo.vo.ResumeVo;

/**
 * 简历服务
 *
 * @author Steveny
 * @since 2023/6/24
 */
public interface ResumeService extends IService<Resume> {
    /**
     * 解析简历
     *
     * @param analyseDto analyseDto
     * @return 返回解析完的简历对象
     */
    ResponseResult<ResumeVo> analyseResume(AnalyseDto analyseDto);

    /**
     * 将简历信息保存到数据库中
     *
     * @param resumeDto 包含username
     * @return 该条简历
     */
    ResponseResult<ResumeVo> saveResume(SaveResumeDto resumeDto);

    /**
     * 根据用户名获取简历
     *
     * @param resumeDto 包含username
     * @return 用户的所有简历
     */
    ResponseResult<ResumePageVo> getResumeByUsername(GetResumeDto resumeDto);

    /**
     * 根据id删除简历
     * @param deleteDto 包含id和username
     * @return 删除是否成功
     */
    ResponseResult<Object> delResumeById(DeleteIdDto deleteDto);

    /**
     * 根据username删除简历
     * @param deleteDto 包含username
     * @return 删除是否成功
     */
    ResponseResult<Object> delResumeByUsername(DeleteUsernameDto deleteDto);

    /**
     * 根据用户ID获取简历
     *
     * @param id 包含ID
     * @return ID简历
     */
    ResponseResult<ResumeDetailVo> getResumeById(String id);
}
