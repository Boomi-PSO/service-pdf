package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;
import com.manywho.services.pdf.types.FormField;

import java.util.List;

@Action.Metadata(name = "Populate PDF (Base64 encoded) forms", summary = "Populate PDF (Base64 encoded) form", uri = "populate-pdf-forms-base64")
public class PopulatePdfBase64 {

    public static class Input {
        @Action.Input(name = "PDF Fields", contentType = ContentType.List, required = true)
        private List<FormField> pdfFields;

        @Action.Input(name = "PDF (Base64 encoded)", contentType = ContentType.String, required = true)
        private String pdf;

        public List<FormField> getPdfFields() {
            return pdfFields;
        }

        public String getPdfFile() {
            return pdf;
        }
    }

    public static class Output {
        @Action.Output(name = "Populate PDF (Base64 encoded)", contentType = ContentType.String)
        private String pdf;

        public Output(String pdf) {
            this.pdf = pdf;
        }
    }
}
