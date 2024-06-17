package com.Java020.MonieFlex.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseClass{
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Customer> customers;
}
