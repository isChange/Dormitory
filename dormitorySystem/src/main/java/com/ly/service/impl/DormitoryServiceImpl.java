package com.ly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.entity.Dormitory;
import com.ly.entity.Student;
import com.ly.mapper.DormitoryMapper;
import com.ly.mapper.StudentMapper;
import com.ly.service.DormitoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.utils.ResultVoUtils;
import com.ly.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-09-19
 */
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService {
    @Autowired
    DormitoryMapper dormitoryMapper;
    @Autowired
    StudentMapper studentMapper;

    @Override
    public ResultVo delete(Integer id) {
        //查询该寝室的学生并将学生迁入空余寝室
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dormitory_id",id);
        List<Student> students = studentMapper.selectList(queryWrapper);
        for (Student student :students) {
            Integer dorId = dormitoryMapper.findAvailableByIdForFirst();
            student.setDormitoryId(dorId);
            int result = studentMapper.updateById(student);
            if (result != 1)return ResultVoUtils.defeat();
            Integer integer = dormitoryMapper.redAvailableById(dorId);
            if (integer != integer)return ResultVoUtils.defeat();
        }
        //修改完删除宿舍
        int deleteById = dormitoryMapper.deleteById(id);
        if (deleteById != 1)return ResultVoUtils.defeat();

        return ResultVoUtils.success(null);
    }
}
