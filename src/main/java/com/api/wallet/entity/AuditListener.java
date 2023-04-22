package com.api.wallet.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;

public class AuditListener {

    @PrePersist
    public void setCreate(Auditable auditable){
            Audit audit = Audit.AuditBuilder.anAudit().forCreation("ADMIN", LocalDate.now()).build();
            auditable.setAuditComposition(audit);
    }

    @PreUpdate
    public void setModified(Auditable auditable){
        Audit audit = auditable.getAuditComposition();
        audit = Audit.AuditBuilder.anAudit().forModified("ADMIN", LocalDate.now(),audit).build();
        auditable.setAuditComposition(audit);
    }

}
