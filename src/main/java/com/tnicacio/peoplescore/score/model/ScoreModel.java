package com.tnicacio.peoplescore.score.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_score")
@Getter
@Setter
@NoArgsConstructor
public class ScoreModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String description;

    @Column(name = "inicial", nullable = false, unique = true)
    private Long initialScore;

    @Column(name = "final", nullable = false, unique = true)
    private Long finalScore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreModel that = (ScoreModel) o;
        return Objects.equals(id, that.id) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
