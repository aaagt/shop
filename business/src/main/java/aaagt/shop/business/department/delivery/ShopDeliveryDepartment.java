package aaagt.shop.business.department.delivery;

import aaagt.shop.business.department.Department;
import aaagt.shop.business.department.delivery.dto.Tracking;
import aaagt.shop.business.department.delivery.repository.OrderRepository;
import aaagt.shop.business.department.delivery.service.ExternalDeliveryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
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

    @Override
    public void showMenu() {
        System.out.println("Введите одну из доступных команд:");
        System.out.println("ОТСЛЕДИТЬ - чтобы оследить заказ");
        System.out.println("ЗАРЕГЕСТРИРОВАТЬ - чтобы зарегестрировать заказ");
        var scanner = new Scanner(System.in);
        var cmd = scanner.nextLine().toUpperCase();
        switch (cmd) {
            case "ОТСЛЕДИТЬ" -> {
                System.out.println("Введите номер заказа");
                var orderId = Integer.parseInt(scanner.nextLine());
                getOrderTracking(orderId).ifPresentOrElse(System.out::println,
                        () -> {
                            System.out.println("Не удалось найти запрошенный заказ");
                        });
            }
            case "ЗАРЕГЕСТРИРОВАТЬ" -> {
                System.out.print("Введите номер заказа ");
                var orderId = Integer.parseInt(scanner.nextLine());
                System.out.print("Введите пункт отправки ");
                var startCity = scanner.nextLine();
                System.out.print("Введите пункт назначения ");
                var finishCity = scanner.nextLine();
                System.out.print("Введите службу доставки ");
                var deliveryService = scanner.nextLine();

                registerOrder(orderId, startCity, finishCity, deliveryService);
                System.out.println("Заказ зарегестрирован");
            }
        }
    }

    /**
     * Получить трэкинг с информацией о текущем местоположении заказа
     *
     * @param orderId идентификатор заказа
     * @return трэкинг с информацией о текущем местоположении заказа
     */
    @Override
    public Optional<Tracking> getOrderTracking(int orderId) {
        return orderRepository.getExternalDeliveryNameForOrder(orderId)
                .map(deliveryServices::get)
                .flatMap(service -> service.getTrackingForOrder(orderId));
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
        if (!deliveryServices.containsKey(deliveryService)) {
            throw new IllegalArgumentException("Не существует запрошенного deliveryService: " + deliveryService);
        }
        var service = deliveryServices.get(deliveryService);
        service.registerOrder(orderId, startCity, finishCity);
        orderRepository.setExternalDeliveryNameForOrder(orderId, deliveryService);
    }
}
