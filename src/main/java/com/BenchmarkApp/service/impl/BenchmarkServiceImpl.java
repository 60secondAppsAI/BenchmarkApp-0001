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
import com.BenchmarkApp.dao.BenchmarkDAO;
import com.BenchmarkApp.domain.Benchmark;
import com.BenchmarkApp.dto.BenchmarkDTO;
import com.BenchmarkApp.dto.BenchmarkSearchDTO;
import com.BenchmarkApp.dto.BenchmarkPageDTO;
import com.BenchmarkApp.dto.BenchmarkConvertCriteriaDTO;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import com.BenchmarkApp.service.BenchmarkService;
import com.BenchmarkApp.util.ControllerUtils;


@Service
public class BenchmarkServiceImpl extends GenericServiceImpl<Benchmark, Integer> implements BenchmarkService {

    private final static Logger logger = LoggerFactory.getLogger(BenchmarkServiceImpl.class);

	@Autowired
	BenchmarkDAO benchmarkDao;

	

	@Override
	public GenericDAO<Benchmark, Integer> getDAO() {
		return (GenericDAO<Benchmark, Integer>) benchmarkDao;
	}
	
	public List<Benchmark> findAll () {
		List<Benchmark> benchmarks = benchmarkDao.findAll();
		
		return benchmarks;	
		
	}

	public ResultDTO addBenchmark(BenchmarkDTO benchmarkDTO, RequestDTO requestDTO) {

		Benchmark benchmark = new Benchmark();

		benchmark.setBenchmarkId(benchmarkDTO.getBenchmarkId());

		benchmark.setName(benchmarkDTO.getName());

		benchmark.setSymbol(benchmarkDTO.getSymbol());

		benchmark.setBenchmarkType(benchmarkDTO.getBenchmarkType());

		benchmark.setCurrency(benchmarkDTO.getCurrency());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		benchmark = benchmarkDao.save(benchmark);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Benchmark> getAllBenchmarks(Pageable pageable) {
		return benchmarkDao.findAll(pageable);
	}

	public Page<Benchmark> getAllBenchmarks(Specification<Benchmark> spec, Pageable pageable) {
		return benchmarkDao.findAll(spec, pageable);
	}

	public ResponseEntity<BenchmarkPageDTO> getBenchmarks(BenchmarkSearchDTO benchmarkSearchDTO) {
	
			Integer benchmarkId = benchmarkSearchDTO.getBenchmarkId(); 
 			String name = benchmarkSearchDTO.getName(); 
 			String symbol = benchmarkSearchDTO.getSymbol(); 
 			String benchmarkType = benchmarkSearchDTO.getBenchmarkType(); 
 			String currency = benchmarkSearchDTO.getCurrency(); 
 			String sortBy = benchmarkSearchDTO.getSortBy();
			String sortOrder = benchmarkSearchDTO.getSortOrder();
			String searchQuery = benchmarkSearchDTO.getSearchQuery();
			Integer page = benchmarkSearchDTO.getPage();
			Integer size = benchmarkSearchDTO.getSize();

	        Specification<Benchmark> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, benchmarkId, "benchmarkId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, symbol, "symbol"); 
			
			spec = ControllerUtils.andIfNecessary(spec, benchmarkType, "benchmarkType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, currency, "currency"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("symbol")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("benchmarkType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("currency")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Benchmark> benchmarks = this.getAllBenchmarks(spec, pageable);
		
		//System.out.println(String.valueOf(benchmarks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(benchmarks.getTotalPages()));
		
		List<Benchmark> benchmarksList = benchmarks.getContent();
		
		BenchmarkConvertCriteriaDTO convertCriteria = new BenchmarkConvertCriteriaDTO();
		List<BenchmarkDTO> benchmarkDTOs = this.convertBenchmarksToBenchmarkDTOs(benchmarksList,convertCriteria);
		
		BenchmarkPageDTO benchmarkPageDTO = new BenchmarkPageDTO();
		benchmarkPageDTO.setBenchmarks(benchmarkDTOs);
		benchmarkPageDTO.setTotalElements(benchmarks.getTotalElements());
		return ResponseEntity.ok(benchmarkPageDTO);
	}

	public List<BenchmarkDTO> convertBenchmarksToBenchmarkDTOs(List<Benchmark> benchmarks, BenchmarkConvertCriteriaDTO convertCriteria) {
		
		List<BenchmarkDTO> benchmarkDTOs = new ArrayList<BenchmarkDTO>();
		
		for (Benchmark benchmark : benchmarks) {
			benchmarkDTOs.add(convertBenchmarkToBenchmarkDTO(benchmark,convertCriteria));
		}
		
		return benchmarkDTOs;

	}
	
	public BenchmarkDTO convertBenchmarkToBenchmarkDTO(Benchmark benchmark, BenchmarkConvertCriteriaDTO convertCriteria) {
		
		BenchmarkDTO benchmarkDTO = new BenchmarkDTO();

		benchmarkDTO.setBenchmarkId(benchmark.getBenchmarkId());

		benchmarkDTO.setName(benchmark.getName());

		benchmarkDTO.setSymbol(benchmark.getSymbol());

		benchmarkDTO.setBenchmarkType(benchmark.getBenchmarkType());

		benchmarkDTO.setCurrency(benchmark.getCurrency());
		
		return benchmarkDTO;
	}

	public ResultDTO updateBenchmark(BenchmarkDTO benchmarkDTO, RequestDTO requestDTO) {
		
		Benchmark benchmark = benchmarkDao.getById(benchmarkDTO.getBenchmarkId());
		
		benchmark.setBenchmarkId(ControllerUtils.setValue(benchmark.getBenchmarkId(), benchmarkDTO.getBenchmarkId()));
		
		benchmark.setName(ControllerUtils.setValue(benchmark.getName(), benchmarkDTO.getName()));
		
		benchmark.setSymbol(ControllerUtils.setValue(benchmark.getSymbol(), benchmarkDTO.getSymbol()));
		
		benchmark.setBenchmarkType(ControllerUtils.setValue(benchmark.getBenchmarkType(), benchmarkDTO.getBenchmarkType()));
		
		benchmark.setCurrency(ControllerUtils.setValue(benchmark.getCurrency(), benchmarkDTO.getCurrency()));

        benchmark = benchmarkDao.save(benchmark);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BenchmarkDTO getBenchmarkDTOById(Integer benchmarkId) {
	
		Benchmark benchmark = benchmarkDao.getById(benchmarkId);
		
		BenchmarkConvertCriteriaDTO convertCriteria = new BenchmarkConvertCriteriaDTO();
		return(this.convertBenchmarkToBenchmarkDTO(benchmark,convertCriteria));
	}

}
