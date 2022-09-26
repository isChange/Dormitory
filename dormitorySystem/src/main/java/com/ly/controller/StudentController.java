package com.ly.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.entity.Dormitory;
import com.ly.entity.Student;
import com.ly.form.SearchForm;
import com.ly.form.StudentForm;
import com.ly.mapper.DormitoryMapper;
import com.ly.service.StudentService;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.ResultVo;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    DormitoryMapper dormitoryMapper;

    @GetMapping("/findById_list/{id}")
    public ResultVo findByIdList(@PathVariable("id") Integer id){
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("available",0);
        List<Dormitory> dormitories = dormitoryMapper.selectList(queryWrapper);
        //防止自己本身住的寝室满了显示不出寝室名
        dormitories.add(dormitoryMapper.selectById(studentService.getById(id).getDormitoryId()));
        return ResultVoUtils.success(dormitories);
    }

    @PostMapping("/add")
    public ResultVo addStudent(@RequestBody Student student){
        ResultVo resultVo = studentService.addStudent(student);
        return resultVo;
    }

    @GetMapping("/dormitory/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage ,@PathVariable("pageSize") Integer pageSize){
        ResultVo resultVo = studentService.list(currentPage, pageSize);
        return resultVo;
    }

    @GetMapping ("/dormitory/search")
    public ResultVo search(SearchForm searchForm){
        ResultVo resultVo = studentService.search(searchForm);
        return resultVo;
    }
    @GetMapping("/echo/{id}")
    public ResultVo echo(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        StudentForm studentForm = new StudentForm();
        BeanUtils.copyProperties(student,studentForm);
        studentForm.setOldDormitoryId(student.getDormitoryId());
        return ResultVoUtils.success(studentForm);
    }
    @PutMapping("/update")
    public ResultVo update(@RequestBody StudentForm studentForm){
        ResultVo resultVo = studentService.update(studentForm);
        return resultVo;
    }

    @DeleteMapping("/delete/{id}")
    public ResultVo delete(@PathVariable("id") Integer id){
        ResultVo resultVo = studentService.delete(id);
        return resultVo;

    }


}

