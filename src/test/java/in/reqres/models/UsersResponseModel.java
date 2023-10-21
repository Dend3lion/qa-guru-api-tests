package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class UsersResponseModel {
    int page, total;
    @JsonProperty("per_page")
    int perPage;
    @JsonProperty("total_pages")
    int totalPages;
    List<UserDataResponseModel> data;
    UserSupportResponseModel support;
}
