package com.BenchmarkApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.BenchmarkApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.BenchmarkApp.domain.Benchmark;
import com.BenchmarkApp.dto.BenchmarkDTO;
import com.BenchmarkApp.dto.BenchmarkSearchDTO;
import com.BenchmarkApp.dto.BenchmarkPageDTO;
import com.BenchmarkApp.service.BenchmarkService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/benchmark")
@RestController
public class BenchmarkController {

	private final static Logger logger = LoggerFactory.getLogger(BenchmarkController.class);

	@Autowired
	BenchmarkService benchmarkService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Benchmark> getAll() {

		List<Benchmark> benchmarks = benchmarkService.findAll();
		
		return benchmarks;	
	}

	@GetMapping(value = "/{benchmarkId}")
	@ResponseBody
	public BenchmarkDTO getBenchmark(@PathVariable Integer benchmarkId) {
		
		return (benchmarkService.getBenchmarkDTOById(benchmarkId));
	}

 	@RequestMapping(value = "/addBenchmark", method = RequestMethod.POST)
	public ResponseEntity<?> addBenchmark(@RequestBody BenchmarkDTO benchmarkDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkService.addBenchmark(benchmarkDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/benchmarks")
	public ResponseEntity<BenchmarkPageDTO> getBenchmarks(BenchmarkSearchDTO benchmarkSearchDTO) {
 
		return benchmarkService.getBenchmarks(benchmarkSearchDTO);
	}	

	@RequestMapping(value = "/updateBenchmark", method = RequestMethod.POST)
	public ResponseEntity<?> updateBenchmark(@RequestBody BenchmarkDTO benchmarkDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkService.updateBenchmark(benchmarkDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
