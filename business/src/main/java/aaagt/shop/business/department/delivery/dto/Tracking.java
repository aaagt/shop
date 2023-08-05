package aaagt.shop.business.department.delivery.dto;

import java.util.List;

public record Tracking(
        List<String> points,
        String currentPoint
) {}
