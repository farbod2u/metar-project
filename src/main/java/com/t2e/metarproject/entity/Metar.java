package com.t2e.metarproject.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.t2e.metarproject.jsonview.MetarJsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_metar")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String data;

    @Column(nullable = false)
    private LocalDateTime saveDate;

    @ManyToOne
    @JoinColumn(name = "icaoCode", nullable = false)
    private Subscription subscription;

    private LocalDateTime timestamp;

    @JsonView(MetarJsonView.MetarWindTemp.class)
    private Float windSpeed;

    @JsonView(MetarJsonView.MetarWindTemp.class)
    private Float temperature;


    private Float visibility;

    @PrePersist
    void defaults() {
        if (saveDate == null)
            saveDate = LocalDateTime.now();
    }
}
