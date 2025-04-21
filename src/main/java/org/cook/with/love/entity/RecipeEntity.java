package org.cook.with.love.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "step")
    private String step;

    @Column(name = "reference")
    private String reference;

    @Column(name = "tags")
    private String tags;

    @Column(name = "category")
    private String category;

    @CreationTimestamp
    @Column(name = "created_dt")
    private Timestamp createdDt;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;
}
