package com.task.library_management_system.dto;

import com.task.library_management_system.entity.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserDto extends BaseDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 48, message = "Username cannot exceed 48 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private UserType type;
}
