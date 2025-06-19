package com.sist.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.AdminDao;
import com.sist.manager.model.Admin;

@Service("adminService")
public class AdminService {
	
	private static Logger logger = LoggerFactory.getLogger(AdminService.class); 
	
	@Autowired
	private AdminDao adminDao;
	
	// 관리자 조회
	public Admin adminSelect(String adminId)
	{
		Admin admin = null;
		
		try
		{
			admin = adminDao.adminSelect(adminId);
		}
		catch(Exception e)
		{
			logger.error("[AdminService] adminSelect Exception : ", e);
		}
		
		return admin;
	}
}
