package com.api.wallet.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Setter
@Getter
public class Audit {

    private String creator;

    private String modifier;

    private LocalDate created;

    private LocalDate modified;

    protected Audit(){}

    public static final class AuditBuilder {
        private String creator;
        private String modifier;
        private LocalDate created;
        private LocalDate modified;

        private AuditBuilder() {
        }

        public static AuditBuilder anAudit() {
            return new AuditBuilder();
        }

        public Audit build() {
            return new Audit(creator, modifier, created, modified);
        }

        public AuditBuilder forCreation(String creator, LocalDate created){
            this.creator = creator;
            this.created = created;
            return this;
        }

        public AuditBuilder forModified(String modifier, LocalDate modified, Audit audit){
            this.modifier = modifier;
            this.modified = modified;
            this.creator = audit.getCreator();
            this.created = audit.getCreated();
            return this;
        }
    }

}
