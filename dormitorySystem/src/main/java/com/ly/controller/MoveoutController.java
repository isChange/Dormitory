package com.ly.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.Dormitory;
import com.ly.entity.Moveout;
import com.ly.entity.Student;
import com.ly.form.SearchForm;
import com.ly.service.DormitoryService;
import com.ly.service.MoveoutService;
import com.ly.service.StudentService;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.MoveoutVo;
import com.ly.vo.PageVo;
import com.ly.vo.ResultVo;
import com.ly.vo.StudentVo;
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
@RequestMapping("/moveout")
public class MoveoutController {
    @Autowired
    MoveoutService moveoutService;
    @Autowired
    StudentService studentService;
    @Autowired
    DormitoryService dormitoryService;

    @GetMapping("/list/{currentPage}/{pageSize}")
    public ResultVo list(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize){
        Page<Student> studentPage = new Page<>(currentPage,pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("state","入住");
        Page<Student> page = studentService.page(studentPage, queryWrapper);
        List<StudentVo> list = new ArrayList<>();
        for (Student student : page.getRecords()) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student,studentVo);
            Dormitory dormitory = dormitoryService.getById(student.getDormitoryId());
            studentVo.setDormitoryName(dormitory.getName());
            list.add(studentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(list);
        return ResultVoUtils.success(pageVo);
    }
    @GetMapping("/search")
    public ResultVo search(SearchForm searchForm){
        Page<Student> studentPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("state","入住");
        Page<Student> page = null;
        if (searchForm.getValue().equals("")){
            page = studentService.page(studentPage,queryWrapper);
        }else {
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            page = studentService.page(studentPage,queryWrapper);
        }
        List<StudentVo> list = new ArrayList<>();
        for (Student student : page.getRecords()) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student,studentVo);
            Dormitory dormitory = dormitoryService.getById(student.getDormitoryId());
            studentVo.setDormitoryName(dormitory.getName());
            list.add(studentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(list);

        return ResultVoUtils.success(pageVo);
    }

    @PutMapping("/moveout/{id}/{reason}")
    public ResultVo moveout(@PathVariable("id") Integer id , @PathVariable("reason") String reason){
        ResultVo resultVo = moveoutService.moveout(id, reason);
        return resultVo;
    }


    @GetMapping("/moveoutList/{currentPage}/{pageSize}")
    public ResultVo moveoutList(@PathVariable("currentPage") Integer currentPage ,@PathVariable("pageSize") Integer pageSize){
        Page<Moveout> moveoutPage = new Page<>(currentPage, pageSize);
        Page<Moveout> page = moveoutService.page(moveoutPage);
        List<MoveoutVo> moveoutVos = new ArrayList<>();
        for (Moveout moveout :page.getRecords()) {
            MoveoutVo moveoutVo = new MoveoutVo();
            BeanUtils.copyProperties(moveout,moveoutVo);
            Student student = studentService.getById(moveout.getStudentId());
            moveoutVo.setStudentName(student.getName());
            Dormitory dormitory = dormitoryService.getById(moveout.getDormitoryId());
            moveoutVo.setDormitoryName(dormitory.getName());
            moveoutVos.add(moveoutVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(moveoutVos);
        return ResultVoUtils.success(pageVo);
    }

    @GetMapping("/moveoutSearch")
    public ResultVo moveSearch(SearchForm searchForm){

        Page<Moveout> moveoutPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        QueryWrapper<Moveout> queryWrapper = new QueryWrapper<>();
        Page<Moveout> page = null;
        if (searchForm.getValue().equals("")){
            page = moveoutService.page(moveoutPage);
        }else {
            //前端传入的参数与表字段不对应,故需要转化
            if (searchForm.getKey().equals("studentName")){
                //找到符合条件的学生
                QueryWrapper<Student> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.like("name",searchForm.getValue());
                List<Student> studentList = studentService.list(queryWrapper1);
                //存放学生id的集合
                List<String> list = new ArrayList<>();
                for (Student student :studentList) {
                    list.add(student.getId().toString());
                }
                //增加对应的查询条件
                queryWrapper.in("student_id",list);
                page = moveoutService.page(moveoutPage,queryWrapper);

            }else if (searchForm.getKey().equals("dormitoryName")){
                //获得宿舍id
                QueryWrapper<Dormitory> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.like("name",searchForm.getValue());
                Dormitory dormitory = dormitoryService.getOne(queryWrapper1);
                //增加对应的查询条件
                queryWrapper.eq("dormitory_id",dormitory.getId());
                page = moveoutService.page(moveoutPage,queryWrapper);
            }

        }
        //将查询到的对象转化为前端对象
        List<MoveoutVo> moveoutVos = new ArrayList<>();
        for (Moveout moveout :page.getRecords()) {
            MoveoutVo moveoutVo = new MoveoutVo();
            BeanUtils.copyProperties(moveout,moveoutVo);
            Student student = studentService.getById(moveout.getStudentId());
            moveoutVo.setStudentName(student.getName());
            Dormitory dormitory = dormitoryService.getById(moveout.getDormitoryId());
            moveoutVo.setDormitoryName(dormitory.getName());
            moveoutVos.add(moveoutVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setData(moveoutVos);
        return ResultVoUtils.success(pageVo);

    }


}

