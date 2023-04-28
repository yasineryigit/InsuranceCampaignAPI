package com.ossovita.insurancecampaignapi.payload.response;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class CampaignResponse {

    private long campaignId;

    private long userId;

    private long campaignCategoryId;

    private String campaignTitle;

    private String campaignDescription;

    private CampaignStatus campaignStatus;
}
