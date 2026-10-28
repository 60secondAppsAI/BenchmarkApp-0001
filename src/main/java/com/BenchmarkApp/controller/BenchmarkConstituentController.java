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

import com.BenchmarkApp.domain.BenchmarkConstituent;
import com.BenchmarkApp.dto.BenchmarkConstituentDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentSearchDTO;
import com.BenchmarkApp.dto.BenchmarkConstituentPageDTO;
import com.BenchmarkApp.service.BenchmarkConstituentService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/benchmarkConstituent")
@RestController
public class BenchmarkConstituentController {

	private final static Logger logger = LoggerFactory.getLogger(BenchmarkConstituentController.class);

	@Autowired
	BenchmarkConstituentService benchmarkConstituentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<BenchmarkConstituent> getAll() {

		List<BenchmarkConstituent> benchmarkConstituents = benchmarkConstituentService.findAll();
		
		return benchmarkConstituents;	
	}

	@GetMapping(value = "/{benchmarkConstituentId}")
	@ResponseBody
	public BenchmarkConstituentDTO getBenchmarkConstituent(@PathVariable Integer benchmarkConstituentId) {
		
		return (benchmarkConstituentService.getBenchmarkConstituentDTOById(benchmarkConstituentId));
	}

 	@RequestMapping(value = "/addBenchmarkConstituent", method = RequestMethod.POST)
	public ResponseEntity<?> addBenchmarkConstituent(@RequestBody BenchmarkConstituentDTO benchmarkConstituentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkConstituentService.addBenchmarkConstituent(benchmarkConstituentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/benchmarkConstituents")
	public ResponseEntity<BenchmarkConstituentPageDTO> getBenchmarkConstituents(BenchmarkConstituentSearchDTO benchmarkConstituentSearchDTO) {
 
		return benchmarkConstituentService.getBenchmarkConstituents(benchmarkConstituentSearchDTO);
	}	

	@RequestMapping(value = "/updateBenchmarkConstituent", method = RequestMethod.POST)
	public ResponseEntity<?> updateBenchmarkConstituent(@RequestBody BenchmarkConstituentDTO benchmarkConstituentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = benchmarkConstituentService.updateBenchmarkConstituent(benchmarkConstituentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
