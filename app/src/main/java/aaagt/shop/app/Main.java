package aaagt.shop.app;

import aaagt.shop.business.department.Department;
import aaagt.shop.business.department.delivery.ShopDeliveryDepartment;
import aaagt.shop.business.department.delivery.dto.Tracking;
import aaagt.shop.business.department.delivery.repository.OrderRepository;
import aaagt.shop.business.department.delivery.service.ExternalDeliveryService;
import aaagt.shop.business.shop.Shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запуск приложения магазина");

        System.out.println("Сборка зависимостей");
        Shop shop = getShop();

        System.out.println("Приложение запустилось\n");

        while (showMenu(shop)) ;
    }

    private static Shop getShop() {
        return new Shop(
                List.of(new ShopDeliveryDepartment(
                        List.of(new ExternalDeliveryService() {

                            final Map<Integer, Tracking> trackers = new HashMap<>(Map.of(1, new Tracking(List.of("Камчатка", "Челябинск", "Краснодар"), "Челябинск")));

                            @Override
                            public String getName() {
                                return "ЖД";
                            }

                            @Override
                            public Optional<Tracking> getTrackingForOrder(int orderId) {
                                if (orderId == 1) {
                                    return Optional.of(new Tracking(List.of("Камчатка", "Челябинск", "Краснодар"), "Челябинск"));
                                } else {
                                    return Optional.empty();
                                }
                            }

                            @Override
                            public void registerOrder(int orderId, String startCity, String finishCity) {
                                trackers.put(orderId, new Tracking(List.of(startCity, finishCity), startCity));
                            }
                        }),
                        new OrderRepository() {
                            final Map<Integer, String> externalDeliveryNameForOrder = new HashMap<>(Map.of(1, "ЖД"));

                            @Override
                            public Optional<String> getExternalDeliveryNameForOrder(int orderId) {
                                return Optional.ofNullable(externalDeliveryNameForOrder.get(orderId));
                            }

                            @Override
                            public void setExternalDeliveryNameForOrder(int orderId, String externalDelivery) {
                                externalDeliveryNameForOrder.put(orderId, externalDelivery);
                            }
                        })
                ));
    }

    private static boolean showMenu(Shop shop) {
        System.out.println("Список департаментов магазина:");
        shop.getDepartmentNames().stream()
                .forEach(System.out::println);

        var chosenDepartmentName = requestString(
                "Введите название депатамента с которым хотите работать");

        if (chosenDepartmentName.equalsIgnoreCase("выход")) {
            return false;
        }

        var department = shop.getDepartment(chosenDepartmentName);

        department.ifPresentOrElse(Department::showMenu, () -> {
            System.out.println("Запрашиваемого департамента не существует");
        });

        return true;
    }

    private static String requestString(String message) {
        var scanner = new Scanner(System.in);
        System.out.print(message + " ");
        return scanner.nextLine();
    }

}
