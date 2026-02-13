package com.job.microservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.job.microservice.dto.JobDTO;
import com.job.microservice.entity.JobResult;
import com.job.microservice.service.JobService;

@RestController
@RequestMapping("api/jobs")
public class JobController {

	private JobService jobService;
	
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping("/")
	public ResponseEntity<List<JobDTO>> findAll()
	{
		return ResponseEntity.ok((jobService.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobResult> getJobById(@PathVariable Long id)
	{
       JobResult jobWithCompanyDTO = jobService.findJobById(id);
	   if(jobWithCompanyDTO!=null)
	   return new ResponseEntity<>(jobWithCompanyDTO,HttpStatus.OK);
	   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJob(@RequestParam String keyword)
	{
		return ResponseEntity.ok((jobService.searchJob(keyword)));
	}

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<List<JobDTO>> getSpecificJobs(@RequestParam boolean isFullTime, @RequestParam boolean isPartTime, @RequestParam boolean isInternship, @PathVariable Long companyId)
	{
		return ResponseEntity.ok(jobService.getSpecificJobs(companyId,isFullTime,isPartTime,isInternship));
	}

}

