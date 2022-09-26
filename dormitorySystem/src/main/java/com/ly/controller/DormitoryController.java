package com.ly.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.Building;
import com.ly.entity.Dormitory;
import com.ly.form.SearchForm;
import com.ly.service.BuildingService;
import com.ly.service.DormitoryService;
import com.ly.service.StudentService;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.DormitoryVo;
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
@RequestMapping("/dormitory")
public class DormitoryController {
    @Autowired
    DormitoryService dormitoryService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResultVo add(@RequestBody Dormitory dormitory){
        dormitory.setAvailable(dormitory.getType());
        boolean save = dormitoryService.save(dormitory);
        if (save) return ResultVoUtils.success(null);
        else return ResultVoUtils.defeat();
    }
    @GetMapping("/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage , @PathVariable("pageSize") Integer pageSize){
        Page<Dormitory> dormitoryPage = new Page<>(currentPage, pageSize);
        Page<Dormitory> page = dormitoryService.page(dormitoryPage);
        List<Dormitory> records = page.getRecords();
        List<DormitoryVo> list = new ArrayList<>();
        for (Dormitory dormitory :records) {
            DormitoryVo dormitoryVo = new DormitoryVo();
            BeanUtils.copyProperties(dormitory,dormitoryVo);
            Building building = buildingService.getById(dormitory.getBuildingId());
            dormitoryVo.setBuildingName(building.getName());
            list.add(dormitoryVo);
        }

        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(list);
        return ResultVoUtils.success(pageVo);
    }
    @GetMapping("/search")
    public ResultVo search(SearchForm searchForm){
        Page<Dormitory> dormitoryPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Dormitory> page;
        if (searchForm.getValue().equals("")){
            page = dormitoryService.page(dormitoryPage);
        }else {
            QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            page = dormitoryService.page(dormitoryPage,queryWrapper);
        }
        List<Dormitory> records = page.getRecords();
        List<DormitoryVo> list = new ArrayList<>();
        for (Dormitory dormitory :records) {
            DormitoryVo dormitoryVo = new DormitoryVo();
            BeanUtils.copyProperties(dormitory,dormitoryVo);
            Building building = buildingService.getById(dormitory.getBuildingId());
            dormitoryVo.setBuildingName(building.getName());
            list.add(dormitoryVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(list);
        return ResultVoUtils.success(pageVo);
    }

    @GetMapping("/findById/{id}")
    public ResultVo findById(@PathVariable("id")Integer id){
        Dormitory result = dormitoryService.getById(id);
        return ResultVoUtils.success(result);
    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody Dormitory dormitory){
        boolean result = dormitoryService.updateById(dormitory);
        if (result){
            return ResultVoUtils.success(null);
        }else {
            return ResultVoUtils.defeat();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResultVo delete(@PathVariable("id") Integer id){
        ResultVo resultVo = dormitoryService.delete(id);
        return resultVo;
    }

}

