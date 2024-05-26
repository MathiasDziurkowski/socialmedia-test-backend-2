package io.github.mathiasdziurkowski.socialmedia.teste.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Getter
    private String nome;
    @Column
    @Getter
    private String email;
    @Column
    @Getter
    private String senha;
}
