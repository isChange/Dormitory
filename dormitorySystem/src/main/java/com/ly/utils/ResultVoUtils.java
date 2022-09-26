package com.ly.utils;

import com.ly.vo.ResultVo;

public class ResultVoUtils {

    public static ResultVo success(Object obj){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setData(obj);
        return resultVo;
    }
    public static ResultVo defeat(){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(-1);
        return resultVo;
    }
}
