package com.tnicacio.peoplescore.affinity.controller;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.service.AffinityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/afinidade")
public class AffinityController {

    private final AffinityService affinityService;

    public AffinityController(AffinityService affinityService) {
        this.affinityService = affinityService;
    }

    @PostMapping
    public ResponseEntity<AffinityDTO> insert(@Valid @RequestBody AffinityDTO dto) {
        dto = affinityService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
