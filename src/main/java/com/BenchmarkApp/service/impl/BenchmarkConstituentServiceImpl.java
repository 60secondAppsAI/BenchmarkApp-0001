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
import com.BenchmarkApp.dao.BenchmarkConstituentDAO;
import com.BenchmarkApp.domain.BenchmarkConstituent;
import com.BenchmarkApp.dto.BenchmarkConstituentDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentSearchDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentPageDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentConvertCriteriaDTO;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import com.BenchmarkApp.service.BenchmarkConstituentService;
import com.BenchmarkApp.util.ControllerUtils;


@Service
public class BenchmarkConstituentServiceImpl extends GenericServiceImpl<BenchmarkConstituent, Integer> implements BenchmarkConstituentService {

    private final static Logger logger = LoggerFactory.getLogger(BenchmarkConstituentServiceImpl.class);

	@Autowired
	BenchmarkConstituentDAO benchmarkConstituentDao;

	

	@Override
	public GenericDAO<BenchmarkConstituent, Integer> getDAO() {
		return (GenericDAO<BenchmarkConstituent, Integer>) benchmarkConstituentDao;
	}
	
	public List<BenchmarkConstituent> findAll () {
		List<BenchmarkConstituent> benchmarkConstituents = benchmarkConstituentDao.findAll();
		
		return benchmarkConstituents;	
		
	}

	public ResultDTO addBenchmarkConstituent(BenchmarkConstituentDTO benchmarkConstituentDTO, RequestDTO requestDTO) {

		BenchmarkConstituent benchmarkConstituent = new BenchmarkConstituent();

		benchmarkConstituent.setBenchmarkConstituentId(benchmarkConstituentDTO.getBenchmarkConstituentId());

		benchmarkConstituent.setBenchmarkId(benchmarkConstituentDTO.getBenchmarkId());

		benchmarkConstituent.setInstrumentId(benchmarkConstituentDTO.getInstrumentId());

		benchmarkConstituent.setWeight(benchmarkConstituentDTO.getWeight());

		benchmarkConstituent.setEffectiveDate(benchmarkConstituentDTO.getEffectiveDate());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		benchmarkConstituent = benchmarkConstituentDao.save(benchmarkConstituent);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<BenchmarkConstituent> getAllBenchmarkConstituents(Pageable pageable) {
		return benchmarkConstituentDao.findAll(pageable);
	}

	public Page<BenchmarkConstituent> getAllBenchmarkConstituents(Specification<BenchmarkConstituent> spec, Pageable pageable) {
		return benchmarkConstituentDao.findAll(spec, pageable);
	}

	public ResponseEntity<BenchmarkConstituentPageDTO> getBenchmarkConstituents(BenchmarkConstituentSearchDTO benchmarkConstituentSearchDTO) {
	
			Integer benchmarkConstituentId = benchmarkConstituentSearchDTO.getBenchmarkConstituentId(); 
 			Integer benchmarkId = benchmarkConstituentSearchDTO.getBenchmarkId(); 
 			Integer instrumentId = benchmarkConstituentSearchDTO.getInstrumentId(); 
  			Date effectiveDate = benchmarkConstituentSearchDTO.getEffectiveDate();
 			String sortBy = benchmarkConstituentSearchDTO.getSortBy();
			String sortOrder = benchmarkConstituentSearchDTO.getSortOrder();
			String searchQuery = benchmarkConstituentSearchDTO.getSearchQuery();
			Integer page = benchmarkConstituentSearchDTO.getPage();
			Integer size = benchmarkConstituentSearchDTO.getSize();

	        Specification<BenchmarkConstituent> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, benchmarkConstituentId, "benchmarkConstituentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, benchmarkId, "benchmarkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, effectiveDate, "effectiveDate");
			

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

		Page<BenchmarkConstituent> benchmarkConstituents = this.getAllBenchmarkConstituents(spec, pageable);
		
		//System.out.println(String.valueOf(benchmarkConstituents.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(benchmarkConstituents.getTotalPages()));
		
		List<BenchmarkConstituent> benchmarkConstituentsList = benchmarkConstituents.getContent();
		
		BenchmarkConstituentConvertCriteriaDTO convertCriteria = new BenchmarkConstituentConvertCriteriaDTO();
		List<BenchmarkConstituentDTO> benchmarkConstituentDTOs = this.convertBenchmarkConstituentsToBenchmarkConstituentDTOs(benchmarkConstituentsList,convertCriteria);
		
		BenchmarkConstituentPageDTO benchmarkConstituentPageDTO = new BenchmarkConstituentPageDTO();
		benchmarkConstituentPageDTO.setBenchmarkConstituents(benchmarkConstituentDTOs);
		benchmarkConstituentPageDTO.setTotalElements(benchmarkConstituents.getTotalElements());
		return ResponseEntity.ok(benchmarkConstituentPageDTO);
	}

