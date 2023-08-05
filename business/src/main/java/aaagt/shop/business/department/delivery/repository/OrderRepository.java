package aaagt.shop.business.department.delivery.repository;

public interface OrderRepository {
    String getExternalDeliveryNameForOrder(int orderId);

    void setExternalDeliveryNameForOrder(int orderId, String externalDelivery);
}
