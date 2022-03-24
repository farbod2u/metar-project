package com.t2e.metarproject.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.t2e.metarproject.jsonview.MetarJsonView;
import com.t2e.metarproject.parser.MetarParseException;
import com.t2e.metarproject.parser.MetarParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_metar")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
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

    @JsonView(MetarJsonView.MetarWindTemp.class)
    public String getAsNaturalLanguage(){
        var result = "";
        try {
            com.t2e.metarproject.parser.Metar parse = MetarParser.parse(this.getData().replace("METAR ", "").replace("=", ""));
            result = parse.asHuman();
        } catch (MetarParseException e) {
            log.log(Level.ERROR, e);
        }
        return result;
    }

    @PrePersist
    void defaults() {
        if (saveDate == null)
            saveDate = LocalDateTime.now();
    }
}
