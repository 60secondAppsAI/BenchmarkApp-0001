package com.BenchmarkApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BenchmarkApp.domain.PortfolioBenchmark;
import com.BenchmarkApp.dto.PortfolioBenchmarkDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkSearchDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkPageDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkConvertCriteriaDTO;
import com.BenchmarkApp.service.GenericService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioBenchmarkService extends GenericService<PortfolioBenchmark, Integer> {

	List<PortfolioBenchmark> findAll();

	ResultDTO addPortfolioBenchmark(PortfolioBenchmarkDTO portfolioBenchmarkDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolioBenchmark(PortfolioBenchmarkDTO portfolioBenchmarkDTO, RequestDTO requestDTO);

    Page<PortfolioBenchmark> getAllPortfolioBenchmarks(Pageable pageable);

    Page<PortfolioBenchmark> getAllPortfolioBenchmarks(Specification<PortfolioBenchmark> spec, Pageable pageable);

	ResponseEntity<PortfolioBenchmarkPageDTO> getPortfolioBenchmarks(PortfolioBenchmarkSearchDTO portfolioBenchmarkSearchDTO);
	
	List<PortfolioBenchmarkDTO> convertPortfolioBenchmarksToPortfolioBenchmarkDTOs(List<PortfolioBenchmark> portfolioBenchmarks, PortfolioBenchmarkConvertCriteriaDTO convertCriteria);

	PortfolioBenchmarkDTO getPortfolioBenchmarkDTOById(Integer portfolioBenchmarkId);



	
}
