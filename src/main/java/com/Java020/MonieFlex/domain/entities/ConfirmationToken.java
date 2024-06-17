package com.Java020.MonieFlex.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmationToken extends BaseClass{
    private String token;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        this.expiredAt = LocalDateTime.now().plusDays(1); // 24 hours later
        this.confirmedAt = null;
    }

    @PreUpdate
    protected void onConfirm() {
        if (this.confirmedAt != null) {
            return; // confirmedAt already set, no need to update
        }
        this.confirmedAt = LocalDateTime.now();
    }
}
