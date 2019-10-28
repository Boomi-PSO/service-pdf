package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.services.actions.ActionCommand;
import com.manywho.sdk.services.actions.ActionResponse;
import com.manywho.services.pdf.ServiceConfiguration;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import java.io.InputStream;
import java.net.URL;

public class CreatePdfBase64FromUrlCommand implements ActionCommand<ServiceConfiguration, CreatePdfBase64FromUrl, CreatePdfBase64FromUrl.Input, CreatePdfBase64FromUrl.Output> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePdfBase64FromUrlCommand.class);
    
    @Inject
    public CreatePdfBase64FromUrlCommand() {
    }

    @Override
    public ActionResponse<CreatePdfBase64FromUrl.Output> execute(ServiceConfiguration serviceConfiguration, ServiceRequest serviceRequest, CreatePdfBase64FromUrl.Input input) {
        try {
            InputStream inputStream = new URL(input.getPdfUrl()).openStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            CreatePdfBase64FromUrl.Output output = new CreatePdfBase64FromUrl.Output(Base64.encodeBytes(bytes));

            return new ActionResponse<>(output);
        } catch (Exception e) {
            LOGGER.error("There was a problem generating a PDF from the provided URL: {}", e.getMessage(), e);

            throw new RuntimeException(String.format("There was a problem generating a PDF from the provided URL: %s", e.getMessage()));
        }
    }
}
