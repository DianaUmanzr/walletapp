package com.api.wallet.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Setter
@Getter
public class Audit {

    private String creator;

    private String modifier;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;
   
    @Column(name = "modified", columnDefinition = "TIMESTAMP")
    private LocalDateTime modified;

    protected Audit(){}

    public static final class AuditBuilder {
        private String creator;
        private String modifier;
        private LocalDateTime created;
        private LocalDateTime modified;

        private AuditBuilder() {
        }

        public static AuditBuilder anAudit() {
            return new AuditBuilder();
        }

        public Audit build() {
            return new Audit(creator, modifier, created, modified);
        }

        public AuditBuilder forCreation(String creator, LocalDateTime created){
            this.creator = creator;
            this.created = created;
            return this;
        }

        public AuditBuilder forModified(String modifier, LocalDateTime modified, Audit audit){
            this.modifier = modifier;
            this.modified = modified;
            this.creator = audit.getCreator();
            this.created = audit.getCreated();
            return this;
        }
    }

}
