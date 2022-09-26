package com.ly.service;

import com.ly.entity.DormitoryAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.form.RuleForm;
import com.ly.form.SearchForm;
import com.ly.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
public interface DormitoryAdminService extends IService<DormitoryAdmin> {
    public ResultVo<DormitoryAdmin> login(RuleForm ruleForm);

    public ResultVo  addDormitory (DormitoryAdmin dormitoryAdmin);

    public ResultVo searchList(SearchForm searchForm);

    public ResultVo update(DormitoryAdmin dormitoryAdmin);

}
