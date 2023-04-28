package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.payload.CampaignDto;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatus;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/create")
    public CampaignResponse createCampaign(@Validated @RequestBody CampaignDto campaignDto) {
        return campaignService.createCampaign(campaignDto);
    }

    @PutMapping("/update-multiple-campaign-status")
    public List<CampaignResponse> updateMultipleCampaignStatus(@Validated @RequestBody UpdateMultipleCampaignStatus updateMultipleCampaignStatus){
        return campaignService.updateMultipleCampaignStatus(updateMultipleCampaignStatus);

    }

    @PutMapping("/update-campaign-status")
    public CampaignResponse updateCampaignStatus(@Validated @RequestBody UpdateCampaignStatusRequest updateCampaignStatusRequest){
        return campaignService.updateCampaignStatus(updateCampaignStatusRequest);
    }

    @PutMapping("/deactivate-campaign-status")
    public CampaignResponse deactivateCampaignStatus(@RequestParam long campaignId) {
        return campaignService.deactivateCampaignStatus(campaignId);
    }

    @PutMapping("/update-campaign-by-admin")
    public CampaignResponse updateCampaignByAdmin(@Validated @RequestBody UpdateCampaignByAdminRequest updateCampaignByAdminRequest){
        return campaignService.updateCampaignByAdmin(updateCampaignByAdminRequest);
    }

    @PutMapping("/update-campaign-by-company")
    public CampaignResponse updateCampaignByCompany(@Validated @RequestBody UpdateCampaignByCompanyRequest updateCampaignByCompanyRequest){
        return campaignService.updateCampaignByCompany(updateCampaignByCompanyRequest);
    }


}
