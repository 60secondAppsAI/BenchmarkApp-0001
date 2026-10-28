package com.BenchmarkApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BenchmarkApp.domain.BenchmarkConstituent;
import com.BenchmarkApp.dto.BenchmarkConstituentDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentSearchDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentPageDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentConvertCriteriaDTO;
import com.BenchmarkApp.service.GenericService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BenchmarkConstituentService extends GenericService<BenchmarkConstituent, Integer> {

	List<BenchmarkConstituent> findAll();

	ResultDTO addBenchmarkConstituent(BenchmarkConstituentDTO benchmarkConstituentDTO, RequestDTO requestDTO);

	ResultDTO updateBenchmarkConstituent(BenchmarkConstituentDTO benchmarkConstituentDTO, RequestDTO requestDTO);

    Page<BenchmarkConstituent> getAllBenchmarkConstituents(Pageable pageable);

    Page<BenchmarkConstituent> getAllBenchmarkConstituents(Specification<BenchmarkConstituent> spec, Pageable pageable);

	ResponseEntity<BenchmarkConstituentPageDTO> getBenchmarkConstituents(BenchmarkConstituentSearchDTO benchmarkConstituentSearchDTO);
	
	List<BenchmarkConstituentDTO> convertBenchmarkConstituentsToBenchmarkConstituentDTOs(List<BenchmarkConstituent> benchmarkConstituents, BenchmarkConstituentConvertCriteriaDTO convertCriteria);

	BenchmarkConstituentDTO getBenchmarkConstituentDTOById(Integer benchmarkConstituentId);



	
}
