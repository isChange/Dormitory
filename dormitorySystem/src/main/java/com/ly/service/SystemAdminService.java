package com.ly.service;

import com.ly.entity.SystemAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.form.RuleForm;
import com.ly.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
public interface SystemAdminService extends IService<SystemAdmin> {
    public ResultVo<SystemAdmin> login(RuleForm ruleForm);
}
