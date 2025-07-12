package co.kr.muldum.application.items;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequestDto {

    private String productName;
    private int quantity;
    private Integer price;
    private String productLink;
    private String itemSource;
    private String reason;

}
