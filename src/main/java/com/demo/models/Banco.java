package com.demo.models;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Banco {
    private Long id;
    private String nombre;
    private int totalTransferencias;
}
