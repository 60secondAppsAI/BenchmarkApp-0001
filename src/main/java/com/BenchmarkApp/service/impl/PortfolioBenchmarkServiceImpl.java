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
import com.BenchmarkApp.dao.PortfolioBenchmarkDAO;
import com.BenchmarkApp.domain.PortfolioBenchmark;
import com.BenchmarkApp.dto.PortfolioBenchmarkDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkSearchDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkPageDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkConvertCriteriaDTO;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import com.BenchmarkApp.service.PortfolioBenchmarkService;
import com.BenchmarkApp.util.ControllerUtils;


@Service
public class PortfolioBenchmarkServiceImpl extends GenericServiceImpl<PortfolioBenchmark, Integer> implements PortfolioBenchmarkService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioBenchmarkServiceImpl.class);

	@Autowired
	PortfolioBenchmarkDAO portfolioBenchmarkDao;

	

	@Override
	public GenericDAO<PortfolioBenchmark, Integer> getDAO() {
		return (GenericDAO<PortfolioBenchmark, Integer>) portfolioBenchmarkDao;
	}
	
	public List<PortfolioBenchmark> findAll () {
		List<PortfolioBenchmark> portfolioBenchmarks = portfolioBenchmarkDao.findAll();
		
		return portfolioBenchmarks;	
		
	}

	public ResultDTO addPortfolioBenchmark(PortfolioBenchmarkDTO portfolioBenchmarkDTO, RequestDTO requestDTO) {

		PortfolioBenchmark portfolioBenchmark = new PortfolioBenchmark();

		portfolioBenchmark.setPortfolioBenchmarkId(portfolioBenchmarkDTO.getPortfolioBenchmarkId());

		portfolioBenchmark.setPortfolioId(portfolioBenchmarkDTO.getPortfolioId());

		portfolioBenchmark.setBenchmarkId(portfolioBenchmarkDTO.getBenchmarkId());

		portfolioBenchmark.setEffectiveDate(portfolioBenchmarkDTO.getEffectiveDate());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolioBenchmark = portfolioBenchmarkDao.save(portfolioBenchmark);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PortfolioBenchmark> getAllPortfolioBenchmarks(Pageable pageable) {
		return portfolioBenchmarkDao.findAll(pageable);
	}

	public Page<PortfolioBenchmark> getAllPortfolioBenchmarks(Specification<PortfolioBenchmark> spec, Pageable pageable) {
		return portfolioBenchmarkDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioBenchmarkPageDTO> getPortfolioBenchmarks(PortfolioBenchmarkSearchDTO portfolioBenchmarkSearchDTO) {
	
			Integer portfolioBenchmarkId = portfolioBenchmarkSearchDTO.getPortfolioBenchmarkId(); 
 			Integer portfolioId = portfolioBenchmarkSearchDTO.getPortfolioId(); 
 			Integer benchmarkId = portfolioBenchmarkSearchDTO.getBenchmarkId(); 
 			Date effectiveDate = portfolioBenchmarkSearchDTO.getEffectiveDate();
 			String sortBy = portfolioBenchmarkSearchDTO.getSortBy();
			String sortOrder = portfolioBenchmarkSearchDTO.getSortOrder();
			String searchQuery = portfolioBenchmarkSearchDTO.getSearchQuery();
			Integer page = portfolioBenchmarkSearchDTO.getPage();
			Integer size = portfolioBenchmarkSearchDTO.getSize();

	        Specification<PortfolioBenchmark> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioBenchmarkId, "portfolioBenchmarkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, benchmarkId, "benchmarkId"); 
			
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

		Page<PortfolioBenchmark> portfolioBenchmarks = this.getAllPortfolioBenchmarks(spec, pageable);
		
		//System.out.println(String.valueOf(portfolioBenchmarks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolioBenchmarks.getTotalPages()));
		
		List<PortfolioBenchmark> portfolioBenchmarksList = portfolioBenchmarks.getContent();
		
		PortfolioBenchmarkConvertCriteriaDTO convertCriteria = new PortfolioBenchmarkConvertCriteriaDTO();
		List<PortfolioBenchmarkDTO> portfolioBenchmarkDTOs = this.convertPortfolioBenchmarksToPortfolioBenchmarkDTOs(portfolioBenchmarksList,convertCriteria);
		
		PortfolioBenchmarkPageDTO portfolioBenchmarkPageDTO = new PortfolioBenchmarkPageDTO();
		portfolioBenchmarkPageDTO.setPortfolioBenchmarks(portfolioBenchmarkDTOs);
		portfolioBenchmarkPageDTO.setTotalElements(portfolioBenchmarks.getTotalElements());
		return ResponseEntity.ok(portfolioBenchmarkPageDTO);
	}

	public List<PortfolioBenchmarkDTO> convertPortfolioBenchmarksToPortfolioBenchmarkDTOs(List<PortfolioBenchmark> portfolioBenchmarks, PortfolioBenchmarkConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioBenchmarkDTO> portfolioBenchmarkDTOs = new ArrayList<PortfolioBenchmarkDTO>();
		
		for (PortfolioBenchmark portfolioBenchmark : portfolioBenchmarks) {
			portfolioBenchmarkDTOs.add(convertPortfolioBenchmarkToPortfolioBenchmarkDTO(portfolioBenchmark,convertCriteria));
		}
		
		return portfolioBenchmarkDTOs;

	}
	
	public PortfolioBenchmarkDTO convertPortfolioBenchmarkToPortfolioBenchmarkDTO(PortfolioBenchmark portfolioBenchmark, PortfolioBenchmarkConvertCriteriaDTO convertCriteria) {
		
		PortfolioBenchmarkDTO portfolioBenchmarkDTO = new PortfolioBenchmarkDTO();

		portfolioBenchmarkDTO.setPortfolioBenchmarkId(portfolioBenchmark.getPortfolioBenchmarkId());

		portfolioBenchmarkDTO.setPortfolioId(portfolioBenchmark.getPortfolioId());

		portfolioBenchmarkDTO.setBenchmarkId(portfolioBenchmark.getBenchmarkId());

		portfolioBenchmarkDTO.setEffectiveDate(portfolioBenchmark.getEffectiveDate());
		
		return portfolioBenchmarkDTO;
	}

	public ResultDTO updatePortfolioBenchmark(PortfolioBenchmarkDTO portfolioBenchmarkDTO, RequestDTO requestDTO) {
		
		PortfolioBenchmark portfolioBenchmark = portfolioBenchmarkDao.getById(portfolioBenchmarkDTO.getPortfolioBenchmarkId());
		
		portfolioBenchmark.setPortfolioBenchmarkId(ControllerUtils.setValue(portfolioBenchmark.getPortfolioBenchmarkId(), portfolioBenchmarkDTO.getPortfolioBenchmarkId()));
		
		portfolioBenchmark.setPortfolioId(ControllerUtils.setValue(portfolioBenchmark.getPortfolioId(), portfolioBenchmarkDTO.getPortfolioId()));
		
		portfolioBenchmark.setBenchmarkId(ControllerUtils.setValue(portfolioBenchmark.getBenchmarkId(), portfolioBenchmarkDTO.getBenchmarkId()));
		
		portfolioBenchmark.setEffectiveDate(ControllerUtils.setValue(portfolioBenchmark.getEffectiveDate(), portfolioBenchmarkDTO.getEffectiveDate()));

        portfolioBenchmark = portfolioBenchmarkDao.save(portfolioBenchmark);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioBenchmarkDTO getPortfolioBenchmarkDTOById(Integer portfolioBenchmarkId) {
	
		PortfolioBenchmark portfolioBenchmark = portfolioBenchmarkDao.getById(portfolioBenchmarkId);
		
		PortfolioBenchmarkConvertCriteriaDTO convertCriteria = new PortfolioBenchmarkConvertCriteriaDTO();
		return(this.convertPortfolioBenchmarkToPortfolioBenchmarkDTO(portfolioBenchmark,convertCriteria));
	}

}
