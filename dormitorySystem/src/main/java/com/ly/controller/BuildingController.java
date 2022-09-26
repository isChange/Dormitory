package com.ly.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.Building;
import com.ly.entity.DormitoryAdmin;
import com.ly.form.SearchForm;
import com.ly.service.BuildingService;
import com.ly.service.DormitoryAdminService;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.BuildingVo;
import com.ly.vo.PageVo;
import com.ly.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;
    @Autowired
    DormitoryAdminService dormitoryAdminService;
    @PostMapping("/add")
    public ResultVo add(@RequestBody Building building){
        boolean save = buildingService.save(building);
        if (save){
            return ResultVoUtils.success(null);
        }else {
            return ResultVoUtils.defeat();
        }
    }

    @GetMapping("/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage , @PathVariable("pageSize") Integer pageSize){
        Page<Building> buildingPage = new Page<>(currentPage,pageSize);
        Page<Building> resultPage = buildingService.page(buildingPage);
        List<BuildingVo> buildingVos = new ArrayList<>();
        for (Building building :resultPage.getRecords()) {
            BuildingVo buildingVo = new BuildingVo();
            BeanUtils.copyProperties(building,buildingVo);
            DormitoryAdmin dormitoryAdmin = dormitoryAdminService.getById(building.getAdminId());
            buildingVo.setAdminName(dormitoryAdmin.getName());
            buildingVos.add(buildingVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) resultPage.getTotal());
        pageVo.setData(buildingVos);
        return ResultVoUtils.success(pageVo);
    }

    @GetMapping("/search")
    public ResultVo search(SearchForm searchForm){
        Page<Building> buildingPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Building> resultPage;
        if (searchForm.getValue().equals("")){
            resultPage = buildingService.page(buildingPage);
        }else {
            QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = buildingService.page(buildingPage,queryWrapper);
        }
        //对象转化为Vo与前端对接
        List<BuildingVo> buildingVos = new ArrayList<>();
        for (Building building :resultPage.getRecords()) {
            BuildingVo buildingVo = new BuildingVo();
            BeanUtils.copyProperties(building,buildingVo);
            DormitoryAdmin dormitoryAdmin = dormitoryAdminService.getById(building.getAdminId());
            buildingVo.setAdminName(dormitoryAdmin.getName());
            buildingVos.add(buildingVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) resultPage.getTotal());
        pageVo.setData(buildingVos);
        return ResultVoUtils.success(pageVo);

    }

    @GetMapping("/echo/{id}")
    public ResultVo echo(@PathVariable("id") Integer id){
        Building building = buildingService.getById(id);
        return ResultVoUtils.success(building);

    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody Building building ){
        boolean result = buildingService.updateById(building);
        if (result){
            return ResultVoUtils.success(null);
        }else {
            return ResultVoUtils.defeat();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResultVo delete(@PathVariable("id") Integer id){
      return   buildingService.delete(id);
    }

    @GetMapping("/list")
    public ResultVo list(){
        List<Building> list = buildingService.list();
        return ResultVoUtils.success(list);
    }

}

