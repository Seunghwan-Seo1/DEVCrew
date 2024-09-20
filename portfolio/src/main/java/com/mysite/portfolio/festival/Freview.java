package com.mysite.portfolio.festival;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Freview {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer frid;

	    @Column(columnDefinition = "TEXT")
	    private String frcontent;

	    private LocalDateTime frcreateDate; 

	    @ManyToOne
	    private Festival festival;  
	}


