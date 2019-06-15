package com.wxb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.*;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wxb.dao.CheckDao;
import com.wxb.entity.Check;
import com.wxb.service.CheckService;
import com.wxb.vo.PageVo;
import com.wxb.vo.ProcessVo;

@Service
public class CheckServiceImpl implements CheckService{

	@Autowired
	private CheckDao cDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	
	
	@Override
	public boolean insert(Check checkProcess, String rname) {
		// TODO Auto-generated method stub
		 Map<String,Object> vars=new HashMap<>();
	        vars.put("startName",checkProcess.getStartname());
	        vars.put("resultName",rname);
	        vars.put("managerName",rname);
	        vars.put("startDate",rname);
	        vars.put("endDate",rname);
	        vars.put("days",checkProcess.getDays());
	        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("process_check",vars);
	        checkProcess.setPid(processInstance.getId());
	        Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
	        taskService.complete(task.getId(),vars);
	        return cDao.add(checkProcess)>0;
	}

	@Override
	public boolean update(String tid, int id, int flag) {
		// TODO Auto-generated method stub
		taskService.complete(tid);
        return cDao.update(id,flag)>0;
	}

	@Override
	public boolean del(String tid, String name, int id) {
		// TODO Auto-generated method stub
		taskService.resolveTask(taskService.createTaskQuery().processInstanceId(tid).taskAssignee(name).singleResult().getId());
        return cDao.update(id,4)>0;
	}

	@Override
	public PageVo<Check> queryAll(String no) {
		// TODO Auto-generated method stub
		PageVo<Check> pageVo = new PageVo<>();
		pageVo.setData(cDao.queryAll(no));
		pageVo.setCount(pageVo.getData().size());
		pageVo.setCode(0);
		return pageVo;
	}

	@Override
	public PageVo<ProcessVo> queryByName(String name) {
		// TODO Auto-generated method stub
		PageVo<ProcessVo> pageVo=new PageVo<>();
        pageVo.setData(cDao.queryByName(name));
        pageVo.setCount(pageVo.getData().size());
        pageVo.setCode(0);
        return pageVo;
	}

}
