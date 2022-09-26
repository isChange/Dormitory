package com.ly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.entity.Dormitory;
import com.ly.entity.Student;
import com.ly.form.SearchForm;
import com.ly.form.StudentForm;
import com.ly.mapper.DormitoryMapper;
import com.ly.mapper.StudentMapper;
import com.ly.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.utils.DateUtils;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.PageVo;
import com.ly.vo.ResultVo;
import com.ly.vo.StudentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
@Transactional
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    DormitoryMapper dormitoryMapper;

    @Override
    public ResultVo addStudent(Student student) {
        student.setCreateDate(DateUtils.createDate());
        boolean save = this.save(student);
        //判断是否添加成功
        if (!save){
          return   ResultVoUtils.defeat();
        }
        //如果添加成功了这对应的该宿舍的可住人数-1
        Dormitory dormitory = dormitoryMapper.selectById(student.getDormitoryId());
        dormitory.setAvailable(dormitory.getAvailable() - 1);
        int result = dormitoryMapper.updateById(dormitory);
        if (result != 1){
            return ResultVoUtils.defeat();
        }
        return ResultVoUtils.success(null);
        }

    @Override
    public ResultVo list(Integer page, Integer size) {
        Page<Student> studentPage = new Page<>(page,size);
        List<StudentVo> StudentVoList = new ArrayList<>();
        Page<Student> result = studentMapper.selectPage(studentPage, null);

        //将Student对象转化为StudentVo 并将属性对应的copy到Vo中 再将不同的属性注入StudentVo
        for (Student student : result.getRecords()) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student,studentVo);
            //查询对应的DormitoryName并注入
            Dormitory dormitory = dormitoryMapper.selectById(student.getDormitoryId());
            studentVo.setDormitoryName(dormitory.getName());
            StudentVoList.add(studentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) result.getTotal());
        pageVo.setData(StudentVoList);
        return ResultVoUtils.success(pageVo);
    }

    @Override
    public ResultVo search(SearchForm searchForm) {
        Page<Student> studentPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Page<Student> resultPage ;
        //判断查询的值是否为空 为空不给查询条件
        if (searchForm.getValue().equals("")){
            resultPage = studentMapper.selectPage(studentPage,null);
        }else {
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = studentMapper.selectPage(studentPage, queryWrapper);
        }
        List<StudentVo> studentVoList = new ArrayList<>();
        for (Student student : resultPage.getRecords()) {
            StudentVo studentVo = new StudentVo();
            //将student的属性值复制到studentVo中
            BeanUtils.copyProperties(student,studentVo);
            Dormitory dormitory = dormitoryMapper.selectById(student.getDormitoryId());
            studentVo.setDormitoryName(dormitory.getName());
            studentVoList.add(studentVo);
        }
        PageVo pageVo = new PageVo();
        pageVo.setTotal((int) resultPage.getTotal());
        pageVo.setData(studentVoList);
        return ResultVoUtils.success(pageVo);
    }

    @Override
    public ResultVo update(StudentForm studentForm) {
        Student student = new Student();
        BeanUtils.copyProperties(studentForm,student);
        int result = studentMapper.updateById(student);
        if (result != 1){
            return ResultVoUtils.defeat();
        }
        //如果寝室号跟开了则表示换寝室了
        if (studentForm.getOldDormitoryId() != studentForm.getDormitoryId()){
            //将对用的老寝室的可住人数+1 新寝室-1
            Integer integer = dormitoryMapper.addAvailableById(studentForm.getOldDormitoryId());
            if (integer != 1) return ResultVoUtils.defeat();
            Integer integer1 = dormitoryMapper.redAvailableById(studentForm.getDormitoryId());
            if (integer1 != 1) return ResultVoUtils.defeat();
        }
            return ResultVoUtils.success(null);

    }
    @Override
    public ResultVo delete(Integer id) {
        Student student = studentMapper.selectById(id);
        int result = studentMapper.deleteById(id);
        if (result != 1){
            return ResultVoUtils.defeat();
        }
        //将删除学生的宿舍的可住人数 +1
        Integer integer = dormitoryMapper.addAvailableById(student.getDormitoryId());
        if (integer != 1) return ResultVoUtils.defeat();
        return ResultVoUtils.success(null);
    }
}
