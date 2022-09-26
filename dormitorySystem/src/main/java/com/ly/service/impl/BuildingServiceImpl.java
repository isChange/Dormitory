package com.ly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.entity.Building;
import com.ly.entity.Dormitory;
import com.ly.entity.Student;
import com.ly.mapper.BuildingMapper;
import com.ly.mapper.DormitoryMapper;
import com.ly.mapper.StudentMapper;
import com.ly.service.BuildingService;
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
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
    @Autowired
    DormitoryMapper dormitoryMapper;
    @Autowired
    BuildingMapper buildingMapper;
    @Autowired
    StudentMapper studentMapper;
    @Override
    public ResultVo delete(Integer id) {
        //查找该楼的宿舍
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",id);
        List<Dormitory> dormitories = dormitoryMapper.selectList(queryWrapper);
        for (Dormitory dormitory :dormitories) {
            //找到对应宿舍的学生
            QueryWrapper<Student> studentQueryWrapper= new QueryWrapper<>();
            studentQueryWrapper.eq("dormitory_id",dormitory.getId());
            List<Student> students = studentMapper.selectList(studentQueryWrapper);
            //将对应宿舍的学生搬入新宿舍
            for (Student student :students) {
                //得到可以入住宿舍的id号
                Integer dorId = dormitoryMapper.findAvailableByIdForFirst();
                student.setDormitoryId(dorId);
                //更改学生宿舍号
                int studentRes = studentMapper.updateById(student);
                if (studentRes != 1)return ResultVoUtils.defeat();
                //对应的该宿舍可住人数-1
                Integer integer = dormitoryMapper.redAvailableById(dorId);
                if (integer !=1 )return ResultVoUtils.defeat();
            }
            //将学生转移后再进行删除宿舍
            int deleteByDorRes= dormitoryMapper.deleteById(dormitory.getId());
            if (deleteByDorRes != 1)return ResultVoUtils.defeat();
        }
        //将宿舍删除完后删除楼
        int delete = buildingMapper.deleteById(id);
        if (delete != 1)return ResultVoUtils.defeat();

        return ResultVoUtils.success(null);
    }
}
