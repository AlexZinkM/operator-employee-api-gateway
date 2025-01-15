package ru.uniteller.operator_employee_api_gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoutingConfig {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("report-service-route") { route ->
                route.path(REPORT_SERVICE_PATH)
                    .uri(REPORT_SERVICE_URI)
            }
            .route("tax-service-route") { route ->
                route.path(TAX_SERVICE_PATH)
                    .uri(TAX_SERVICE_URI)
            }
            .build()
    }

}