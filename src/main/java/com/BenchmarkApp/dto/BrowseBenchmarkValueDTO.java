package com.BenchmarkApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBenchmarkValueDTO {

	private Integer ownerId;

	private Integer benchmarkValueId;

	private Integer benchmarkValueStatus;
	
	private Integer nextOrPrevious;
}

