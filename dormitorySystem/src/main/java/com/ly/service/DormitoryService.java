package com.ly.service;

import com.ly.entity.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
public interface DormitoryService extends IService<Dormitory> {
    public ResultVo delete(Integer id);
}
