package aaagt.shop.app;

import aaagt.shop.business.department.Department;
import aaagt.shop.business.department.delivery.ShopDeliveryDepartment;
import aaagt.shop.business.shop.Shop;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запуск приложения магазина");

        System.out.println("Сборка зависимостей");
        Department department = new ShopDeliveryDepartment(List.of(), null);
        Shop shop = new Shop(List.of(department));

        System.out.println("Приложение запустилось\n");

        System.out.println("Список департаментов магазина:");
        shop.getDepartmentNames().stream()
                .forEach(System.out::println);
    }
}
