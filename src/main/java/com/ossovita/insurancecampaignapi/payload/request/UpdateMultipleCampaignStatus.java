package com.ossovita.insurancecampaignapi.payload.request;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateMultipleCampaignStatus {

    @NotNull
    List<Long> campaignIdList;

    @NotNull
    CampaignStatus campaignStatus;

}
