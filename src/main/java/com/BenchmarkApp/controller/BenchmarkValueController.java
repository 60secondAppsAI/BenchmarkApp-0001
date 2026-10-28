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

import com.BenchmarkApp.domain.BenchmarkValue;
import com.BenchmarkApp.dto.BenchmarkValueDTO;
import com.BenchmarkApp.dto.BenchmarkValueSearchDTO;
import com.BenchmarkApp.dto.BenchmarkValuePageDTO;
import com.BenchmarkApp.service.BenchmarkValueService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/benchmarkValue")
@RestController
public class BenchmarkValueController {

	private final static Logger logger = LoggerFactory.getLogger(BenchmarkValueController.class);

	@Autowired
	BenchmarkValueService benchmarkValueService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<BenchmarkValue> getAll() {

		List<BenchmarkValue> benchmarkValues = benchmarkValueService.findAll();
		
		return benchmarkValues;	
	}

	@GetMapping(value = "/{benchmarkValueId}")
	@ResponseBody
	public BenchmarkValueDTO getBenchmarkValue(@PathVariable Integer benchmarkValueId) {
		
		return (benchmarkValueService.getBenchmarkValueDTOById(benchmarkValueId));
	}

 	@RequestMapping(value = "/addBenchmarkValue", method = RequestMethod.POST)
	public ResponseEntity<?> addBenchmarkValue(@RequestBody BenchmarkValueDTO benchmarkValueDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkValueService.addBenchmarkValue(benchmarkValueDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/benchmarkValues")
	public ResponseEntity<BenchmarkValuePageDTO> getBenchmarkValues(BenchmarkValueSearchDTO benchmarkValueSearchDTO) {
 
		return benchmarkValueService.getBenchmarkValues(benchmarkValueSearchDTO);
	}	

	@RequestMapping(value = "/updateBenchmarkValue", method = RequestMethod.POST)
	public ResponseEntity<?> updateBenchmarkValue(@RequestBody BenchmarkValueDTO benchmarkValueDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkValueService.updateBenchmarkValue(benchmarkValueDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
