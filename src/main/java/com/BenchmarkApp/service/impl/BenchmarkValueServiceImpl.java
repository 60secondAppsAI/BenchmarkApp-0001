package com.BenchmarkApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.BenchmarkApp.dao.GenericDAO;
import com.BenchmarkApp.service.GenericService;
import com.BenchmarkApp.service.impl.GenericServiceImpl;
import com.BenchmarkApp.dao.BenchmarkValueDAO;
import com.BenchmarkApp.domain.BenchmarkValue;
import com.BenchmarkApp.dto.BenchmarkValueDTO;
import com.BenchmarkApp.dto.BenchmarkValueSearchDTO;
import com.BenchmarkApp.dto.BenchmarkValuePageDTO;
import com.BenchmarkApp.dto.BenchmarkValueConvertCriteriaDTO;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import com.BenchmarkApp.service.BenchmarkValueService;
import com.BenchmarkApp.util.ControllerUtils;


@Service
public class BenchmarkValueServiceImpl extends GenericServiceImpl<BenchmarkValue, Integer> implements BenchmarkValueService {

    private final static Logger logger = LoggerFactory.getLogger(BenchmarkValueServiceImpl.class);

	@Autowired
	BenchmarkValueDAO benchmarkValueDao;

	

	@Override
	public GenericDAO<BenchmarkValue, Integer> getDAO() {
		return (GenericDAO<BenchmarkValue, Integer>) benchmarkValueDao;
	}
	
	public List<BenchmarkValue> findAll () {
		List<BenchmarkValue> benchmarkValues = benchmarkValueDao.findAll();
		
		return benchmarkValues;	
		
	}

	public ResultDTO addBenchmarkValue(BenchmarkValueDTO benchmarkValueDTO, RequestDTO requestDTO) {

		BenchmarkValue benchmarkValue = new BenchmarkValue();

		benchmarkValue.setBenchmarkValueId(benchmarkValueDTO.getBenchmarkValueId());

		benchmarkValue.setBenchmarkId(benchmarkValueDTO.getBenchmarkId());

		benchmarkValue.setValueDate(benchmarkValueDTO.getValueDate());

		benchmarkValue.setValue(benchmarkValueDTO.getValue());

		benchmarkValue.setDailyReturn(benchmarkValueDTO.getDailyReturn());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		benchmarkValue = benchmarkValueDao.save(benchmarkValue);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<BenchmarkValue> getAllBenchmarkValues(Pageable pageable) {
		return benchmarkValueDao.findAll(pageable);
	}

	public Page<BenchmarkValue> getAllBenchmarkValues(Specification<BenchmarkValue> spec, Pageable pageable) {
		return benchmarkValueDao.findAll(spec, pageable);
	}

	public ResponseEntity<BenchmarkValuePageDTO> getBenchmarkValues(BenchmarkValueSearchDTO benchmarkValueSearchDTO) {
	
			Integer benchmarkValueId = benchmarkValueSearchDTO.getBenchmarkValueId(); 
 			Integer benchmarkId = benchmarkValueSearchDTO.getBenchmarkId(); 
 			Date valueDate = benchmarkValueSearchDTO.getValueDate();
   			String sortBy = benchmarkValueSearchDTO.getSortBy();
			String sortOrder = benchmarkValueSearchDTO.getSortOrder();
			String searchQuery = benchmarkValueSearchDTO.getSearchQuery();
			Integer page = benchmarkValueSearchDTO.getPage();
			Integer size = benchmarkValueSearchDTO.getSize();

	        Specification<BenchmarkValue> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, benchmarkValueId, "benchmarkValueId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, benchmarkId, "benchmarkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, valueDate, "valueDate");
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<BenchmarkValue> benchmarkValues = this.getAllBenchmarkValues(spec, pageable);
		
		//System.out.println(String.valueOf(benchmarkValues.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(benchmarkValues.getTotalPages()));
		
		List<BenchmarkValue> benchmarkValuesList = benchmarkValues.getContent();
		
		BenchmarkValueConvertCriteriaDTO convertCriteria = new BenchmarkValueConvertCriteriaDTO();
		List<BenchmarkValueDTO> benchmarkValueDTOs = this.convertBenchmarkValuesToBenchmarkValueDTOs(benchmarkValuesList,convertCriteria);
		
		BenchmarkValuePageDTO benchmarkValuePageDTO = new BenchmarkValuePageDTO();
		benchmarkValuePageDTO.setBenchmarkValues(benchmarkValueDTOs);
		benchmarkValuePageDTO.setTotalElements(benchmarkValues.getTotalElements());
		return ResponseEntity.ok(benchmarkValuePageDTO);
	}

