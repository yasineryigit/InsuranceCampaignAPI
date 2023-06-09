package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.payload.request.CampaignRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;

import java.util.List;

public interface CampaignService {
    CampaignResponse createCampaign(CampaignRequest campaignRequest);

    Campaign findById(long campaignId);

    CampaignResponse deactivateCampaignStatus(long campaignId);

    CampaignResponse updateCampaignByAdmin(UpdateCampaignByAdminRequest updateCampaignByAdminRequest);

    CampaignResponse updateCampaignByCompany(UpdateCampaignByCompanyRequest updateCampaignByAdminRequest);

    CampaignResponse updateCampaignStatus(UpdateCampaignStatusRequest updateCampaignStatusRequest);

    List<CampaignResponse> updateMultipleCampaignStatus(UpdateMultipleCampaignStatusRequest updateMultipleCampaignStatusRequest);

    StatisticsResponse getStatisticsForCampaigns();
}
