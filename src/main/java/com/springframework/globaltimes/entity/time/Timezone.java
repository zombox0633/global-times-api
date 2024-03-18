package com.springframework.globaltimes.entity.time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "timezones")
public class Timezone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "country_id")
    private UUID countryId;

    @Column(name = "timezone_name")
    private String timezoneName;

    @Column(name = "create_timestamp")
    private ZonedDateTime createTimestamp;

    @Column(name = "last_updated_timestamp")
    private ZonedDateTime lastUpdatedTimestamp;

    @Column(name = "last_op_id")
    private UUID lastOpId;

}
