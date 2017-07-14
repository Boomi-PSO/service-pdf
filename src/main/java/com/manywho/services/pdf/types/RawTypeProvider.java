package com.manywho.services.pdf.types;

import com.google.common.base.Strings;
import com.manywho.sdk.api.describe.DescribeServiceRequest;
import com.manywho.sdk.api.draw.elements.type.TypeElement;
import com.manywho.sdk.services.configuration.Configuration;
import com.manywho.sdk.services.configuration.ConfigurationParser;
import com.manywho.sdk.services.types.TypeProvider;
import com.manywho.services.pdf.ServiceConfiguration;
import com.manywho.services.pdf.managers.DescribeManager;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RawTypeProvider implements TypeProvider {

    private DescribeManager describeTypeService;
    private ConfigurationParser configurationParser;

    @Inject
    public RawTypeProvider(DescribeManager describeManager, ConfigurationParser configurationParser) {
        this.describeTypeService = describeManager;
        this.configurationParser = configurationParser;
    }

    @Override
    public boolean doesTypeExist(Configuration configuration, String s) {
        return true;
    }

    @Override
    public List<TypeElement> describeTypes(Configuration configuration, DescribeServiceRequest describeServiceRequest) {

        try {
            if (describeServiceRequest.getConfigurationValues() != null && describeServiceRequest.getConfigurationValues().size()>0) {
                ServiceConfiguration serviceConfiguration = configurationParser.from(describeServiceRequest);
                if (serviceConfiguration.getPdfFormUrl() != null) {
                    if (Strings.isNullOrEmpty(serviceConfiguration.getPdfFormUrl())) {
                        throw new RuntimeException("The PDF Type Name can not be empty if PDF Form URL is populated");
                    }

                    return describeTypeService.getListTypeElement(serviceConfiguration.getPdfFormUrl());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>();
    }
}