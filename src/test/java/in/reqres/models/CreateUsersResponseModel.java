package in.reqres.models;

import lombok.Data;

@Data
public class CreateUsersResponseModel {
    String name, job, createdAt;
    int id;
}
