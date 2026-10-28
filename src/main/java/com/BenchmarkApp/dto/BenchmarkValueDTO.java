package com.BenchmarkApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BenchmarkValueDTO {

	private Integer benchmarkValueId;

	private Integer benchmarkId;

	private Date valueDate;

	private Double value;

	private Double dailyReturn;






}
