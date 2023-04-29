package com.ossovita.insurancecampaignapi.payload.response;

import lombok.Data;

@Data
public class StatisticsResponse {

    private long activeCampaignCount;

    private long pendingApprovalCampaignCount;

    private long deactivatedCampaignCount;

    private long repetitiveCampaignCount;

    public StatisticsResponse(long activeCampaignCount, long pendingApprovalCampaignCount, long deactivatedCampaignCount, long repetitiveCampaignCount) {
        this.activeCampaignCount = activeCampaignCount;
        this.pendingApprovalCampaignCount = pendingApprovalCampaignCount;
        this.deactivatedCampaignCount = deactivatedCampaignCount;
        this.repetitiveCampaignCount = repetitiveCampaignCount;
    }
}
