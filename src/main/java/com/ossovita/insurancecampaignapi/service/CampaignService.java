package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.payload.CampaignDto;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatus;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;

import java.util.List;

public interface CampaignService {
    CampaignResponse createCampaign(CampaignDto campaignDto);


    CampaignResponse deactivateCampaignStatus(long campaignId);

    CampaignResponse updateCampaignByAdmin(UpdateCampaignByAdminRequest updateCampaignByAdminRequest);

    CampaignResponse updateCampaignByCompany(UpdateCampaignByCompanyRequest updateCampaignByAdminRequest);

    CampaignResponse updateCampaignStatus(UpdateCampaignStatusRequest updateCampaignStatusRequest);

    List<CampaignResponse> updateMultipleCampaignStatus(UpdateMultipleCampaignStatus updateMultipleCampaignStatus);

    StatisticsResponse getStatisticsForCampaigns();
}
