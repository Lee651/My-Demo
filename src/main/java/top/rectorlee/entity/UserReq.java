package top.rectorlee.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Lee
 * @description
 * @date 2023-04-24  17:57:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    @NotBlank(message = "姓名不能为空")
    @Length(max = 4, min = 2, message = "姓名最少为两个字, 最多为四个字")
    private String name;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    @NotBlank(message = "密码不能为空")
    @Length(max = 20, min = 8, message = "密码最少为8位数, 最多为20位数")
    private String password;
}
