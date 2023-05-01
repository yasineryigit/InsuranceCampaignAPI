package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.*;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.payload.request.CampaignRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.repository.CampaignCategoryRepository;
import com.ossovita.insurancecampaignapi.repository.CampaignRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @InjectMocks
    CampaignServiceImpl campaignService;
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    CampaignCategoryService campaignCategoryService;
    @Mock
    CampaignEventService campaignEventService;
    @Mock
    UserService userService;
    @Mock
    ModelMapper modelMapper;

    Campaign campaign;
    CampaignCategory campaignCategory;
    CampaignResponse campaignResponse;
    CampaignRequest campaignRequest;

    @BeforeEach
    void setUp() {

        //Create required objects
        campaignCategory = CampaignCategory.builder()
                .campaignCategoryName("Complementary Health Insurance")
                .campaignCategoryRequiresApprovement(true)
                .build();
        campaign = Campaign.builder()
                .campaignId(1)
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignStatus(CampaignStatus.PENDING_APPROVAL)
                .userId(2)
                .campaignCategoryId(2)
                .build();
        campaignRequest = CampaignRequest.builder()
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignCategoryId(1)
                .userId(2)
                .build();
        campaignResponse = CampaignResponse.builder()
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignStatus(CampaignStatus.PENDING_APPROVAL)
                .userId(2)
                .campaignCategoryId(1)
                .build();

    }

    @DisplayName("Test CampaignService createCampaign method")
    @Description("Test the functionality of the createCampaign method in the CampaignServiceImpl class")
    @Test
    void createCampaign_ShouldReturnCampaignResponse() {
        // Arrange
        when(campaignRepository.isCampaignExists(campaign)).thenReturn(false);
        when(userService.findByUserId(campaignRequest.getUserId())).thenReturn(new User());
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(campaignCategoryService.findById(campaign.getCampaignCategoryId())).thenReturn(campaignCategory);
        when(modelMapper.map(campaignRequest, Campaign.class)).thenReturn(campaign);
        when(modelMapper.map(campaign, CampaignResponse.class)).thenReturn(campaignResponse);

        // Act
        CampaignResponse actualResponse = campaignService.createCampaign(campaignRequest);

        // Assert
        assertEquals(campaignResponse, actualResponse);
    }

    @DisplayName("Test CampaignService deactivateCampaignStatus method")
    @Description("Test the functionality of the deactivateCampaignStatus method in the CampaignServiceImpl class")
    @Test
    void deactivateCampaignStatus_ShouldReturnDeactivatedCampaignResponse() {
        // Arrange

        CampaignResponse expectedResponse = new CampaignResponse();
        expectedResponse.setCampaignId(campaign.getCampaignId());
        expectedResponse.setCampaignStatus(CampaignStatus.DEACTIVATED);

        when(campaignRepository.findById(campaign.getCampaignId())).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(modelMapper.map(campaign, CampaignResponse.class)).thenReturn(expectedResponse);

        // Act
        CampaignResponse actualResponse = campaignService.deactivateCampaignStatus(campaign.getCampaignId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        assertEquals(CampaignStatus.DEACTIVATED, campaign.getCampaignStatus());
    }


    @Test
    void updateCampaignByAdmin_ShouldReturnCampaignResponse() {
        // Arrange
        UpdateCampaignByAdminRequest request = new UpdateCampaignByAdminRequest();
        request.setCampaignId(1L);
        request.setCampaignTitle("New Campaign Title");
        request.setCampaignDescription("New Campaign Description");
        request.setCampaignCategoryId(2L);
        request.setCampaignStatus(CampaignStatus.ACTIVE);

        Campaign campaign = new Campaign();
        campaign.setCampaignId(1L);
        campaign.setCampaignTitle("Old Campaign Title");
        campaign.setCampaignDescription("Old Campaign Description");
        campaign.setCampaignCategoryId(2L);
        campaign.setCampaignStatus(CampaignStatus.PENDING_APPROVAL);

        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setCampaignId(campaign.getCampaignId());
        campaignResponse.setCampaignTitle(campaign.getCampaignTitle());
        campaignResponse.setCampaignDescription(campaign.getCampaignDescription());
        campaignResponse.setCampaignCategoryId(campaign.getCampaignCategoryId());
        campaignResponse.setCampaignStatus(campaign.getCampaignStatus());

        when(campaignRepository.findById(request.getCampaignId())).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(modelMapper.map(request, Campaign.class)).thenReturn(campaign);
        when(campaignCategoryService.findById(request.getCampaignCategoryId())).thenReturn(new CampaignCategory());
        when(modelMapper.map(campaign, CampaignResponse.class)).thenReturn(campaignResponse);

        // Act
        CampaignResponse actualResponse = campaignService.updateCampaignByAdmin(request);

        // Assert
        assertEquals(campaignResponse, actualResponse);

    }




    @Test
    void updateCampaignByCompany() {
    }

    @Test
    void updateCampaignStatus() {
    }

    @Test
    void updateMultipleCampaignStatus() {
    }

    @Test
    void getStatisticsForCampaigns() {
    }

    @Test
    void updateCampaign() {
    }

    @Test
    void setCampaignStatusIfNotRepetitive() {
    }

    @Test
    void setCampaignStatusDependsOnRequiresApprovement() {
    }

    @Test
    void saveCampaignEvent() {
    }

    @Test
    void findById() {
    }
}