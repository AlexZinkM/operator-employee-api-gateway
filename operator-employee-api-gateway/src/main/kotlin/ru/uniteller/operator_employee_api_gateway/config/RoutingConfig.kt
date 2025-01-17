package ru.uniteller.operator_employee_api_gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.web.server.ServerWebExchange
import java.security.Principal

@Configuration
class RoutingConfig {


    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("report-service-route") { route ->
                route.path(REPORT_SERVICE_PATH)
                    .filters { f ->
                        f.filter { exchange, chain ->
                            val roles = getUserRolesFromToken(exchange)
                            val modifiedRequest = exchange.request.mutate()
                                .header(HEADER_ROLES, roles.joinToString(","))
                                .build()
                            chain.filter(exchange.mutate().request(modifiedRequest).build())
                        }
                    }
                    .uri(REPORT_SERVICE_URI)
            }
            .route("tax-service-route") { route ->
                route.path(TAX_SERVICE_PATH)
                    .filters { f ->
                        f.filter { exchange, chain ->
                            val roles = getUserRolesFromToken(exchange)
                            val modifiedRequest = exchange.request.mutate()
                                .header(HEADER_ROLES, roles.joinToString(","))
                                .build()
                            chain.filter(exchange.mutate().request(modifiedRequest).build())
                        }
                    }
                    .uri(TAX_SERVICE_URI)
            }
            .build()
    }

    private fun getUserRolesFromToken(exchange: ServerWebExchange): List<String> {
        val authentication = exchange.getPrincipal<Principal>().block() as? AbstractAuthenticationToken
        return authentication?.authorities?.map { it.authority } ?: emptyList()
    }
}

