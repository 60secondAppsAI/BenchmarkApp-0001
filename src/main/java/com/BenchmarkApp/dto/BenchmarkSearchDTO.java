package com.BenchmarkApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BenchmarkSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer benchmarkId;
	
	private String name;
	
	private String symbol;
	
	private String benchmarkType;
	
	private String currency;
	
}
