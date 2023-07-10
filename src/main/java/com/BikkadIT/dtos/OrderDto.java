package com.BikkadIT.dtos;

import com.BikkadIT.entity.OrderItem;
import com.BikkadIT.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderDto {

    private long orderItemId;
    @NotNull(message = "Order Status is required")
    private String orderStatus="Pending";

    @NotNull(message = "Payment Status is required")
    private String paymentStatus="NotPaid";

    private long orderAmount;

    @NotNull(message = "Billing Address is required")
    private String billingAddress;

    @NotNull(message = "Billing Phone number is required")
    private String billingPhone;

    @NotNull(message = "Billing Name number is required")
    private String billingName;

    private Date orderedDate;

    private Date deliveredDate;

    private List<OrderItem> orderItem = new ArrayList<>();
}
