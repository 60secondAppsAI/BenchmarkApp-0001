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

import com.BenchmarkApp.domain.PortfolioBenchmark;
import com.BenchmarkApp.dto.PortfolioBenchmarkDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkSearchDTO;
import com.BenchmarkApp.dto.PortfolioBenchmarkPageDTO;
import com.BenchmarkApp.service.PortfolioBenchmarkService;
import com.BenchmarkApp.dto.common.RequestDTO;
import com.BenchmarkApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/portfolioBenchmark")
@RestController
public class PortfolioBenchmarkController {

	private final static Logger logger = LoggerFactory.getLogger(PortfolioBenchmarkController.class);

	@Autowired
	PortfolioBenchmarkService portfolioBenchmarkService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PortfolioBenchmark> getAll() {

		List<PortfolioBenchmark> portfolioBenchmarks = portfolioBenchmarkService.findAll();
		
		return portfolioBenchmarks;	
	}

	@GetMapping(value = "/{portfolioBenchmarkId}")
	@ResponseBody
	public PortfolioBenchmarkDTO getPortfolioBenchmark(@PathVariable Integer portfolioBenchmarkId) {
		
		return (portfolioBenchmarkService.getPortfolioBenchmarkDTOById(portfolioBenchmarkId));
	}

 	@RequestMapping(value = "/addPortfolioBenchmark", method = RequestMethod.POST)
	public ResponseEntity<?> addPortfolioBenchmark(@RequestBody PortfolioBenchmarkDTO portfolioBenchmarkDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioBenchmarkService.addPortfolioBenchmark(portfolioBenchmarkDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/portfolioBenchmarks")
	public ResponseEntity<PortfolioBenchmarkPageDTO> getPortfolioBenchmarks(PortfolioBenchmarkSearchDTO portfolioBenchmarkSearchDTO) {
 
		return portfolioBenchmarkService.getPortfolioBenchmarks(portfolioBenchmarkSearchDTO);
	}	

	@RequestMapping(value = "/updatePortfolioBenchmark", method = RequestMethod.POST)
	public ResponseEntity<?> updatePortfolioBenchmark(@RequestBody PortfolioBenchmarkDTO portfolioBenchmarkDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioBenchmarkService.updatePortfolioBenchmark(portfolioBenchmarkDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
