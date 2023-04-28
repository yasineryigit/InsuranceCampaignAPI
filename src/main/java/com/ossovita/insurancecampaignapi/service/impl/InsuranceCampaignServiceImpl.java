package com.ossovita.insurancecampaignapi.service.impl;

import com.ossovita.insurancecampaignapi.entity.InsuranceCampaign;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.payload.InsuranceCampaignDto;
import com.ossovita.insurancecampaignapi.repository.InsuranceCampaignRepository;
import com.ossovita.insurancecampaignapi.service.CampaignCategoryService;
import com.ossovita.insurancecampaignapi.service.InsuranceCampaignService;
import com.ossovita.insurancecampaignapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InsuranceCampaignServiceImpl implements InsuranceCampaignService {

    InsuranceCampaignRepository insuranceCampaignRepository;
    CampaignCategoryService campaignCategoryService;
    UserService userService;
    ModelMapper modelMapper;

    public InsuranceCampaignServiceImpl(InsuranceCampaignRepository insuranceCampaignRepository, CampaignCategoryService campaignCategoryService, UserService userService, ModelMapper modelMapper) {
        this.insuranceCampaignRepository = insuranceCampaignRepository;
        this.campaignCategoryService = campaignCategoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public InsuranceCampaignDto createInsuranceCampaign(InsuranceCampaignDto insuranceCampaignDto) {
        InsuranceCampaign insuranceCampaign = modelMapper.map(insuranceCampaignDto, InsuranceCampaign.class);
        //check repetitive campaign
        if (insuranceCampaignRepository.isInsuranceCampaignExists(insuranceCampaign)) {
            insuranceCampaign.setCampaignStatus(CampaignStatus.REPETITIVE);
        } else { // if campaign is unique
            //validate userId //TODO | remove?
            insuranceCampaign.setUserId(userService.findByUserId(insuranceCampaignDto.getUserId()).getUserId());
            //validate userId & check isCampaignCategoryRequiresApprovement
            if (campaignCategoryService.findById(insuranceCampaignDto.getCampaignCategoryId()).isCampaignCategoryRequiresApprovement()) {
                insuranceCampaign.setCampaignStatus(CampaignStatus.PENDING_APPROVAL);
            } else {
                insuranceCampaign.setCampaignStatus(CampaignStatus.ACTIVE);
            }
        }
        InsuranceCampaign savedInsuranceCampaign = insuranceCampaignRepository.save(insuranceCampaign);
        return modelMapper.map(savedInsuranceCampaign, InsuranceCampaignDto.class);
    }


}
