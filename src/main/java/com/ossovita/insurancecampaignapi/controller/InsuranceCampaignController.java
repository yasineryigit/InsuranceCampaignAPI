package com.ossovita.insurancecampaignapi.controller;

import com.ossovita.insurancecampaignapi.payload.InsuranceCampaignDto;
import com.ossovita.insurancecampaignapi.service.InsuranceCampaignService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance-campaigns")
public class InsuranceCampaignController {

    InsuranceCampaignService insuranceCampaignService;

    public InsuranceCampaignController(InsuranceCampaignService insuranceCampaignService) {
        this.insuranceCampaignService = insuranceCampaignService;
    }

    @PostMapping("/create")
    public InsuranceCampaignDto createInsuranceCampaign(@Validated @RequestBody InsuranceCampaignDto insuranceCampaignDto) {
        return insuranceCampaignService.createInsuranceCampaign(insuranceCampaignDto);
    }

}
