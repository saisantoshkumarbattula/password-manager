package org.santhu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private int userId;
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
