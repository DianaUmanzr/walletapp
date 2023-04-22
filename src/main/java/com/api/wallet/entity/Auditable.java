package com.api.wallet.entity;

public interface Auditable {

    Audit getAuditComposition();

    void setAuditComposition(Audit audit);
}
