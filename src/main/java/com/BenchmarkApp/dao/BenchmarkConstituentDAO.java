package com.BenchmarkApp.dao;

import java.util.List;
import java.util.Date;

import com.BenchmarkApp.dao.GenericDAO;
import com.BenchmarkApp.domain.BenchmarkConstituent;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface BenchmarkConstituentDAO extends GenericDAO<BenchmarkConstituent, Integer> {
  
	List<BenchmarkConstituent> findAll();
	


}

