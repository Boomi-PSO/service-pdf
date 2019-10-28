package com.manywho.services.pdf.types;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.types.Type;

@Type.Element(name = PDFBase64.NAME, summary = "PDF Base64 Encoded")
public class PDFBase64 implements Type{
    public final static String NAME = "PDF Base64";

    @Type.Identifier
    @Type.Property(name = "Data", contentType = ContentType.String)
    private String data;

    public String getData() {
        return data;
    }

    public PDFBase64() {}

    public PDFBase64(String data) {
        this.data = data;
    }
}
