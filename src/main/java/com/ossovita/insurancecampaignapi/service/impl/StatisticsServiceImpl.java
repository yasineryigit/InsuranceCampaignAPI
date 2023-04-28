package com.ossovita.insurancecampaignapi.service.impl;

import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import com.ossovita.insurancecampaignapi.service.StatisticsService;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    CampaignService campaignService;

    public StatisticsServiceImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public StatisticsResponse getStatistics() {
        return campaignService.getStatisticsForCampaigns();
    }
}
