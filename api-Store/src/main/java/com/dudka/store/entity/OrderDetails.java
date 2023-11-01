package com.dudka.store.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails implements Serializable {

    public OrderDetails(Boolean payment_status, User user, List<Order> orders) {
        this.payment_status = payment_status;
        this.user = user;
        this.orders = orders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_status")
    private Boolean payment_status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "users_id")
    @JsonManagedReference
    private User user;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    @JsonManagedReference
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetails)) return false;
        OrderDetails orderDetails = (OrderDetails) o;
        return getId().equals(orderDetails.getId());
    }

    @Override
    public int hashCode() {
        return (int) (37 * this.getId());
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", payment_status=" + payment_status +
                ", user=" + user +
                ", orders=" + orders +
                '}';
    }
}
