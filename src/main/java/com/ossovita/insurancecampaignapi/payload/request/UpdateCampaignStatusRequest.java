package com.ossovita.insurancecampaignapi.payload.request;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCampaignStatusRequest {

    @NotNull(message = "{ossovita.constraint.field.NotNull.message}")
    @Min(value = 1, message = "{ossovita.constraint.id.validation.message}")
    private long campaignId;

    @NotNull
    private CampaignStatus campaignStatus;


}
