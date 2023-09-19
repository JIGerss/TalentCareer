package pro.franky.talentcareer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pro.franky.talentcareer.pojo.domain.Resume;

/**
 * 简历数据库映射
 *
 * @author Steveny
 * @since 2023/6/24
 */
@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {
}
