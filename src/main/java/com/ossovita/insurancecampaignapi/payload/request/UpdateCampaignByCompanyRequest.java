package com.ossovita.insurancecampaignapi.payload.request;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateCampaignByCompanyRequest {

    @NotNull(message = "{ossovita.constraint.field.NotNull.message}")
    @Min(value = 1, message = "{ossovita.constraint.id.validation.message}")
    private long campaignId;

    @Size(min = 1, max = 50) //TODO | set as 10-50
    private String campaignTitle;

    @Size(min = 1, max = 200) //TODO | set as 50-200
    private String campaignDescription;

    @NotNull(message = "{ossovita.constraint.field.NotNull.message}")
    @Min(value = 1, message = "{ossovita.constraint.id.validation.message}")
    @ApiModelProperty(value = "1: Complementary Health Insurance | 2: Private Health Insurance | 3: Life Insurance | 4: Others", example = "2", required = true)
    private long campaignCategoryId;

}
