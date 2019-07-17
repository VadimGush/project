package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.controller.validation.OptionalName;
import net.thumbtack.onlineshop.controller.validation.Password;
import net.thumbtack.onlineshop.controller.validation.Phone;
import net.thumbtack.onlineshop.controller.validation.RequiredName;
import net.thumbtack.onlineshop.database.models.Account;
import net.thumbtack.onlineshop.dto.actions.Edit;
import net.thumbtack.onlineshop.dto.actions.Register;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto extends LoginDto {

    private Long id;

    @RequiredName(groups = { Register.class, Edit.class})
    private String firstName;

    @RequiredName(groups = { Register.class, Edit.class })
    private String lastName;

    @OptionalName(groups = { Register.class, Edit.class })
    private String patronymic;

    @Email(groups = { Register.class, Edit.class })
    private String email;

    @NotBlank(groups = { Register.class, Edit.class })
    private String address;

    @Phone(groups = { Register.class, Edit.class })
    private String phone;

    @NotBlank(groups = Edit.class)
    private String oldPassword;

    @Password(groups = Edit.class)
    private String newPassword;

    public ClientDto() {

    }

    public ClientDto(Account account) {
        super(account.getLogin(), account.getPassword());
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.patronymic = account.getPatronymic();
        this.email = account.getEmail();
        this.address = account.getAddress();
        this.phone = account.getPhone();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
