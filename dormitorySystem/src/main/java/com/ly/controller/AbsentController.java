package com.ly.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.Absent;
import com.ly.entity.Building;
import com.ly.entity.Dormitory;
import com.ly.entity.Student;
import com.ly.form.SearchForm;
import com.ly.service.*;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.AbsentVo;
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
@RequestMapping("/absent")
public class AbsentController {
    @Autowired
    AbsentService absentService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    DormitoryService dormitoryService;
    @Autowired
    StudentService studentService;
    @Autowired
    DormitoryAdminService dormitoryAdminService;
    @GetMapping("/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage ,@PathVariable("pageSize") Integer pageSize){
        Page<Absent> absentPage = new Page<>(currentPage, pageSize);
        Page<Absent> page = absentService.page(absentPage);
        List<AbsentVo> absentVoList = new ArrayList<>();
        for (Absent absent : page.getRecords()) {
            AbsentVo absentVo = new AbsentVo();
            BeanUtils.copyProperties(absent,absentVo);
            absentVo.setBuildingName(buildingService.getById(absent.getBuildingId()).getName());
            absentVo.setDormitoryAdminName(dormitoryAdminService.getById(absent.getDormitoryAdminId()).getName());
            absentVo.setDormitoryName(dormitoryService.getById(absent.getDormitoryId()).getName());
            absentVo.setStudentName(studentService.getById(absent.getStudentId()).getName());
            absentVoList.add(absentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(absentVoList);
        return ResultVoUtils.success(pageVo);
    }

    @GetMapping("/search")
    private ResultVo search(SearchForm searchForm){
        Page<Absent> absentPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        QueryWrapper<Absent> queryWrapper = new QueryWrapper<>();
        Page<Absent> page = null;
        if (searchForm.getValue().equals("")){
            page = absentService.page(absentPage);
        }else {
            if (searchForm.getKey().equals("buildingName")){
                QueryWrapper<Building> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.like("name",searchForm.getValue());
                List<Building> buildingList = buildingService.list(queryWrapper1);
                List<Integer> list = new ArrayList<>();
                for (Building building :buildingList) {
                    list.add(building.getId());
                }
                queryWrapper.in("building_id",list);
                page = absentService.page(absentPage,queryWrapper);
            }else if (searchForm.getKey().equals("dormitoryName")){
                QueryWrapper<Dormitory> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.like("name",searchForm.getValue());
                List<Dormitory> dormitories = dormitoryService.list(queryWrapper1);
                List<Integer> list = new ArrayList<>();
                for (Dormitory dormitory: dormitories) {
                    list.add(dormitory.getId());
                }
                queryWrapper.in("dormitory_id",list);
                page = absentService.page(absentPage,queryWrapper);
            }



        }
        List<AbsentVo> absentVoList = new ArrayList<>();
        for (Absent absent : page.getRecords()) {
            AbsentVo absentVo = new AbsentVo();
            BeanUtils.copyProperties(absent,absentVo);
            absentVo.setBuildingName(buildingService.getById(absent.getBuildingId()).getName());
            absentVo.setDormitoryAdminName(dormitoryAdminService.getById(absent.getDormitoryAdminId()).getName());
            absentVo.setDormitoryName(dormitoryService.getById(absent.getDormitoryId()).getName());
            absentVo.setStudentName(studentService.getById(absent.getStudentId()).getName());
            absentVoList.add(absentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(absentVoList);
        return ResultVoUtils.success(pageVo);

    }

    @GetMapping("/buildingList")
    public ResultVo buildingList(){
        List<Building> list = buildingService.list();
        return ResultVoUtils.success(list);
    }
    @GetMapping("/findDormitoryByBuildingId/{id}")
    public ResultVo findDormitoryByDormitoryId(@PathVariable("id") Integer id){
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",id);
        List<Dormitory> list = dormitoryService.list(queryWrapper);
        return ResultVoUtils.success(list);
    }
    @GetMapping("/findStudentByDormitoryId/{id}")
    public ResultVo findStudentByDormitoryId(@PathVariable("id") Integer id){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dormitory_id",id);
        List<Student> list = studentService.list(queryWrapper);
        return ResultVoUtils.success(list);
    }
    @PostMapping("/add")
    public ResultVo add(@RequestBody Absent absent){
        String createDate = absent.getCreateDate();
        String substring = createDate.substring(0, 10);
        absent.setCreateDate(substring);
        boolean save = absentService.save(absent);
        if (save)return ResultVoUtils.success(null);
        else return ResultVoUtils.defeat();
    }

}

