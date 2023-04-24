package com.api.wallet.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void setCreate(Auditable auditable){
            Audit audit = Audit.AuditBuilder.anAudit().forCreation("ADMIN", LocalDateTime.now()).build();
            auditable.setAuditComposition(audit);
    }

    @PreUpdate
    public void setModified(Auditable auditable){
        Audit audit = auditable.getAuditComposition();
        audit = Audit.AuditBuilder.anAudit().forModified("ADMIN", LocalDateTime.now(),audit).build();
        auditable.setAuditComposition(audit);
    }

}
