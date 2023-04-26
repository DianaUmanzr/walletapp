package com.api.wallet.entity;

import com.api.wallet.entity.enums.TransactionStatusType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 8600252731258980661L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false,precision=20, scale=2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet(Long id, BigDecimal balance, User user) {
        super();
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

    public Wallet() {
    }
}
