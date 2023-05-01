package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.payload.request.CampaignRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import com.ossovita.insurancecampaignapi.service.security.CampaignSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@Api(value = "Campaign Resource", description = "User can create & update campaign")
public class CampaignController {

    CampaignService campaignService;
    CampaignSecurityService campaignSecurityService;

    public CampaignController(CampaignService campaignService, CampaignSecurityService campaignSecurityService) {
        this.campaignService = campaignService;
        this.campaignSecurityService = campaignSecurityService;
    }

    //admin, company (only with own id)
    @ApiOperation(value = "Create a new campaign", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToCreateCampaign(#campaignRequest.userId,principal)")
    @PostMapping("/create")
    public ResponseEntity<CampaignResponse> createCampaign(@Validated @RequestBody CampaignRequest campaignRequest) {
        CampaignResponse response = campaignService.createCampaign(campaignRequest);
        return ResponseEntity.ok(response);
    }


    //admin
    @ApiOperation(value = "Update status of multiple campaigns and return list of CampaignResponse objects", response = List.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-multiple-campaign-status")
    public ResponseEntity<List<CampaignResponse>> updateMultipleCampaignStatus(@Validated @RequestBody UpdateMultipleCampaignStatusRequest updateMultipleCampaignStatusRequest){
        List<CampaignResponse> response = campaignService.updateMultipleCampaignStatus(updateMultipleCampaignStatusRequest);
        return ResponseEntity.ok(response);
    }

    //admin
    @ApiOperation(value = "Update campaign status by campaign ID and status", response = CampaignResponse.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-campaign-status")
    public ResponseEntity<CampaignResponse> updateCampaignStatus(@Validated @RequestBody UpdateCampaignStatusRequest updateCampaignStatusRequest){
        CampaignResponse response = campaignService.updateCampaignStatus(updateCampaignStatusRequest);
        return ResponseEntity.ok(response);
    }

    //admin, company (only with own id)
    @ApiOperation(value = "Deactivate campaign status by campaign ID", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToUpdateCampaign(#campaignId,principal)")
    @PutMapping("/deactivate-campaign-status")
    public ResponseEntity<CampaignResponse> deactivateCampaignStatus(@RequestParam long campaignId) {
        CampaignResponse response = campaignService.deactivateCampaignStatus(campaignId);
        return ResponseEntity.ok(response);
    }

    //admin
    @ApiOperation(value = "Update campaign by admin and return CampaignResponse object", response = CampaignResponse.class)
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/update-campaign-by-admin")
    public ResponseEntity<CampaignResponse> updateCampaignByAdmin(@Validated @RequestBody UpdateCampaignByAdminRequest updateCampaignByAdminRequest){
        CampaignResponse response = campaignService.updateCampaignByAdmin(updateCampaignByAdminRequest);
        return ResponseEntity.ok(response);
    }

    //admin, company (only with own id)
    @ApiOperation(value = "Update campaign by company", response = CampaignResponse.class)
    @PreAuthorize("@campaignSecurityService.isAllowedToUpdateCampaign(#updateCampaignByCompanyRequest.campaignId,principal)")
    @PutMapping("/update-campaign-by-company")
    public ResponseEntity<CampaignResponse> updateCampaignByCompany(@Validated @RequestBody UpdateCampaignByCompanyRequest updateCampaignByCompanyRequest){
        CampaignResponse response = campaignService.updateCampaignByCompany(updateCampaignByCompanyRequest);
        return ResponseEntity.ok(response);
    }


}
