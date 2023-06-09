package com.api.wallet.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    protected BankAccount() {}

    private static final long serialVersionUID = 8600254731258980661L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "routing_number", nullable = false)
    private String routingNumber;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static final class BankAccountBuilder {
        private String routingNumber;
        private String accountNumber;
        private String bankName;
        private User user;

        public BankAccount build() {
            BankAccount bankAccount = new BankAccount();
            bankAccount.routingNumber = routingNumber;
            bankAccount.accountNumber = accountNumber;
            bankAccount.bankName = bankName;
            bankAccount.user = user;
            return bankAccount;
        }
    }
}