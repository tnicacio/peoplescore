package com.tnicacio.peoplescore.state.model;

import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_state")
@Getter
@Setter
@NoArgsConstructor
public class StateModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sigla", nullable = false, unique = true)
    private String abbreviation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "afinidade")
    private AffinityModel affinity;
}
