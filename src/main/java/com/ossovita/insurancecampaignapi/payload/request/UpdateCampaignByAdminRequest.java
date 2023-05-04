package com.ossovita.insurancecampaignapi.payload.request;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCampaignByAdminRequest {

    @NotNull(message = "{ossovita.constraint.field.NotNull.message}")
    @Min(value = 1, message = "{ossovita.constraint.id.validation.message}")
    private long campaignId;

    @Size(min = 10, max = 50)
    private String campaignTitle;

    @Size(min = 20, max = 200)
    private String campaignDescription;

    @NotNull
    private CampaignStatus campaignStatus;

    @NotNull(message = "{ossovita.constraint.field.NotNull.message}")
    @Min(value = 1, message = "{ossovita.constraint.id.validation.message}")
    @ApiModelProperty(value = "1: Complementary Health Insurance | 2: Private Health Insurance | 3: Life Insurance | 4: Others", example = "2", required = true)
    private long campaignCategoryId;


}
