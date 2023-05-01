package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;
import com.ossovita.insurancecampaignapi.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Api(value = "Dashboard Resource", description = "User can view statistics")
public class DashboardController {

    StatisticsService statisticsService;

    public DashboardController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    //admin
    @ApiOperation(value = "Get statistics of classifieds", response = StatisticsResponse.class)
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/classifieds/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics() {
        StatisticsResponse statisticsResponse = statisticsService.getStatistics();
        return ResponseEntity.ok(statisticsResponse);
    }
}
