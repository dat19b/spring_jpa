package dk.andl.model;

//import lombok.Data;

import javax.persistence.*;
import java.util.Set;

//@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "recipes_categories",
            joinColumns = {
                    @JoinColumn(name = "recipes_id", referencedColumnName = "id",
                            nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "categories_id", referencedColumnName = "id",
                            nullable = false, updatable = false)
            }
    )
//    @JsonIgnore
    private Set<Category> categories;

    public Recipe() {}

    public Recipe(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
