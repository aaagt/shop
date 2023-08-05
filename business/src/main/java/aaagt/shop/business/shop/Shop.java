package aaagt.shop.business.shop;

import aaagt.shop.business.department.Department;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Shop {
    private final Map<String, Department> departments = new HashMap<>();

    public Set<String> getDepartmentNames() {
        return departments.keySet();
    }
}
