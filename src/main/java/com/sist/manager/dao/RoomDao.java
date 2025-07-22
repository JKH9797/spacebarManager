package com.sist.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.Room;

@Repository("roomDao")
public interface RoomDao {
	
	//룸리스트
	public List<Room> roomList(Room room);
	
	//룸리스트 건수조회
	public int roomListCount(Room room);
	
	//삭제토글
	public long toggleDelete(long roomSeq);
	
	//판매상태토글
	public long toggleSale(long roomSeq);
}
