CREATE DATABASE IF NOT EXISTS BenchmarkApp;

CREATE TABLE BenchmarkApp.benchmarks (
    `benchmark_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `symbol` VARCHAR(255),
    `benchmark_type` VARCHAR(255),
    `currency` VARCHAR(255),
PRIMARY KEY (benchmark_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BenchmarkApp.benchmark_constituents (
    `benchmark_constituent_id` INT AUTO_INCREMENT,
    `benchmark_id` INT,
    `instrument_id` INT,
    `weight` DOUBLE,
    `effective_date` DATETIME,
PRIMARY KEY (benchmark_constituent_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BenchmarkApp.benchmark_values (
    `benchmark_value_id` INT AUTO_INCREMENT,
    `benchmark_id` INT,
    `value_date` DATETIME,
    `value` DOUBLE,
    `daily_return` DOUBLE,
PRIMARY KEY (benchmark_value_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BenchmarkApp.portfolio_benchmarks (
    `portfolio_benchmark_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `benchmark_id` INT,
    `effective_date` DATETIME,
PRIMARY KEY (portfolio_benchmark_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
