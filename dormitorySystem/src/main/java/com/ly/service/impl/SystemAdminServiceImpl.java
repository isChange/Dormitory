package com.ly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.entity.SystemAdmin;
import com.ly.form.RuleForm;
import com.ly.mapper.SystemAdminMapper;
import com.ly.service.SystemAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
@Service
public class SystemAdminServiceImpl extends ServiceImpl<SystemAdminMapper, SystemAdmin> implements SystemAdminService {
    @Autowired
    SystemAdminMapper systemAdminMapper;
    @Override
    public ResultVo<SystemAdmin> login(RuleForm ruleForm) {
        ResultVo<SystemAdmin> resultVo = new ResultVo<>();
        //判断该管理员是否存在
        QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",ruleForm.getUsername());
        SystemAdmin systemAdmin = systemAdminMapper.selectOne(queryWrapper);
        if (systemAdmin == null){
            resultVo.setCode(-1);
        }else {
            if (!systemAdmin.getPassword().equals(ruleForm.getPassword())){
                resultVo.setCode(-2);
            }else {
                resultVo.setCode(0);
                resultVo.setData(systemAdmin);
            }
        }
        return resultVo;
    }
}
