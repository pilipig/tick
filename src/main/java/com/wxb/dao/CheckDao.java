package com.wxb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wxb.entity.Check;
import com.wxb.vo.ProcessVo;

public interface CheckDao {

	//发起请假流程
	@Insert("insert into t_check (type,startname,info,startno,startdate,enddate,days,pid,flag) values (#{type},#{startname},#{info},#{startno},#{startdate},#{enddate},#{days},#{pid},1)")
	int add(Check check);
	//审批请假
	@Update("update t_check set flag=#{flag} where id=#{id}")
	int update(int id, int flag);
	//查询请假信息
	@Select("select * from t_check where startno=#{no}")
	@ResultType(Check.class)
	List<Check> queryAll(String no);
	//代办事项
	@Select("select t.id_ tid,t.name_ tname,t.CREATE_TIME_ tctime,c.* from act_ru_task t left join t_check c on t.PROC_INST_ID_=c.pid where t.ASSIGNEE_=#{name}")
	List<ProcessVo> queryByName(String name);
}
