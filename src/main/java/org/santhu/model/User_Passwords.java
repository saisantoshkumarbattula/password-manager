package org.santhu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User_Passwords {
    private int pass_count;
    @NonNull
    private int user_id;
    @NonNull
    private String website_name;
    @NonNull
    private String website_username;
    @NonNull
    private String website_password;
}
