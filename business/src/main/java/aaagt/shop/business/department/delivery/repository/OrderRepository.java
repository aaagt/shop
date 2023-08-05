package aaagt.shop.business.department.delivery.repository;

import java.util.Optional;

public interface OrderRepository {
    Optional<String> getExternalDeliveryNameForOrder(int orderId);

    void setExternalDeliveryNameForOrder(int orderId, String externalDelivery);
}
