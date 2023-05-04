package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.entity.CampaignEvent;
import com.ossovita.insurancecampaignapi.enums.CampaignEventType;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.error.exception.IdNotFoundException;
import com.ossovita.insurancecampaignapi.error.exception.RepetitiveCampaignException;
import com.ossovita.insurancecampaignapi.payload.request.*;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;
import com.ossovita.insurancecampaignapi.repository.CampaignRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    CampaignRepository campaignRepository;
    CampaignCategoryService campaignCategoryService;
    CampaignEventService campaignEventService;
    UserService userService;
    ModelMapper modelMapper;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignCategoryService campaignCategoryService, CampaignEventService campaignEventService, UserService userService, ModelMapper modelMapper) {
        this.campaignRepository = campaignRepository;
        this.campaignCategoryService = campaignCategoryService;
        this.campaignEventService = campaignEventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CampaignResponse createCampaign(CampaignRequest campaignRequest) {
        Campaign campaign = modelMapper.map(campaignRequest, Campaign.class);
        //check repetitive campaign
        if (campaignRepository.isCampaignExists(campaign)) {
            campaign.setCampaignStatus(CampaignStatus.REPETITIVE);
        } else { // if campaign is unique
            //validate userId
            campaign.setUserId(userService.findByUserId(campaignRequest.getUserId()).getUserId());
            setCampaignStatusDependsOnRequiresApprovement(campaign);
        }
        Campaign savedCampaign = campaignRepository.save(campaign);
        saveCampaignEvent(savedCampaign, CampaignEventType.CREATE);
        return modelMapper.map(savedCampaign, CampaignResponse.class);
    }


    @Override
    public CampaignResponse deactivateCampaignStatus(long campaignId) {
        Campaign campaign = findById(campaignId);
        //The user can "Deactivate" the "Active" status or "Pending Approval" status.
        //Repetitive Campaign can not be updated
        setCampaignStatusIfNotRepetitive(campaign, CampaignStatus.DEACTIVATED);

        saveCampaignEvent(campaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(campaign), CampaignResponse.class);
    }


    @Override
    public CampaignResponse updateCampaignByAdmin(UpdateCampaignByAdminRequest updateCampaignByAdminRequest) {
        Campaign updatedCampaign = updateCampaign(modelMapper.map(updateCampaignByAdminRequest, Campaign.class));
        saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(updatedCampaign), CampaignResponse.class);
    }


    @Override
    public CampaignResponse updateCampaignByCompany(UpdateCampaignByCompanyRequest updateCampaignByAdminRequest) {
        Campaign updatedCampaign = updateCampaign(modelMapper.map(updateCampaignByAdminRequest, Campaign.class));
        setCampaignStatusDependsOnRequiresApprovement(updatedCampaign);
        saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(updatedCampaign), CampaignResponse.class);
    }


    @Override
    public CampaignResponse updateCampaignStatus(UpdateCampaignStatusRequest updateCampaignStatusRequest) {
        Campaign campaign = findById(updateCampaignStatusRequest.getCampaignId());
        //Repetitive Campaign can not be updated
        campaign = setCampaignStatusIfNotRepetitive(campaign, updateCampaignStatusRequest.getCampaignStatus());
        saveCampaignEvent(campaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(campaign), CampaignResponse.class);
    }


    @Override
    public List<CampaignResponse> updateMultipleCampaignStatus(UpdateMultipleCampaignStatusRequest updateMultipleCampaignStatusRequest) {
        List<Campaign> campaignList = campaignRepository.findAllById(updateMultipleCampaignStatusRequest.getCampaignIdList());
        List<Campaign> updatedCampaignList = campaignList.stream()
                .map(campaign -> {
                    //Repetitive Campaign can not be updated
                    return setCampaignStatusIfNotRepetitive(campaign, updateMultipleCampaignStatusRequest.getCampaignStatus());
                }).toList();
        updatedCampaignList.forEach(updatedCampaign -> saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE));
        return campaignRepository.saveAll(updatedCampaignList).stream()
                .map(campaign -> modelMapper.map(campaign, CampaignResponse.class)).toList();
    }


    @Override
    public StatisticsResponse getStatisticsForCampaigns() {
        return campaignRepository.getStatisticsForCampaigns();
    }


    public Campaign updateCampaign(Campaign campaignTemplate) {
        //check campaignId existence
        Campaign campaign = findById(campaignTemplate.getCampaignId());
        setCampaignStatusIfNotRepetitive(campaign, campaignTemplate.getCampaignStatus());
        //check campainCategoryId existence
        campaign.setCampaignCategoryId(campaignCategoryService.findById(campaignTemplate.getCampaignCategoryId()).getCampaignCategoryId());
        campaign.setCampaignTitle(campaignTemplate.getCampaignTitle());
        campaign.setCampaignDescription(campaignTemplate.getCampaignDescription());
        return campaign;
    }

    //Repetitive Campaign can not be updated
    public Campaign setCampaignStatusIfNotRepetitive(Campaign campaign, CampaignStatus campaignStatus) {
        if (!campaign.getCampaignStatus().equals(CampaignStatus.REPETITIVE)) {
            campaign.setCampaignStatus(campaignStatus);
            return campaign;
        } else {
            throw new RepetitiveCampaignException("Repetitive Campaign with id: " + campaign.getCampaignId() + " cannot be updated");
        }
    }

    //campaignStatus should be PENDING_APPROVAL right after Create or Update Campaign except 'Life Insurance' category
    public void setCampaignStatusDependsOnRequiresApprovement(Campaign campaign) {
        // check isCampaignCategoryRequiresApprovement
        if (campaignCategoryService.findById(campaign.getCampaignCategoryId()).isCampaignCategoryRequiresApprovement()) {
            campaign.setCampaignStatus(CampaignStatus.PENDING_APPROVAL);
        } else {
            campaign.setCampaignStatus(CampaignStatus.ACTIVE);
        }
    }

    public void saveCampaignEvent(Campaign campaign, CampaignEventType campaignEventType) {
        CampaignEvent campaignEvent = CampaignEvent.builder()
                .campaignId(campaign.getCampaignId())
                .campaignEventTime(LocalDateTime.now())
                .campaignEventType(campaignEventType)
                .campaignEventDetails(modelMapper.map(campaign, CampaignResponse.class).toString())
                .build();

        campaignEventService.save(campaignEvent);
    }

    @Override
    public Campaign findById(long campaignId) {
        return campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IdNotFoundException("Campaign not found by given id: " + campaignId));
    }


}
