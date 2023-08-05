package aaagt.shop.business.department.delivery;

import aaagt.shop.business.department.delivery.dto.Tracking;

import java.util.Optional;

public interface DeliveryDepartment {

    /**
     * Получить трэкинг с информацией о текущем местоположении заказа
     *
     * @param orderId идентификатор заказа
     * @return трэкинг с информацией о текущем местоположении заказа
     */
    Optional<Tracking> getOrderTracking(int orderId);

    /**
     * Зарегестрировать заказ во внешней службе доставки
     *
     * @param orderId         идентификатор заказа
     * @param startCity       из какого города
     * @param finishCity      в какой город
     * @param deliveryService идентификатор внешней службы доставки, с помощью
     *                        которой будет доставляться заказ
     */
    void registerOrder(
            int orderId,
            String startCity,
            String finishCity,
            String deliveryService
    );
}
