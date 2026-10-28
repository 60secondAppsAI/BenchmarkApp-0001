package com.BenchmarkApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BenchmarkApp.domain.BenchmarkValue;
import com.BenchmarkApp.dto.BenchmarkValueDTO;
import com.BenchmarkApp.dto.BenchmarkValueSearchDTO;
import com.BenchmarkApp.dto.BenchmarkValuePageDTO;
import com.BenchmarkApp.dto.BenchmarkValueConvertCriteriaDTO;
import com.BenchmarkApp.service.GenericService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BenchmarkValueService extends GenericService<BenchmarkValue, Integer> {

	List<BenchmarkValue> findAll();

	ResultDTO addBenchmarkValue(BenchmarkValueDTO benchmarkValueDTO, RequestDTO requestDTO);

	ResultDTO updateBenchmarkValue(BenchmarkValueDTO benchmarkValueDTO, RequestDTO requestDTO);

    Page<BenchmarkValue> getAllBenchmarkValues(Pageable pageable);

    Page<BenchmarkValue> getAllBenchmarkValues(Specification<BenchmarkValue> spec, Pageable pageable);

	ResponseEntity<BenchmarkValuePageDTO> getBenchmarkValues(BenchmarkValueSearchDTO benchmarkValueSearchDTO);
	
	List<BenchmarkValueDTO> convertBenchmarkValuesToBenchmarkValueDTOs(List<BenchmarkValue> benchmarkValues, BenchmarkValueConvertCriteriaDTO convertCriteria);

	BenchmarkValueDTO getBenchmarkValueDTOById(Integer benchmarkValueId);



	
}
