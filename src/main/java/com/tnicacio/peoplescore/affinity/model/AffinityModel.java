package com.tnicacio.peoplescore.affinity.model;

import com.tnicacio.peoplescore.state.model.StateModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_affinity")
@Getter
@Setter
@NoArgsConstructor
public class AffinityModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "regiao")
    private String region;

    @OneToMany(mappedBy = "affinity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<StateModel> states = new HashSet<>();
}
