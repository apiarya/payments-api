package com.islomar.payments.end2end;

import com.islomar.payments.web.UpsertPaymentCommand;
import com.islomar.payments.web.response.DeleteOnePaymentResponse;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.OnePaymentResponse;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static com.islomar.payments.shared.ObjectMother.aNewPaymentCommand;


@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringBootBaseFeatureTest {


    private final String SERVER_URL = "http://localhost";
    private final String VERSION_1_PAYMENTS_PATH = "/v1/payments";
    private final TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public SpringBootBaseFeatureTest() {
        restTemplate = new TestRestTemplate();
    }

    public ResponseEntity<OnePaymentResponse> fetchOnePayment(String paymentId) {
        return restTemplate.getForEntity(generatePaymentURI(paymentId), OnePaymentResponse.class);
    }

    public ResponseEntity<FetchAllPaymentsResponse> fetchAllPayments() {
        return restTemplate.getForEntity(generateBaseApiUri(), FetchAllPaymentsResponse.class);
    }

    public ResponseEntity<OnePaymentResponse> createOnePayment(UpsertPaymentCommand upsertPaymentCommand) {
        return restTemplate.postForEntity(generateBaseApiUri(), upsertPaymentCommand, OnePaymentResponse.class);
    }

    public ResponseEntity<DeleteOnePaymentResponse> deleteOnePayment(String paymentId) {
        RequestEntity<DeleteOnePaymentResponse> entity = new RequestEntity<>(HttpMethod.DELETE, generatePaymentURI(paymentId));
        return restTemplate.exchange(entity, DeleteOnePaymentResponse.class);
    }

    public ResponseEntity<OnePaymentResponse> updateOnePayment(String paymentId) {
        UpsertPaymentCommand updatePaymentCommand = aNewPaymentCommand();
        updatePaymentCommand.setOrganisationId("updated-organisation-id");
        RequestEntity<UpsertPaymentCommand> entity = new RequestEntity<>(updatePaymentCommand, HttpMethod.PUT, generatePaymentURI(paymentId));
        return restTemplate.exchange(entity, OnePaymentResponse.class);
    }

    private URI generatePaymentURI(String paymentId) {
        return URI.create(generateBaseApiUri().toString() + "/" + paymentId);
    }

    private URI generateBaseApiUri() {
        return URI.create(SERVER_URL + ":" + port + VERSION_1_PAYMENTS_PATH);
    }
}