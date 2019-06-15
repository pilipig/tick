package com.wxb.service;

import com.wxb.entity.Check;
import com.wxb.vo.PageVo;
import com.wxb.vo.ProcessVo;

public interface CheckService {
	//增加请假
    boolean insert(Check checkProcess,String rname);
    //修改请假
    boolean update(String tid,int id, int flag);
    //删除
    boolean del(String tid,String name,int id);
    
    PageVo<Check> queryAll(String no);
    PageVo<ProcessVo> queryByName(String name);
}
