package com.BenchmarkApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BenchmarkConstituentDTO {

	private Integer benchmarkConstituentId;

	private Integer benchmarkId;

	private Integer instrumentId;

	private Double weight;

	private Date effectiveDate;






}
