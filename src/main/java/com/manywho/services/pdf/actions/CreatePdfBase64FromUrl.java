package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;

@Action.Metadata(name = "Create PDF from URL", summary = "Create PDF from URL", uri = "pdf-from-url")
public class CreatePdfBase64FromUrl {

    public static class Input {
        @Action.Input(name = "PDF URL", contentType = ContentType.String, required = true)
        private String pdfUrl;

        public String getPdfUrl() {
            return pdfUrl;
        }
    }

    public static class Output {
        @Action.Output(name = "PDF (Base64 Encoded)", contentType = ContentType.String)
        private String pdf;

        public Output(String pdf) {
            this.pdf = pdf;
        }
    }
}
