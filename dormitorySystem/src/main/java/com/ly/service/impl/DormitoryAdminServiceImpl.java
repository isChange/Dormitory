package com.ly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.DormitoryAdmin;
import com.ly.form.RuleForm;
import com.ly.form.SearchForm;
import com.ly.mapper.DormitoryAdminMapper;
import com.ly.service.DormitoryAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.PageVo;
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
public class DormitoryAdminServiceImpl extends ServiceImpl<DormitoryAdminMapper, DormitoryAdmin> implements DormitoryAdminService {
    @Autowired
    DormitoryAdminMapper dormitoryAdminMapper;
    @Override
    public ResultVo<DormitoryAdmin> login(RuleForm ruleForm) {
        ResultVo<DormitoryAdmin> resultVo = new ResultVo<>();
        //判断用户是否为空
        //在数据库中查询对应的记录
        QueryWrapper<DormitoryAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",ruleForm.getUsername());
        DormitoryAdmin dormitoryAdmin = dormitoryAdminMapper.selectOne(queryWrapper);
        if (dormitoryAdmin == null){
            resultVo.setCode(-1);
        }else {
            //然后存在该用户则验证密码是否正确
            if (!dormitoryAdmin.getPassword().equals(ruleForm.getPassword())){
                resultVo.setCode(-2);
            }else {
                resultVo.setCode(0);
                resultVo.setData(dormitoryAdmin);
            }
        }
        return resultVo;
    }

    @Override
    public ResultVo addDormitory(DormitoryAdmin dormitoryAdmin) {
        boolean save = this.save(dormitoryAdmin);
        if (save){
            return  ResultVoUtils.success(null);
        }else {
           return ResultVoUtils.defeat();
        }
    }

    @Override
    public ResultVo searchList(SearchForm searchForm) {
        //得到分页对象
        Page<DormitoryAdmin> page = new Page<>(searchForm.getPage(),searchForm.getSize());
        //条件对象
        QueryWrapper<DormitoryAdmin> queryWrapper = new QueryWrapper<>();
        Page<DormitoryAdmin> resultPage;
        //判断查询框的value值是否为空 ，为空这不给条件
        if(searchForm.getValue().equals("")){
            resultPage = dormitoryAdminMapper.selectPage(page,null);
        }else {
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = dormitoryAdminMapper.selectPage(page, queryWrapper);
        }
        //返回分页查询结果集
        //将所需要的信息封装
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) resultPage.getTotal());
        pageVo.setData(resultPage.getRecords());
        return ResultVoUtils.success(pageVo);
    }

    @Override
    public ResultVo update(DormitoryAdmin dormitoryAdmin) {

        Integer result = dormitoryAdminMapper.updateById(dormitoryAdmin);

        if (result != 1){
          return   ResultVoUtils.defeat();
        }else {
          return   ResultVoUtils.success(null);
        }
    }


}
