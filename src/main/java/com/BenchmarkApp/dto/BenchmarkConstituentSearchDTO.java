package com.BenchmarkApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BenchmarkConstituentSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer benchmarkConstituentId;
	
	private Integer benchmarkId;
	
	private Integer instrumentId;
	
	private Double weight;
	
	private Date effectiveDate;
	
}
