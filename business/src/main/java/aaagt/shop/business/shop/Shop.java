package aaagt.shop.business.shop;

import aaagt.shop.business.department.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Магазин организует работу со своими департаментами
 */
public class Shop {

    private final Map<String, Department> departments;

    public Shop(List<Department> departments) {
        this.departments = departments.stream()
                .collect(Collectors.toMap(department -> department.getName().toUpperCase(), Function.identity()));
    }

    /**
     * Получить идентификаторы подразделений
     *
     * @return идентификаторы подразделений
     */
    public Set<String> getDepartmentNames() {
        return departments.keySet();
    }

    /**
     * Получить подразделение по его идентификатору
     *
     * @param departmentName идентификатор подразделения
     * @return подразделение
     */
    public Optional<Department> getDepartment(String departmentName) {
        return Optional.ofNullable(departments.get(departmentName.toUpperCase()));
    }
}
