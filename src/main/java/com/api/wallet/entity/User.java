package com.api.wallet.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 8600252731258980661L;

    protected User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "national_id", nullable = false)
    private String nationalId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets = new ArrayList<>();


    public static final class UserBuilder {
        private Long userId;
        private String name;
        private String surname;
        private String nationalId;
        private List<BankAccount> bankAccounts = new ArrayList<>();
        private List<Wallet> wallets = new ArrayList<>();

        public UserBuilder addBankAccount(BankAccount bankAccount) {
            this.bankAccounts.add(bankAccount);
            return this;
        }

        public UserBuilder addWallet(Wallet wallet) {
            this.wallets.add(wallet);
            return this;
        }

        public User build() {
            User user = new User();
            user.setUserId(this.userId);
            user.setName(this.name);
            user.setSurname(this.surname);
            user.setNationalId(this.nationalId);
            user.setBankAccounts(this.bankAccounts);
            user.setWallets(this.wallets);
            return user;
        }
    }
}
