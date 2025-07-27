package com.sist.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.UserDao;
import com.sist.manager.model.Paging;
import com.sist.manager.model.User;

@Service("userService")
public class UserService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
    // 한 페이지에 보여줄 판매자 수
    private static final int SELLERS_PER_PAGE = 5; 
    // 페이징 블록에 표시할 페이지 개수
    private static final int PAGE_COUNT = 5;
	
	@Autowired
	private UserDao userDao;
	
	// 사용자 리스트
	public List<User> userList(User user)
	{
		List<User> list = null;
		
		try
		{
			list = userDao.userList(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userList Exception", e);
		}
		
		return list;
	}
	
	// 사용자 리스트 전체 건수 조회
	public int userListCount(User user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userListCount(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userListCount Exception", e);
		}
		
		return count;
	}
	
	// 사용자 조회
	public User userSelect(String userId)
	{
		User user = null;
		
		try
		{
			user = userDao.userSelect(userId);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userSelect Exception", e);
		}	
		
		return user;
	}
	
	// 사용자 정보 수정
	public int userUpdate(User user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userUpdate(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userUpdate Exception", e);
		}
		
		
		return count;
	}
	
	// 승인버튼
	public int userApprove(String userId) {
	    return userDao.userApprove(userId);
	}
	
    
    // [수정] findSellers 메서드
    public Map<String, Object> findSellers(String query, int currentPage) {
        Map<String, Object> resultMap = new HashMap<>();
        
        Map<String, Object> paramsForCount = new HashMap<>();
        paramsForCount.put("query", query);
        
        int totalCount = userDao.countSellersByQuery(paramsForCount);
        
        Paging paging = new Paging("", totalCount, SELLERS_PER_PAGE, PAGE_COUNT, currentPage, "currentPage");
        
        Map<String, Object> paramsForSelect = new HashMap<>();
        paramsForSelect.put("query", query);

        // ▼▼▼ [수정] DAO에 전달하기 전에 페이징 값을 보정합니다 (+1) ▼▼▼
        paramsForSelect.put("startRow", (currentPage - 1) * SELLERS_PER_PAGE + 1);
        paramsForSelect.put("endRow", currentPage * SELLERS_PER_PAGE);
        // ▲▲▲ 여기까지 ▲▲▲

        resultMap.put("sellers", userDao.findSellersByQuery(paramsForSelect));
        resultMap.put("paging", paging); // 프론트엔드용 paging 객체는 그대로 전달

        return resultMap;
    }
}
