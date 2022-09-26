package com.ly.service.impl;

import com.ly.entity.Moveout;
import com.ly.entity.Student;
import com.ly.mapper.DormitoryMapper;
import com.ly.mapper.MoveoutMapper;
import com.ly.mapper.StudentMapper;
import com.ly.service.MoveoutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.utils.DateUtils;
import com.ly.utils.ResultVoUtils;
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
public class MoveoutServiceImpl extends ServiceImpl<MoveoutMapper, Moveout> implements MoveoutService {
    @Autowired
    MoveoutMapper moveoutMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    DormitoryMapper dormitoryMapper;

    @Override
    public ResultVo moveout(Integer id, String reason) {
        //找学生将入住改为迁出
        Student student = studentMapper.selectById(id);
        student.setState("迁出");
        int i = studentMapper.updateById(student);
        if (i != 1) return ResultVoUtils.defeat();
        //对应宿舍可以住让数+1
        Integer integer = dormitoryMapper.addAvailableById(student.getDormitoryId());
        if (integer != 1)return ResultVoUtils.defeat();
        //同时迁出记录表中增加对应的记录
        Moveout moveout = new Moveout();
        moveout.setStudentId(student.getId().toString());
        moveout.setReason(reason);
        moveout.setDormitoryId(student.getDormitoryId().toString());
        moveout.setCreateDate(DateUtils.createDate());
        int insert = moveoutMapper.insert(moveout);
        if (insert != 1)return ResultVoUtils.defeat();
        return ResultVoUtils.success(null);
    }
}
