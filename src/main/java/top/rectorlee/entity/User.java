package top.rectorlee.entity;

import lombok.*;
import top.rectorlee.annotation.Sensitive;
import top.rectorlee.enums.SensitiveStrategy;

/**
 * @author Lee
 * @description
 * @date 2023-04-24  17:57:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    @Sensitive(strategy = SensitiveStrategy.USERNAME)
    private String name;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;

    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
    private String address;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;

    private String password;
}
