package com.api.wallet.repository;

import com.api.wallet.entity.Audit;

public interface Auditable {

    Audit getAuditComposition();

    void setAuditComposition(Audit audit);
}
