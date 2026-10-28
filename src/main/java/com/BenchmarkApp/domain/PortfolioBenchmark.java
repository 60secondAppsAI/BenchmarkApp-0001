package com.BenchmarkApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="portfolio_benchmarks")
@Getter @Setter @NoArgsConstructor
public class PortfolioBenchmark {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="portfolio_benchmark_id")
	private Integer portfolioBenchmarkId;
    
  	@Column(name="portfolio_id")
	private Integer portfolioId;
    
  	@Column(name="benchmark_id")
	private Integer benchmarkId;
    
  	@Column(name="effective_date")
	private Date effectiveDate;
    

}
