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
@Table(name="benchmarks")
@Getter @Setter @NoArgsConstructor
public class Benchmark {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="benchmark_id")
	private Integer benchmarkId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="symbol")
	private String symbol;
    
  	@Column(name="benchmark_type")
	private String benchmarkType;
    
  	@Column(name="currency")
	private String currency;
    

}
