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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.actuate.audit.listener.AuditListener;

@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditListener.class)
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "transaction")
public class Transaction implements Serializable, Auditable {

    protected Transaction(Long transactionId, BigDecimal amount, BigDecimal fee, String status, TransactionStatusType type,
			BigDecimal balance, String transactionDestinationId, Audit auditComposition, BankAccount bankAccount) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.fee = fee;
		this.status = status;
		this.type = type;
		this.balance = balance;
		this.transactionDestinationId = transactionDestinationId;
		this.auditComposition = auditComposition;
		this.bankAccount = bankAccount;
	}

	private static final long serialVersionUID = 8600252731258980661L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    @Column(nullable = false,precision=20, scale=2)
    private BigDecimal amount;

    @Column(nullable = false,precision=20, scale=2)
    private BigDecimal fee;

    @Column(nullable = false)
    private String status;

    @Enumerated(EnumType.STRING)
    private TransactionStatusType type;

    @Column(nullable = false,precision=20, scale=2)
    private BigDecimal balance;

    @Column(name = "transaction_destination_id", nullable = false)
    private String transactionDestinationId;

    @Embedded
    private Audit auditComposition;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;
    
    public Transaction() {
    	super();
    }

    public Audit getAuditComposition() {
        return auditComposition;
    }

    public void setAuditComposition(Audit auditComposition) {
        this.auditComposition = auditComposition;
    }

}