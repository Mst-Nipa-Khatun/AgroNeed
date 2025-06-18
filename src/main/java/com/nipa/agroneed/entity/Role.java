package com.nipa.agroneed.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}