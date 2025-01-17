package ru.uniteller.operator_employee_api_gateway.routing

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import ru.uniteller.operator_employee_api_gateway.config.REPORT_SERVICE_ROOT_PATH
import ru.uniteller.operator_employee_api_gateway.config.REPORT_SERVICE_URI
import ru.uniteller.operator_employee_api_gateway.config.TAX_SERVICE_URI
import kotlin.test.Test


@SpringBootTest
class RoutingTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun should_reroute_to_correct_service() {
        assertTrue(makeRequest(REPORT_SERVICE_ROOT_PATH).contains(REPORT_SERVICE_URI))
        assertTrue(makeRequest(REPORT_SERVICE_ROOT_PATH).contains(TAX_SERVICE_URI))
        assertFalse(makeRequest("/api/do/not/exist").contains(":8080"));
    }



    private fun makeRequest(path: String): String {
        val client = WebTestClient.bindToApplicationContext(context).build()
        return client.get().uri(path).exchange().returnResult(String::class.java).toString()
    }

}


