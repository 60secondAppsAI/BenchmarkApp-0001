package com.BenchmarkApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBenchmarkDTO {

	private Integer ownerId;

	private Integer benchmarkId;

	private Integer benchmarkStatus;
	
	private Integer nextOrPrevious;
}

