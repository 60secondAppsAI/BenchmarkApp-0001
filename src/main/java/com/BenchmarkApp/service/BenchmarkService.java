package com.BenchmarkApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BenchmarkApp.domain.Benchmark;
import com.BenchmarkApp.dto.BenchmarkDTO;
import com.BenchmarkApp.dto.BenchmarkSearchDTO;
import com.BenchmarkApp.dto.BenchmarkPageDTO;
import com.BenchmarkApp.dto.BenchmarkConvertCriteriaDTO;
import com.BenchmarkApp.service.GenericService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BenchmarkService extends GenericService<Benchmark, Integer> {

	List<Benchmark> findAll();

	ResultDTO addBenchmark(BenchmarkDTO benchmarkDTO, RequestDTO requestDTO);

	ResultDTO updateBenchmark(BenchmarkDTO benchmarkDTO, RequestDTO requestDTO);

    Page<Benchmark> getAllBenchmarks(Pageable pageable);

    Page<Benchmark> getAllBenchmarks(Specification<Benchmark> spec, Pageable pageable);

	ResponseEntity<BenchmarkPageDTO> getBenchmarks(BenchmarkSearchDTO benchmarkSearchDTO);
	
	List<BenchmarkDTO> convertBenchmarksToBenchmarkDTOs(List<Benchmark> benchmarks, BenchmarkConvertCriteriaDTO convertCriteria);

	BenchmarkDTO getBenchmarkDTOById(Integer benchmarkId);



	
}
