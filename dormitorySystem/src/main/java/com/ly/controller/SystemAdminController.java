package com.ly.controller;


import com.ly.entity.SystemAdmin;
import com.ly.form.RuleForm;
import com.ly.service.SystemAdminService;
import com.ly.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/systemAdmin")
public class SystemAdminController {
    @Autowired
    SystemAdminService systemAdminService;

    @GetMapping("/login")
    public ResultVo<SystemAdmin> login(RuleForm ruleForm){
        ResultVo<SystemAdmin> result = systemAdminService.login(ruleForm);
        return result;
    }

}

