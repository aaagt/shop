package aaagt.shop.business.department.delivery;

import aaagt.shop.business.department.Department;
import aaagt.shop.business.department.delivery.dto.Tracking;
import aaagt.shop.business.department.delivery.repository.OrderRepository;
import aaagt.shop.business.department.delivery.service.ExternalDeliveryService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Департамент службы доставки
 */
public class ShopDeliveryDepartment implements Department, DeliveryDepartment {

    /**
     * Название-идентификатор департамента службы доставки
     */
    private static final String DELIVERY_DEPARTMENT_NAME = "Доставка";

    private final Map<String, ExternalDeliveryService> deliveryServices;
    private final OrderRepository orderRepository;

    public ShopDeliveryDepartment(
            List<ExternalDeliveryService> deliveryServices,
            OrderRepository orderRepository
    ) {
        this.deliveryServices = deliveryServices.stream()
                .collect(Collectors.toMap(ExternalDeliveryService::getName, Function.identity()));
        this.orderRepository = orderRepository;
    }

    /**
     * Получить название-идентификатор подразделения
     *
     * @return название-идентификатор подразделения
     */
    @Override
    public String getName() {
        return DELIVERY_DEPARTMENT_NAME;
    }

    /**
     * Получить трэкинг с информацией о текущем местоположении заказа
     *
     * @param orderId идентификатор заказа
     * @return трэкинг с информацией о текущем местоположении заказа
     */
    @Override
    public Tracking getOrderTracking(int orderId) {
        var externalDelivery = orderRepository.getExternalDeliveryNameForOrder(orderId);
        var service = deliveryServices.get(externalDelivery);
        return service.getTrackingForOrder(orderId);
    }

    /**
     * Зарегестрировать заказ во внешней службе доставки
     *
     * @param orderId         идентификатор заказа
     * @param startCity       из какого города
     * @param finishCity      в какой город
     * @param deliveryService идентификатор внешней службы доставки, с помощью
     *                        которой будет доставляться заказ
     */
    @Override
    public void registerOrder(
            int orderId,
            String startCity,
            String finishCity,
            String deliveryService
    ) {
        var service = deliveryServices.get(deliveryService);
        service.registerOrder(orderId, startCity, finishCity);
        orderRepository.setExternalDeliveryNameForOrder(orderId, deliveryService);
    }
}