	public List<BenchmarkConstituentDTO> convertBenchmarkConstituentsToBenchmarkConstituentDTOs(List<BenchmarkConstituent> benchmarkConstituents, BenchmarkConstituentConvertCriteriaDTO convertCriteria) {
		
		List<BenchmarkConstituentDTO> benchmarkConstituentDTOs = new ArrayList<BenchmarkConstituentDTO>();
		
		for (BenchmarkConstituent benchmarkConstituent : benchmarkConstituents) {
			benchmarkConstituentDTOs.add(convertBenchmarkConstituentToBenchmarkConstituentDTO(benchmarkConstituent,convertCriteria));
		}
		
		return benchmarkConstituentDTOs;

	}
	
	public BenchmarkConstituentDTO convertBenchmarkConstituentToBenchmarkConstituentDTO(BenchmarkConstituent benchmarkConstituent, BenchmarkConstituentConvertCriteriaDTO convertCriteria) {
		
		BenchmarkConstituentDTO benchmarkConstituentDTO = new BenchmarkConstituentDTO();

		benchmarkConstituentDTO.setBenchmarkConstituentId(benchmarkConstituent.getBenchmarkConstituentId());

		benchmarkConstituentDTO.setBenchmarkId(benchmarkConstituent.getBenchmarkId());

		benchmarkConstituentDTO.setInstrumentId(benchmarkConstituent.getInstrumentId());

		benchmarkConstituentDTO.setWeight(benchmarkConstituent.getWeight());

		benchmarkConstituentDTO.setEffectiveDate(benchmarkConstituent.getEffectiveDate());
		
		return benchmarkConstituentDTO;
	}

	public ResultDTO updateBenchmarkConstituent(BenchmarkConstituentDTO benchmarkConstituentDTO, RequestDTO requestDTO) {
		
		BenchmarkConstituent benchmarkConstituent = benchmarkConstituentDao.getById(benchmarkConstituentDTO.getBenchmarkConstituentId());
		
		benchmarkConstituent.setBenchmarkConstituentId(ControllerUtils.setValue(benchmarkConstituent.getBenchmarkConstituentId(), benchmarkConstituentDTO.getBenchmarkConstituentId()));
		
		benchmarkConstituent.setBenchmarkId(ControllerUtils.setValue(benchmarkConstituent.getBenchmarkId(), benchmarkConstituentDTO.getBenchmarkId()));
		
		benchmarkConstituent.setInstrumentId(ControllerUtils.setValue(benchmarkConstituent.getInstrumentId(), benchmarkConstituentDTO.getInstrumentId()));
		
		benchmarkConstituent.setWeight(ControllerUtils.setValue(benchmarkConstituent.getWeight(), benchmarkConstituentDTO.getWeight()));
		
		benchmarkConstituent.setEffectiveDate(ControllerUtils.setValue(benchmarkConstituent.getEffectiveDate(), benchmarkConstituentDTO.getEffectiveDate()));

        benchmarkConstituent = benchmarkConstituentDao.save(benchmarkConstituent);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BenchmarkConstituentDTO getBenchmarkConstituentDTOById(Integer benchmarkConstituentId) {
	
		BenchmarkConstituent benchmarkConstituent = benchmarkConstituentDao.getById(benchmarkConstituentId);
		
		BenchmarkConstituentConvertCriteriaDTO convertCriteria = new BenchmarkConstituentConvertCriteriaDTO();
		return(this.convertBenchmarkConstituentToBenchmarkConstituentDTO(benchmarkConstituent,convertCriteria));
	}

}
