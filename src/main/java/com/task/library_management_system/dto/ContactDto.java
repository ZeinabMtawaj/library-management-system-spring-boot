package com.task.library_management_system.dto;

import com.task.library_management_system.dto.validation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ContactDto extends BaseDto {

    @NotBlank(message = "Phone cannot be blank")
    @ValidPhoneNumber
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

}
