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

    @Column(nullable = false)
    private Integer active;

    @PrePersist
    void defaults() {
        if (saveDate == null)
            this.saveDate = LocalDateTime.now();

        if (active == null)
            active = 1;

    }
}
