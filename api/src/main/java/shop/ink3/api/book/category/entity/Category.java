package shop.ink3.api.book.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(nullable = false)
    private String path;

    public boolean isRoot() {
        return parent == null;
    }

    public int getDepth() {
        if (path.isEmpty()) {
            return 0;
        }
        return Math.toIntExact(path.chars().filter(ch -> ch == '/').count());
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateParent(Category newParent) {
        this.parent = newParent;
    }

    public void updatePath(String newPath) {
        this.path = newPath;
    }
}
