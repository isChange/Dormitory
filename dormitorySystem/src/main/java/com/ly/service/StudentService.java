package com.ly.service;

import com.ly.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.form.SearchForm;
import com.ly.form.StudentForm;
import com.ly.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
public interface StudentService extends IService<Student> {

    public ResultVo addStudent(Student student);

    public ResultVo list(Integer page , Integer size);

    public ResultVo search(SearchForm searchForm);

    public ResultVo update(StudentForm studentForm);

    public ResultVo delete(Integer id);
}
