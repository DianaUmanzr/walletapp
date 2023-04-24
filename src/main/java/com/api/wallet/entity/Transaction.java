package com.api.wallet.entity;

import com.api.wallet.entity.enums.TransactionStatusType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.actuate.audit.listener.AuditListener;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@EntityListeners(AuditListener.class)
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "transaction")
public class Transaction implements Serializable, Auditable {

    private static final long serialVersionUID = 8600252731258980661L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal fee;

    @Column(nullable = false)
    private String status;

    @Enumerated(EnumType.STRING)
    private TransactionStatusType type;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "transaction_destination_id", nullable = false)
    private String transactionDestinationId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    @Embedded
    private Audit auditComposition;

    public Audit getAuditComposition() {
        return auditComposition;
    }

    public void setAuditComposition(Audit auditComposition) {
        this.auditComposition = auditComposition;
    }

    protected Transaction() {

    }

    public static class TransactionBuilder {
        private Long transactionId;
        private BigDecimal amount;
        private BigDecimal fee;
        private String status;
        private TransactionStatusType type;
        private BigDecimal balance;
        private Audit auditComposition;
        private Wallet wallet = null;

        TransactionBuilder() {}

        public TransactionBuilder transactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder fee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public TransactionBuilder status(String status) {
            this.status = status;
            return this;
        }

        public TransactionBuilder type(TransactionStatusType type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public TransactionBuilder auditComposition(Audit auditComposition) {
            this.auditComposition = auditComposition;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.transactionId = this.transactionId;
            transaction.amount = this.amount;
            transaction.fee = this.fee;
            transaction.status = this.status;
            transaction.type = this.type;
            transaction.balance = this.balance;
            transaction.auditComposition = this.auditComposition;
            return transaction;
        }
    }
}