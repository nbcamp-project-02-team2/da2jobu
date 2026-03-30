package com.da2jobu.deliveryservice.domain.deliveryRouteRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRouteRecordRepository extends JpaRepository<DeliveryRouteRecord, UUID> {

}