	public List<BenchmarkValueDTO> convertBenchmarkValuesToBenchmarkValueDTOs(List<BenchmarkValue> benchmarkValues, BenchmarkValueConvertCriteriaDTO convertCriteria) {
		
		List<BenchmarkValueDTO> benchmarkValueDTOs = new ArrayList<BenchmarkValueDTO>();
		
		for (BenchmarkValue benchmarkValue : benchmarkValues) {
			benchmarkValueDTOs.add(convertBenchmarkValueToBenchmarkValueDTO(benchmarkValue,convertCriteria));
		}
		
		return benchmarkValueDTOs;

	}
	
	public BenchmarkValueDTO convertBenchmarkValueToBenchmarkValueDTO(BenchmarkValue benchmarkValue, BenchmarkValueConvertCriteriaDTO convertCriteria) {
		
		BenchmarkValueDTO benchmarkValueDTO = new BenchmarkValueDTO();

		benchmarkValueDTO.setBenchmarkValueId(benchmarkValue.getBenchmarkValueId());

		benchmarkValueDTO.setBenchmarkId(benchmarkValue.getBenchmarkId());

		benchmarkValueDTO.setValueDate(benchmarkValue.getValueDate());

		benchmarkValueDTO.setValue(benchmarkValue.getValue());

		benchmarkValueDTO.setDailyReturn(benchmarkValue.getDailyReturn());
		
		return benchmarkValueDTO;
	}

	public ResultDTO updateBenchmarkValue(BenchmarkValueDTO benchmarkValueDTO, RequestDTO requestDTO) {
		
		BenchmarkValue benchmarkValue = benchmarkValueDao.getById(benchmarkValueDTO.getBenchmarkValueId());
		
		benchmarkValue.setBenchmarkValueId(ControllerUtils.setValue(benchmarkValue.getBenchmarkValueId(), benchmarkValueDTO.getBenchmarkValueId()));
		
		benchmarkValue.setBenchmarkId(ControllerUtils.setValue(benchmarkValue.getBenchmarkId(), benchmarkValueDTO.getBenchmarkId()));
		
		benchmarkValue.setValueDate(ControllerUtils.setValue(benchmarkValue.getValueDate(), benchmarkValueDTO.getValueDate()));
		
		benchmarkValue.setValue(ControllerUtils.setValue(benchmarkValue.getValue(), benchmarkValueDTO.getValue()));
		
		benchmarkValue.setDailyReturn(ControllerUtils.setValue(benchmarkValue.getDailyReturn(), benchmarkValueDTO.getDailyReturn()));

        benchmarkValue = benchmarkValueDao.save(benchmarkValue);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BenchmarkValueDTO getBenchmarkValueDTOById(Integer benchmarkValueId) {
	
		BenchmarkValue benchmarkValue = benchmarkValueDao.getById(benchmarkValueId);
		
		BenchmarkValueConvertCriteriaDTO convertCriteria = new BenchmarkValueConvertCriteriaDTO();
		return(this.convertBenchmarkValueToBenchmarkValueDTO(benchmarkValue,convertCriteria));
	}

}
