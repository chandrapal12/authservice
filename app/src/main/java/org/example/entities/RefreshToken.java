package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name ="tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name="id", referencedColumnName="user_id")

    private User user;
}
