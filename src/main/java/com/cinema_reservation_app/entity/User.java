package com.cinema_reservation_app.entity;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
