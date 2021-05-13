package switchtwentytwenty.project.domain.aggregates.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.domain.valueobject.CategoryID;
import switchtwentytwenty.project.domain.valueobject.CategoryName;

import java.util.Objects;

@NoArgsConstructor
@Setter
public class StandardCategory implements Category {

    @Getter
    private CategoryName categoryName;
    private CategoryID categoryID;
    @Getter
    private CategoryID parentID;

    public StandardCategory(CategoryName categoryName) {

        this.categoryName = categoryName;
    }

    public StandardCategory(CategoryName categoryName, CategoryID parentID) {

        this.categoryName = categoryName;
        this.parentID = parentID;
    }

    public StandardCategory(CategoryName categoryName, CategoryID categoryID, CategoryID parentID) {

        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.parentID = parentID;
    }


    @Override
    public CategoryID id() {
        return this.categoryID;
    }

    @Override
    public boolean hasID(CategoryID id) {
       return this.categoryID.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardCategory that = (StandardCategory) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryID, that.categoryID) && Objects.equals(parentID, that.parentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, categoryID, parentID);
    }
}
