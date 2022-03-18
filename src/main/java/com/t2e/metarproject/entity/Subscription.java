package com.t2e.metarproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_subscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @Column(length = 4)
    private String icaoCode;

    @Column(nullable = false)
    private LocalDateTime saveDate;

    @PrePersist
    void defaults() {
        this.saveDate = LocalDateTime.now();
    }
}
