package aaagt.shop.business.department.delivery.service;


import aaagt.shop.business.department.delivery.dto.Tracking;

import java.util.Optional;


/**
 * Интерфейс для работы со внешними службами доставки
 */
public interface ExternalDeliveryService {

    /**
     * Получить название-идентификатор внешней службы доставки
     *
     * @return идентификатор
     */
    String getName();

    /**
     * Отследить заказ
     *
     * @param orderId идентификатор заказа
     * @return трекинг по заказу
     */
    Optional<Tracking> getTrackingForOrder(int orderId);

    /**
     * Зарегестрировать заказ во внешней службе доставки
     *
     * @param orderId    идентификатор заказа
     * @param startCity  из какого города
     * @param finishCity в какой город
     */
    void registerOrder(int orderId, String startCity, String finishCity);
}
