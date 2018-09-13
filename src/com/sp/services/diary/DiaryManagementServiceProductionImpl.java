package com.sp.services.diary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sp.dataaccess.ActionDao;
import com.sp.domain.Action;

@Transactional
@Service("diaryService")
public class DiaryManagementServiceProductionImpl implements DiaryManagementService {

	private ActionDao dao;
	@Autowired
	public DiaryManagementServiceProductionImpl(ActionDao dao) {
		
		this.dao = dao;
	}

	@Override
	public void recordAction(Action action) {
		 dao.create(action);
	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {
		// TODO Auto-generated method stub
		return dao.getIncompleteActions(requiredUser);
	}

}
