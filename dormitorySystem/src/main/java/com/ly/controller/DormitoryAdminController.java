package com.ly.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.DormitoryAdmin;
import com.ly.form.RuleForm;
import com.ly.form.SearchForm;
import com.ly.service.DormitoryAdminService;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.PageVo;
import com.ly.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/dormitoryAdmin")
public class DormitoryAdminController {
    @Autowired
    DormitoryAdminService dormitoryAdminService;

    @GetMapping("/login")
    public ResultVo<DormitoryAdmin> login(RuleForm ruleForm){

        ResultVo<DormitoryAdmin> result = dormitoryAdminService.login(ruleForm);

        return result;

    }
    @PostMapping("/add")
    public ResultVo add(@RequestBody DormitoryAdmin dormitoryAdmin){
        ResultVo resultVo = dormitoryAdminService.addDormitory(dormitoryAdmin);
        return resultVo;
    }

    @GetMapping("/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage ,@PathVariable("pageSize") Integer size){
        //得到分页对象
        Page<DormitoryAdmin> dormitoryAdminPage = new Page<>(currentPage, size);
        //用分页对象查询分页结果
        Page<DormitoryAdmin> page = dormitoryAdminService.page(dormitoryAdminPage);
        //将所需的信息封装返回
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(page.getRecords());
        return ResultVoUtils.success(pageVo);
    }
    @GetMapping("list")
    public ResultVo lsit(){
        List<DormitoryAdmin> list = dormitoryAdminService.list();
        return ResultVoUtils.success(list);
    }
    @GetMapping("/search")
    public ResultVo search(SearchForm searchForm){

        ResultVo resultVo = dormitoryAdminService.searchList(searchForm);
        return resultVo;

    }

    @GetMapping("/findById/{id}")
    public ResultVo echo(@PathVariable("id") Integer id){
        DormitoryAdmin result = this.dormitoryAdminService.getById(id);
        return ResultVoUtils.success(result);
    }
    @PutMapping("/update")
    public ResultVo update(@RequestBody DormitoryAdmin dormitoryAdmin){
        return dormitoryAdminService.update(dormitoryAdmin);
    }
    @DeleteMapping("/delete/{id}")
    public ResultVo delete(@PathVariable("id") Integer id){

        boolean result = dormitoryAdminService.removeById(id);
        if (result){
            return ResultVoUtils.success(null);
        }else {
            return ResultVoUtils.defeat();
        }
    }


}

