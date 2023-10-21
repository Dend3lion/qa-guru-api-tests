package in.reqres.models;

import lombok.Data;


@Data
public class UserResponseModel {
    UserDataResponseModel data;
    UserSupportResponseModel support;
}
