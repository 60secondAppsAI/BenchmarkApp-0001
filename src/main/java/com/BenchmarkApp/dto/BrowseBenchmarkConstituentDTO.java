package com.BenchmarkApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBenchmarkConstituentDTO {

	private Integer ownerId;

	private Integer benchmarkConstituentId;

	private Integer benchmarkConstituentStatus;
	
	private Integer nextOrPrevious;
}

