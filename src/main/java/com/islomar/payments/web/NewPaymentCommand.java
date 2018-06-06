package com.islomar.payments.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.PaymentType;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NewPaymentCommand {

    private PaymentType type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;
}