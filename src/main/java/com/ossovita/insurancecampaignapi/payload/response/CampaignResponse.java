package com.ossovita.insurancecampaignapi.payload.response;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponse {

    private long campaignId;

    private long userId;

    private long campaignCategoryId;

    private String campaignTitle;

    private String campaignDescription;

    private CampaignStatus campaignStatus;
}
