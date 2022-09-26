package com.ly.mapper;

import com.ly.entity.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
public interface DormitoryMapper extends BaseMapper<Dormitory> {
        Integer addAvailableById(@Param("id") Integer id);
        Integer redAvailableById(@Param("id") Integer id);
        Integer findAvailableByIdForFirst();
}
