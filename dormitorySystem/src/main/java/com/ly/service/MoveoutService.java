package com.ly.service;

import com.ly.entity.Moveout;
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
public interface MoveoutService extends IService<Moveout> {

    public ResultVo moveout(Integer id ,String reason);

}
