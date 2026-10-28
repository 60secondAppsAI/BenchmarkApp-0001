package com.BenchmarkApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePortfolioBenchmarkDTO {

	private Integer ownerId;

	private Integer portfolioBenchmarkId;

	private Integer portfolioBenchmarkStatus;
	
	private Integer nextOrPrevious;
}

