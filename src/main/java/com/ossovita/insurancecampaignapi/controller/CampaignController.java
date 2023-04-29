package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.payload.CampaignDto;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatus;
import com.ossovita.insurancecampaignapi.payload.response.AuthResponse;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@Api(value = "Campaign Resource", description = "User can create & update campaign")
public class CampaignController {

    CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }


    //admin, company
    @ApiOperation(value = "Create a new campaign", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToCreateCampaign(#campaignDto.userId,principal)")
    @PostMapping("/create")
    public CampaignResponse createCampaign(@Validated @RequestBody CampaignDto campaignDto) {
        return campaignService.createCampaign(campaignDto);
    }

    //admin
    @ApiOperation(value = "Update status of multiple campaigns and return list of CampaignResponse objects", response = List.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-multiple-campaign-status")
    public List<CampaignResponse> updateMultipleCampaignStatus(@Validated @RequestBody UpdateMultipleCampaignStatus updateMultipleCampaignStatus){
        return campaignService.updateMultipleCampaignStatus(updateMultipleCampaignStatus);
    }

    //admin
    @ApiOperation(value = "Update campaign status by campaign ID and status", response = CampaignResponse.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-campaign-status")
    public CampaignResponse updateCampaignStatus(@Validated @RequestBody UpdateCampaignStatusRequest updateCampaignStatusRequest){
        return campaignService.updateCampaignStatus(updateCampaignStatusRequest);
    }

    //admin, company
    @ApiOperation(value = "Deactivate campaign status by campaign ID", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToUpdateCampaign(#campaignId,principal)")
    @PutMapping("/deactivate-campaign-status")
    public CampaignResponse deactivateCampaignStatus(@RequestParam long campaignId) {
        return campaignService.deactivateCampaignStatus(campaignId);
    }

    //admin
    @ApiOperation(value = "Update campaign by admin and return CampaignResponse object", response = CampaignResponse.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-campaign-by-admin")
    public CampaignResponse updateCampaignByAdmin(@Validated @RequestBody UpdateCampaignByAdminRequest updateCampaignByAdminRequest){
        return campaignService.updateCampaignByAdmin(updateCampaignByAdminRequest);
    }

    //admin, company
    @ApiOperation(value = "Update campaign by company", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToUpdateCampaign(#updateCampaignByCompanyRequest.campaignId,principal)")
    @PutMapping("/update-campaign-by-company")
    public CampaignResponse updateCampaignByCompany(@Validated @RequestBody UpdateCampaignByCompanyRequest updateCampaignByCompanyRequest){
        return campaignService.updateCampaignByCompany(updateCampaignByCompanyRequest);
    }


}
