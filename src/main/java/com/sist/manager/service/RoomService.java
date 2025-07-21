package com.sist.manager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.RoomDao;
import com.sist.manager.model.Room;

@Service("roomService")
public class RoomService {
	private static Logger logger = LoggerFactory.getLogger(RoomService.class);

	@Autowired
	private RoomDao roomDao;
	
	//룸리스트
	public List<Room> roomList(Room room)
	{
		List<Room> list = null;
		
		try
		{
			list = roomDao.roomList(room);
		}
		catch(Exception e)
		{
			logger.error("[RoomService] roomList Exception", e);
		}
		
		return list;
	}

	//룸리스트 건수조회
	public int roomListCount(Room room)
	{
		int count = 0;
		
		try
		{
			count = roomDao.roomListCount(room);
		}
		catch(Exception e)
		{
			logger.error("[RoomService] roomListCount Exception", e);
		}
		
		return count;
		
	}
	
	//삭제토글
	public long toggleDelete(long roomSeq)
	{
		long count = 0;
		
		try
		{
			count = roomDao.toggleDelete(roomSeq);
		}
		catch(Exception e)
		{
			logger.error("[RoomService] toggleDelete Exception", e);
		}
		
		return count;
	}
	
	//판매상태토글
	public long toggleSale(long roomSeq)
	{
		long count = 0;
		
		try
		{
			count = roomDao.toggleSale(roomSeq);
		}
		catch(Exception e)
		{
			logger.error("[RoomService] toggleSale Exception", e);
		}
		
		return count;
	}
}
