package com.da2jobu.aiservice.domain.model;

import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_delivery_request", schema = "ai_input")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryRequest extends BaseEntity {

    @Id
    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "delivery_manager_slack_id", nullable = false)
    private String deliveryManagerSlackId;

    @Column(name = "hub_manager_slack_id", nullable = false)
    private String hubManagerSlackId;

    @Column(name = "departure_hub_name", nullable = false)
    private String departureHubName;

    @Column(name = "arrival_hub_name", nullable = false)
    private String arrivalHubName;

    @Column(name = "departure_lat", nullable = false)
    private double departureLat;

    @Column(name = "departure_lon", nullable = false)
    private double departureLon;

    @Column(name = "arrival_lat", nullable = false)
    private double arrivalLat;

    @Column(name = "arrival_lon", nullable = false)
    private double arrivalLon;

    @Column(name = "scheduled_departure_time", nullable = false)
    private LocalDateTime scheduledDepartureTime;

    @Builder
    private DeliveryRequest(UUID deliveryId, String deliveryManagerSlackId, String hubManagerSlackId,
                            String departureHubName, String arrivalHubName,
                            double departureLat, double departureLon,
                            double arrivalLat, double arrivalLon,
                            LocalDateTime scheduledDepartureTime) {
        this.deliveryId = deliveryId;
        this.deliveryManagerSlackId = deliveryManagerSlackId;
        this.hubManagerSlackId = hubManagerSlackId;
        this.departureHubName = departureHubName;
        this.arrivalHubName = arrivalHubName;
        this.departureLat = departureLat;
        this.departureLon = departureLon;
        this.arrivalLat = arrivalLat;
        this.arrivalLon = arrivalLon;
        this.scheduledDepartureTime = scheduledDepartureTime;
    }
}
