package ru.uniteller.operator_employee_api_gateway.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import ru.uniteller.operator_employee_api_gateway.config.REPORT_SERVICE_PATH
import kotlin.test.Test

@WebFluxTest
class ReportingSecurityTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

//    @MockitoBean
//    private lateinit var webClient: WebClient


    @Test
    @WithMockUser(roles = ["OPERATOR_USER"])
    fun `should return 200 for authorized user`() {
        webTestClient.get()
            .uri(REPORT_SERVICE_PATH)
            .header("Authorization", "Bearer valid_token")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    @WithMockUser(roles = ["OPERATOR_USER"])
    fun `should return 403 for unauthorized user`() {
        webTestClient.get()
            .uri(REPORT_SERVICE_PATH)
            .header("Authorization", "Bearer invalid_token")
            .exchange()
            .expectStatus().isForbidden
    }
}