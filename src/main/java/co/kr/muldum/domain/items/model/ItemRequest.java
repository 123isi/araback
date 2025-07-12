package co.kr.muldum.domain.items.model;

import co.kr.muldum.domain.items.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    @Column(length = 100, nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    private Integer price;

    @Column(columnDefinition = "TEXT")
    private String productLink;

    @Column(length = 20)
    private String itemSource;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private Integer deliveryNum;

    public void changeIntempToPending() {
        if (this.status != Status.INTEMP) {
            throw new IllegalStateException("PENDING으로 전환할수 없음");
        }
        this.status = Status.PENDING;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }

}
