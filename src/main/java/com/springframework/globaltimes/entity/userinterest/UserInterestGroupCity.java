package com.springframework.globaltimes.entity.userinterest;

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
@Table(name = "user_interest_group_cities")
public class UserInterestGroupCity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_group_id")
    private UUID groupId;

    @Column(name = "city_id")
    private UUID cityId;

    @Column(name = "create_timestamp")
    private ZonedDateTime createTimestamp;

    @Column(name = "last_updated_timestamp")
    private ZonedDateTime lastUpdatedTimestamp;

    @Column(name = "last_op_id")
    private UUID lastOpId;

}
